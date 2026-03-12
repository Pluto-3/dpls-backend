package com.dpls.common.audit;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {
    List<AuditLog> findByApplicationIdOrderByTimestampAsc(Long applicationId);
    List<AuditLog> findAllByOrderByTimestampDesc();
}
