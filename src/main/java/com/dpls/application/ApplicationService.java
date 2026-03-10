package com.dpls.application;

import com.dpls.common.audit.AuditLog;
import com.dpls.common.audit.AuditLogService;
import com.dpls.common.enums.ApplicationStatus;
import com.dpls.permittype.PermitType;
import com.dpls.permittype.PermitTypeRepository;
import com.dpls.user.User;
import com.dpls.user.UserService;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final PermitTypeRepository permitTypeRepository;
    private final UserService userService;
    private final AuditLogService auditLogService;

    public ApplicationResponse create(ApplicationRequest request) {
        User applicant = userService.getCurrentUser();

        PermitType permitType = permitTypeRepository.findById(request.getPermitTypeId())
                .orElseThrow(() -> new RuntimeException("Permit type not found"));

        Application application = Application.builder()
                .applicant(applicant)
                .permitType(permitType)
                .status(ApplicationStatus.DRAFT)
                .build();

        applicationRepository.save(application);
        auditLogService.log(application, applicant, "APPLICATION_CREATED", "Application created as DRAFT");
        return mapToResponse(application);
    }

    public ApplicationResponse submit(Long id) {
        User currentUser = userService.getCurrentUser();
        Application application = getApplicationById(id);

        if (!application.getApplicant().getId().equals(currentUser.getId())) {
            throw new RuntimeException("You are not authorized to submit this application");
        }

        if (application.getStatus() != ApplicationStatus.DRAFT &&
                application.getStatus() != ApplicationStatus.NEEDS_CORRECTION) {
            throw new RuntimeException("Only DRAFT or NEEDS_CORRECTION applications can be submitted");
        }

        application.setStatus(ApplicationStatus.SUBMITTED);
        applicationRepository.save(application);
        auditLogService.log(application, currentUser, "APPLICATION_SUBMITTED", "Application submitted for review");
        return mapToResponse(application);
    }

    public ApplicationResponse getById(Long id) {
        User currentUser = userService.getCurrentUser();
        Application application = getApplicationById(id);

        if (!application.getApplicant().getId().equals(currentUser.getId())) {
            throw new RuntimeException("You are not authorized to view this application");
        }

        return mapToResponse(application);
    }

    public List<ApplicationResponse> getMyApplications() {
        User currentUser = userService.getCurrentUser();
        return applicationRepository.findByApplicantId(currentUser.getId())
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public Application getApplicationById(Long id) {
        return applicationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Application not found"));
    }

    private ApplicationResponse mapToResponse(Application application) {
        return ApplicationResponse.builder()
                .id(application.getId())
                .applicantName(application.getApplicant().getName())
                .applicantEmail(application.getApplicant().getEmail())
                .permitTypeName(application.getPermitType().getName())
                .status(application.getStatus())
                .notes(application.getNotes())
                .submittedAt(application.getSubmittedAt())
                .updatedAt(application.getUpdatedAt())
                .build();
    }
}