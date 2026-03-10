package com.dpls.review;

import com.dpls.common.enums.ReviewDecision;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ReviewResponse {
    private Long id;
    private Long applicationId;
    private String reviewerName;
    private ReviewDecision decision;
    private String comments;
    private LocalDateTime reviewedAt;
}