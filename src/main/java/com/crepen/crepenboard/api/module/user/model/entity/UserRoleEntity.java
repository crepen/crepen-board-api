package com.crepen.crepenboard.api.module.user.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "user-role")
public class UserRoleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

//    @Column(name = "user_uuid" , nullable = false , length = 200)
//    private String userUuid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_uuid" , referencedColumnName = "uuid" , nullable = false)
    private UserEntity user;

    @Column(name = "role" , nullable = false , length = 50)
    private String role;
}
