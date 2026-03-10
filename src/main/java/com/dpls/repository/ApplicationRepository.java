package com.dpls.repository;

import com.dpls.application.Application;
import com.dpls.common.enums.ApplicationStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApplicationRepository extends JpaRepository<Application, Long> {
    List<Application> findByApplicantId(Long applicantId);
    List<Application> findByStatus(ApplicationStatus status);
}
