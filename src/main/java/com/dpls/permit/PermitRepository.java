package com.dpls.permit;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.time.LocalDateTime;
import java.util.List;

public interface PermitRepository extends JpaRepository<Permit, Long> {
    Optional<Permit> findByVerificationCode(String verificationCode);
    Optional<Permit> findByApplicationId(Long applicationId);

    @Query("SELECT p FROM Permit p WHERE p.expiresAt < :now AND p.application.status = 'PERMIT_ISSUED'")
    List<Permit> findExpiredPermits(@Param("now") LocalDateTime now);
}