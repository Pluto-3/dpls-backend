package com.dpls.review;

import com.dpls.application.Application;
import com.dpls.application.ApplicationResponse;
import com.dpls.application.ApplicationService;
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
public class ReviewController {

    private final ReviewService reviewService;
    private final ApplicationService applicationService;

    @GetMapping("/submitted")
    @PreAuthorize("hasRole('ROLE_OFFICER')")
    public ResponseEntity<ApiResponse<List<Application>>> getSubmitted() {
        List<Application> applications = reviewService.getSubmittedApplications();
        return ResponseEntity.ok(ApiResponse.success("Submitted applications retrieved", applications));
    }

    @PostMapping("/{id}/approve")
    @PreAuthorize("hasRole('ROLE_OFFICER')")
    public ResponseEntity<ApiResponse<ReviewResponse>> approve(
            @PathVariable Long id,
            @RequestBody(required = false) ReviewRequest request) {
        if (request == null) request = new ReviewRequest();
        request.setDecision(com.dpls.common.enums.ReviewDecision.APPROVED);
        ReviewResponse response = reviewService.review(id, request);
        return ResponseEntity.ok(ApiResponse.success("Application approved", response));
    }

    @PostMapping("/{id}/reject")
    @PreAuthorize("hasRole('ROLE_OFFICER')")
    public ResponseEntity<ApiResponse<ReviewResponse>> reject(
            @PathVariable Long id,
            @Valid @RequestBody ReviewRequest request) {
        request.setDecision(com.dpls.common.enums.ReviewDecision.REJECTED);
        ReviewResponse response = reviewService.review(id, request);
        return ResponseEntity.ok(ApiResponse.success("Application rejected", response));
    }

    @PostMapping("/{id}/request-correction")
    @PreAuthorize("hasRole('ROLE_OFFICER')")
    public ResponseEntity<ApiResponse<ReviewResponse>> requestCorrection(
            @PathVariable Long id,
            @Valid @RequestBody ReviewRequest request) {
        request.setDecision(com.dpls.common.enums.ReviewDecision.REQUEST_CORRECTION);
        ReviewResponse response = reviewService.review(id, request);
        return ResponseEntity.ok(ApiResponse.success("Correction requested", response));
    }

    @GetMapping("/{id}/reviews")
    @PreAuthorize("hasRole('ROLE_OFFICER')")
    public ResponseEntity<ApiResponse<List<ReviewResponse>>> getReviews(@PathVariable Long id) {
        List<ReviewResponse> response = reviewService.getReviewsByApplicationId(id);
        return ResponseEntity.ok(ApiResponse.success("Reviews retrieved", response));
    }
}