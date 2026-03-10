package com.dpls.review;

import com.dpls.common.enums.ReviewDecision;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ReviewRequest {

    private ReviewDecision decision;

    private String comments;
}