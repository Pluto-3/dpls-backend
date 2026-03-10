package com.dpls.permit;

import com.dpls.application.Application;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "permits")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Permit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "application_id", nullable = false)
    private Application application;

    @Column(nullable = false, unique = true)
    private String permitNumber;

    @Column(nullable = false, unique = true)
    private String verificationCode;

    @CreationTimestamp
    private LocalDateTime issuedAt;

    private LocalDateTime expiresAt;
}