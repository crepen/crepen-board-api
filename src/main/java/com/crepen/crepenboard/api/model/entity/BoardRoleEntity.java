package com.crepen.crepenboard.api.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@Table(name = "board-role")
@NoArgsConstructor
@AllArgsConstructor
public class BoardRoleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Column(nullable = false , length = 200 , name = "board_uuid")
    private String boardUuid;

    @Column(nullable = false , length = 200 , name = "user_uuid")
    private String userUuid;
}
