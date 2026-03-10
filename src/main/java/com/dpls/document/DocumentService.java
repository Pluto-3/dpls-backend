package com.dpls.document;

import com.dpls.application.Application;
import com.dpls.application.ApplicationService;
import com.dpls.common.enums.ApplicationStatus;
import com.dpls.repository.ApplicationDocumentRepository;
import com.dpls.user.User;
import com.dpls.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DocumentService {

    private final ApplicationDocumentRepository documentRepository;
    private final ApplicationService applicationService;
    private final UserService userService;

    @Value("${file.upload-dir}")
    private String uploadDir;

    public DocumentResponse upload(Long applicationId, MultipartFile file) {
        User currentUser = userService.getCurrentUser();
        Application application = applicationService.getApplicationById(applicationId);

        if (!application.getApplicant().getId().equals(currentUser.getId())) {
            throw new RuntimeException("You are not authorized to upload to this application");
        }

        if (application.getStatus() != ApplicationStatus.DRAFT &&
                application.getStatus() != ApplicationStatus.NEEDS_CORRECTION) {
            throw new RuntimeException("Documents can only be uploaded to DRAFT or NEEDS_CORRECTION applications");
        }

        try {
            Path uploadPath = Paths.get(uploadDir);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            String uniqueFileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
            Path filePath = uploadPath.resolve(uniqueFileName);
            Files.copy(file.getInputStream(), filePath);

            ApplicationDocument document = ApplicationDocument.builder()
                    .application(application)
                    .fileName(file.getOriginalFilename())
                    .fileUrl(filePath.toString())
                    .build();

            documentRepository.save(document);
            return mapToResponse(document);

        } catch (IOException e) {
            throw new RuntimeException("Failed to upload file: " + e.getMessage());
        }
    }

    public List<DocumentResponse> getByApplicationId(Long applicationId) {
        User currentUser = userService.getCurrentUser();
        Application application = applicationService.getApplicationById(applicationId);

        if (!application.getApplicant().getId().equals(currentUser.getId())) {
            throw new RuntimeException("You are not authorized to view these documents");
        }

        return documentRepository.findByApplicationId(applicationId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    private DocumentResponse mapToResponse(ApplicationDocument document) {
        return DocumentResponse.builder()
                .id(document.getId())
                .fileName(document.getFileName())
                .fileUrl(document.getFileUrl())
                .uploadedAt(document.getUploadedAt())
                .build();
    }
}