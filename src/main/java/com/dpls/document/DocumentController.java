package com.dpls.document;

import com.dpls.common.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
}
