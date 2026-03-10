package com.dpls.permit;

import com.dpls.application.Application;
import com.dpls.application.ApplicationRepository;
import com.dpls.application.ApplicationService;
import com.dpls.common.audit.AuditLogService;
import com.dpls.common.enums.ApplicationStatus;
import com.dpls.user.User;
import com.dpls.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PermitService {

    private final PermitRepository permitRepository;
    private final ApplicationService applicationService;
    private final ApplicationRepository applicationRepository;
    private final UserService userService;
    private final AuditLogService auditLogService;

    public PermitResponse issue(Long applicationId) {
        Application application = applicationService.getApplicationById(applicationId);
        User officer = userService.getCurrentUser();

        if (application.getStatus() != ApplicationStatus.APPROVED) {
            throw new RuntimeException("Only APPROVED applications can be issued a permit");
        }

        if (permitRepository.findByApplicationId(applicationId).isPresent()) {
            throw new RuntimeException("A permit has already been issued for this application");
        }

        int validityDays = Integer.parseInt(application.getPermitType().getValidityPeriodDays());

        Permit permit = Permit.builder()
                .application(application)
                .permitNumber(generatePermitNumber())
                .verificationCode(UUID.randomUUID().toString())
                .issuedAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusDays(validityDays))
                .build();

        permitRepository.save(permit);

        application.setStatus(ApplicationStatus.PERMIT_ISSUED);
        applicationRepository.save(application);

        auditLogService.log(application, officer, "PERMIT_ISSUED",
                "Permit issued with number " + permit.getPermitNumber());

        return mapToResponse(permit);
    }

    public PermitResponse getByApplicationId(Long applicationId) {
        Permit permit = permitRepository.findByApplicationId(applicationId)
                .orElseThrow(() -> new RuntimeException("No permit found for this application"));
        return mapToResponse(permit);
    }

    public PermitResponse verify(String verificationCode) {
        Permit permit = permitRepository.findByVerificationCode(verificationCode)
                .orElseThrow(() -> new RuntimeException("Invalid verification code"));
        return mapToResponse(permit);
    }

    private String generatePermitNumber() {
        return "DPLS-" + System.currentTimeMillis();
    }

    private PermitResponse mapToResponse(Permit permit) {
        Application application = permit.getApplication();
        return PermitResponse.builder()
                .id(permit.getId())
                .permitNumber(permit.getPermitNumber())
                .verificationCode(permit.getVerificationCode())
                .applicantName(application.getApplicant().getName())
                .applicantEmail(application.getApplicant().getEmail())
                .permitTypeName(application.getPermitType().getName())
                .departmentName(application.getPermitType().getDepartment().getName())
                .issuedAt(permit.getIssuedAt())
                .expiresAt(permit.getExpiresAt())
                .build();
    }
}