package com.cafein.service;

import com.cafein.dto.review.createreview.CreateReviewInput;
import com.cafein.dto.review.selectreview.SelectReviewInput;
import com.cafein.dto.review.selectreview.SelectReviewOutput;
import com.cafein.dto.review.updatereview.UpdateReviewInput;
import com.cafein.response.PageResponse;
import com.cafein.response.Response;
import org.springframework.http.ResponseEntity;

public interface ReviewService {
    ResponseEntity<Response<Object>> createReview(CreateReviewInput createReviewInput);
    ResponseEntity<PageResponse<SelectReviewOutput>> selectReview(SelectReviewInput selectReviewInput);
    ResponseEntity<Response<Object>> updateReview(UpdateReviewInput updateReviewInput, int reviewId);
}
