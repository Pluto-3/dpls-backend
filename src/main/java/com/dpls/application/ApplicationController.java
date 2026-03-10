package com.dpls.application;

import com.dpls.common.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/applications")
@RequiredArgsConstructor
public class ApplicationController {

    private final ApplicationService applicationService;

    @PostMapping
    @PreAuthorize("hasRole('ROLE_APPLICANT')")
    public ResponseEntity<ApiResponse<ApplicationResponse>> create(
            @Valid @RequestBody ApplicationRequest request) {
        ApplicationResponse response = applicationService.create(request);
        return ResponseEntity.ok(ApiResponse.success("Application created", response));
    }

    @PostMapping("/{id}/submit")
    @PreAuthorize("hasRole('ROLE_APPLICANT')")
    public ResponseEntity<ApiResponse<ApplicationResponse>> submit(@PathVariable Long id) {
        ApplicationResponse response = applicationService.submit(id);
        return ResponseEntity.ok(ApiResponse.success("Application submitted", response));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_APPLICANT')")
    public ResponseEntity<ApiResponse<ApplicationResponse>> getById(@PathVariable Long id) {
        ApplicationResponse response = applicationService.getById(id);
        return ResponseEntity.ok(ApiResponse.success("Application retrieved", response));
    }

    @GetMapping("/my")
    @PreAuthorize("hasRole('ROLE_APPLICANT')")
    public ResponseEntity<ApiResponse<List<ApplicationResponse>>> getMyApplications() {
        List<ApplicationResponse> response = applicationService.getMyApplications();
        return ResponseEntity.ok(ApiResponse.success("Applications retrieved", response));
    }
}