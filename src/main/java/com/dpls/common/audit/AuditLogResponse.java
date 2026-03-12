package com.dpls.common.audit;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class AuditLogResponse {
    private Long id;
    private Long applicationId;
    private String actorName;
    private String actorRole;
    private String action;
    private String notes;
    private LocalDateTime timestamp;
}