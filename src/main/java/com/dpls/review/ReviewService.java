package com.dpls.review;

import com.dpls.application.Application;
import com.dpls.repository.ApplicationRepository;
import com.dpls.application.ApplicationService;
import com.dpls.common.enums.ApplicationStatus;
import com.dpls.common.enums.ReviewDecision;
import com.dpls.repository.ApplicationReviewRepository;
import com.dpls.user.User;
import com.dpls.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ApplicationReviewRepository reviewRepository;
    private final ApplicationRepository applicationRepository;
    private final ApplicationService applicationService;
    private final UserService userService;

    public ReviewResponse review(Long applicationId, ReviewRequest request) {
        User reviewer = userService.getCurrentUser();
        Application application = applicationService.getApplicationById(applicationId);

        if (application.getStatus() != ApplicationStatus.SUBMITTED &&
                application.getStatus() != ApplicationStatus.UNDER_REVIEW) {
            throw new RuntimeException("Only SUBMITTED or UNDER_REVIEW applications can be reviewed");
        }

        // Update application status based on decision
        if (request.getDecision() == ReviewDecision.APPROVED) {
            application.setStatus(ApplicationStatus.APPROVED);
        } else if (request.getDecision() == ReviewDecision.REJECTED) {
            application.setStatus(ApplicationStatus.REJECTED);
        } else if (request.getDecision() == ReviewDecision.REQUEST_CORRECTION) {
            application.setStatus(ApplicationStatus.NEEDS_CORRECTION);
        }

        application.setNotes(request.getComments());
        applicationRepository.save(application);

        ApplicationReview applicationReview = ApplicationReview.builder()
                .application(application)
                .reviewer(reviewer)
                .decision(request.getDecision())
                .comments(request.getComments())
                .build();

        reviewRepository.save(applicationReview);
        return mapToResponse(applicationReview);
    }

    public List<ReviewResponse> getReviewsByApplicationId(Long applicationId) {
        return reviewRepository.findByApplicationId(applicationId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public List<Application> getSubmittedApplications() {
        return applicationRepository.findByStatus(ApplicationStatus.SUBMITTED);
    }

    private ReviewResponse mapToResponse(ApplicationReview review) {
        return ReviewResponse.builder()
                .id(review.getId())
                .applicationId(review.getApplication().getId())
                .reviewerName(review.getReviewer().getName())
                .decision(review.getDecision())
                .comments(review.getComments())
                .reviewedAt(review.getReviewedAt())
                .build();
    }
}