package com.crepen.crepenboard.api.entity;

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
    private String uuid;

    @Column(nullable = false, unique = true , length = 200 , name = "file_name")
    private String fileName;
}
