package com.dpls.permit;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class PermitResponse {
    private Long id;
    private String permitNumber;
    private String verificationCode;
    private String applicantName;
    private String applicantEmail;
    private String permitTypeName;
    private String departmentName;
    private LocalDateTime issuedAt;
    private LocalDateTime expiresAt;
}