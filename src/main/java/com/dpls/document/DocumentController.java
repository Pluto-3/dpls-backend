package com.dpls.document;

import com.dpls.common.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.List;

@RestController
@RequestMapping("/api/applications")
@RequiredArgsConstructor
public class DocumentController {

    private final DocumentService documentService;

    @PostMapping("/{id}/documents")
    @PreAuthorize("hasRole('ROLE_APPLICANT')")
    public ResponseEntity<ApiResponse<DocumentResponse>> upload(
            @PathVariable Long id,
            @RequestParam("file") MultipartFile file) {
        DocumentResponse response = documentService.upload(id, file);
        return ResponseEntity.ok(ApiResponse.success("Document uploaded", response));
    }

    @GetMapping("/{id}/documents")
    @PreAuthorize("hasRole('ROLE_APPLICANT')")
    public ResponseEntity<ApiResponse<List<DocumentResponse>>> getByApplicationId(
            @PathVariable Long id) {
        List<DocumentResponse> response = documentService.getByApplicationId(id);
        return ResponseEntity.ok(ApiResponse.success("Documents retrieved", response));
    }

    @GetMapping("/officer/{id}/documents")
    @PreAuthorize("hasAuthority('ROLE_OFFICER')")
    public ResponseEntity<ApiResponse<List<DocumentResponse>>> getByApplicationIdForOfficer(
            @PathVariable Long id) {
        List<DocumentResponse> response = documentService.getByApplicationIdForOfficer(id);
        return ResponseEntity.ok(ApiResponse.success("Documents retrieved", response));
    }

    @GetMapping("/files/{filename}")
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
        try {
            Path filePath = Paths.get("uploads").resolve(filename).normalize();
            Resource resource = new UrlResource(filePath.toUri());

            if (!resource.exists()) {
                return ResponseEntity.notFound().build();
            }

            String contentType = Files.probeContentType(filePath);
            if (contentType == null) contentType = "application/octet-stream";

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .header(HttpHeaders.CONTENT_DISPOSITION,
                            "inline; filename=\"" + resource.getFilename() + "\"")
                    .body(resource);

        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
