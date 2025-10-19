package com.crepen.crepenboard.api.module.user.model.entity;

import com.crepen.crepenboard.api.module.user.model.UserRole;
import com.crepen.crepenboard.api.module.user.model.UserStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "user")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String uuid;



    @Column(nullable = false , unique = true , length = 200 , name = "user_name")
    private String userName;

    @Column(nullable = false , unique = true , length = 200 , name = "user_id")
    private String userId;

    @Column(nullable = false , name = "user_password" , length = 200)
    private String userPassword;

    @Column(name = "user_email" , nullable = false , length = 200)
    private String userEmail;



    @Enumerated(EnumType.STRING)
    @Column(nullable = false , name = "user_status" , columnDefinition = "VARCHAR(20) DEFAULT 'STABLE'")
    private UserStatus userStatus;




    @OneToMany(mappedBy = "user" , cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<UserRoleEntity> userRoles;


    @Column(name = "create_date" , nullable = false , updatable = false )
    private OffsetDateTime createDate;


    @Column(name = "terminate_date")
    private OffsetDateTime terminateDate;



    @PrePersist
    protected void onCreate() {
        this.createDate = OffsetDateTime.now(ZoneOffset.UTC);
    }
}
