package com.cafein.service;

import com.cafein.dto.review.createreview.CreateReviewInput;
import com.cafein.response.Response;
import org.springframework.http.ResponseEntity;

public interface ReviewService {
    ResponseEntity<Response<Object>> createReview(CreateReviewInput createReviewInput);
}
