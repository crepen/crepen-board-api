package com.crepen.crepenboard.api.common.provider;

import com.crepen.crepenboard.api.model.entity.UserEntity;
import com.crepen.crepenboard.api.model.enums.JwtType;
import com.crepen.crepenboard.api.model.vo.JwtUserPayload;
import com.crepen.crepenboard.api.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.*;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JwtProvider {

    private final UserService userService;

    @Value("${jwt.secret}")
    private String secretKey;

    private long tokenValidityInMilliseconds = 60*60*1000;

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    // 1. 토큰 생성
    public String createToken(UserEntity userEntity , JwtType type) {

        Date now = new Date();
        Date validity = new Date(now.getTime() + (type == JwtType.ACCESS ? 5*60*1000 : 60*60*1000));

        SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes());

        List<String> roles = new ArrayList<>();
        roles.add("ROLE_COMMON");

        if(type == JwtType.ACCESS) {
            roles.add("ROLE_TOKEN_ACCESS");
        }
        else if(type == JwtType.REFRESH){
            roles.add("ROLE_TOKEN_REFRESH");
        }

        return Jwts.builder()
                .issuedAt(now)
                .expiration(validity)
                .signWith(key)
                .claim("uuid", userEntity.getUuid())
                .claim("type" , type.getTypeName())
                .claim("roles" ,roles )
                .compact();

    }

    // 2. 토큰에서 사용자 정보(Authentication) 추출
    public Authentication getAuthentication(String token) {

        SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes());

        Claims claims = Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();

        Optional<UserEntity> userEntity = userService.getUserByUUID(claims.get("uuid").toString());

        JwtUserPayload jwtUserPayload = JwtUserPayload.builder()
                .type(JwtType.of(claims.get("type").toString()))
                .uuid(claims.get("uuid").toString())
                .userData(userEntity.orElse(null))
                .build();



        List<String> roles = claims.get("roles", List.class);
        List<GrantedAuthority> authorities = roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

//        UserDetails principal = new User(claims.getSubject(), "", authorities);
        return new UsernamePasswordAuthenticationToken(jwtUserPayload, token ,authorities );
    }

    // 3. 토큰 유효성 검사
    public boolean validateToken(String token) {
        try {
            SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes());

            Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (Exception e) {
            // Log the exception details (MalformedJwtException, ExpiredJwtException, etc.)
            return false;
        }
    }
}
