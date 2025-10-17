package com.crepen.crepenboard.api.module.log.model.entity;

import com.crepen.crepenboard.api.module.log.model.LogCategory;
import com.crepen.crepenboard.api.module.log.model.LogType;
import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Entity
@Getter
@Setter
@Builder
@Table(name = "log")
@NoArgsConstructor
@AllArgsConstructor
public class LogEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Enumerated(EnumType.STRING)
    @Column(name = "log_category" , nullable = false ,columnDefinition = "VARCHAR(20)")
    private LogCategory logCategory;

    @Enumerated(EnumType.STRING)
    @Column(name = "log_type" , nullable = false , columnDefinition = "VARCHAR(20)")
    private LogType logType;

    @Column(name = "target_uid" )
    private String targetUid;

    @Column(name = "log_message" , nullable = false)
    private String message;

    @Column(name = "timestamp" , nullable = false , updatable = false )
    private OffsetDateTime timestamp;

    @PrePersist
    protected void onCreate() {
        this.timestamp = OffsetDateTime.now(ZoneOffset.UTC);
    }
}
