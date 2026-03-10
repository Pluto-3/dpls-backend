package com.dpls.application;

import com.dpls.common.enums.ApplicationStatus;
import lombok.Data;
import lombok.Builder;

import java.time.LocalDateTime;

@Data
@Builder
public class ApplicationResponse {
    private Long id;
    private String applicantName;
    private String applicantEmail;
    private String permitTypeName;
    private ApplicationStatus status;
    private String notes;
    private LocalDateTime submittedAt;
    private LocalDateTime updatedAt;
}