package com.dpls.common.audit;

import com.dpls.application.Application;
import com.dpls.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuditLogService {

    private final AuditLogRepository auditLogRepository;

    public void log(Application application, User actor, String action, String notes) {
        AuditLog auditLog = AuditLog.builder()
                .application(application)
                .actor(actor)
                .action(action)
                .notes(notes)
                .build();
        auditLogRepository.save(auditLog);
    }

    public List<AuditLogResponse> getTimeline(Long applicationId) {
        return auditLogRepository
                .findByApplicationIdOrderByTimestampAsc(applicationId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    private AuditLogResponse mapToResponse(AuditLog log) {
        return AuditLogResponse.builder()
                .id(log.getId())
                .actorName(log.getActor().getName())
                .actorRole(log.getActor().getRole().name())
                .action(log.getAction())
                .notes(log.getNotes())
                .timestamp(log.getTimestamp())
                .build();
    }
}