package org.kaoden.ws.homework.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import static lombok.AccessLevel.PROTECTED;

@Data
@Entity
@Builder
@Table(name = "security_audit")
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
public class SecurityAudit {

    @Id
    @GeneratedValue
    Long id;

    @Column(nullable = false)
    Long assessmentId;

    @Column(nullable = false)
    String info;

    @Column(nullable = false)
    LocalDateTime createdAt;

}
