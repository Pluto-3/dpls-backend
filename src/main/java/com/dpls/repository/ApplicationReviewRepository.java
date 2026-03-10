package com.dpls.repository;

import com.dpls.review.ApplicationReview;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApplicationReviewRepository extends JpaRepository<ApplicationReview, Long> {
    List<ApplicationReview> findByApplicationId(Long applicationId);
}