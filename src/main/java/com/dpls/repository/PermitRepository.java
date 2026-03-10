package com.dpls.repository;

import com.dpls.permit.Permit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PermitRepository extends JpaRepository<Permit, Long> {
    Optional<Permit> findByVerificationCode(String verificationCode);
    Optional<Permit> findByApplicationId(Long applicationId);
}