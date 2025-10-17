package com.crepen.crepenboard.api.module.board.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "board")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BoardEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String uuid;

    @Column(nullable = false , unique = true , length = 100 , name = "board_name")
    private String boardName;

    @Column(nullable = false , unique = true , length = 100 , name = "board_alias")
    private String boardAlias;

    @Column(nullable = false ,unique = false , length = 100 , name = "board_owner")
    private String boardOwner;

    @Column(length = 200 , name = "board_desc")
    private String boardDesc;

    @Column(nullable = false , name = "is_public", columnDefinition = "BOOLEAN DEFAULT FALSE")
    private boolean isPublic;

}
