package com.crepen.crepenboard.api.model.entity;

import com.crepen.crepenboard.api.model.enums.UserStatusType;
import jakarta.persistence.*;
import lombok.*;

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
    @Column(nullable = false , name = "user_status" , columnDefinition = "VARCHAR(10) DEFAULT 'STABLE'")
    private UserStatusType userStatus;
}
