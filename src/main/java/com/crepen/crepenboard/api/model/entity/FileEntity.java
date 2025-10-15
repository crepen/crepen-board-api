package com.crepen.crepenboard.api.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "file-store")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FileEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String uuid;

    @Column(nullable = false, unique = true , length = 200 , name = "file_name")
    private String fileName;
}
