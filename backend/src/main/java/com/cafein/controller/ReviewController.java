package com.cafein.controller;

import com.cafein.dto.review.createreview.CreateReviewInput;
import com.cafein.dto.review.selectreview.SelectReviewInput;
import com.cafein.dto.review.selectreview.SelectReviewOutput;
import com.cafein.dto.review.updatereview.UpdateReviewInput;
import com.cafein.response.PageResponse;
import com.cafein.response.Response;
import com.cafein.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
@Slf4j
public class ReviewController {

    private final ReviewService reviewService;

    /**
     * 리뷰 작성 API [POST] /api/reviews
     * 
     * @return ResponseEntity<Response<Object>>
     */
    // Body
    @PostMapping
    public ResponseEntity<Response<Object>> createReview(@RequestBody CreateReviewInput createReviewInput) {
        log.info("[POST] /api/reviews");
        return reviewService.createReview(createReviewInput);
    }

    /**
     * 리뷰 조회 API [GET] /api/reviews
     *
     * @return ResponseEntity<PageResponse<SelectReviewOutput>>
     */

    @GetMapping
    public ResponseEntity<PageResponse<SelectReviewOutput>> selectReview(SelectReviewInput selectReviewInput) {
        log.info("[GET] /api/reviews");
        return reviewService.selectReview(selectReviewInput);
    }

    /**
     * 리뷰 수정 API [PATCH] /api/reviews/{id}
     *
     * @return ResponseEntity<Response<Object>>
     */

    @PatchMapping("/{id}")
    public ResponseEntity<Response<Object>> updateReview(@RequestBody UpdateReviewInput updateReviewInput, @PathVariable("id") int reviewId) {
        log.info("[PATCH] /api/reviews");
        return reviewService.updateReview(updateReviewInput, reviewId);
    }

    /**
     * 리뷰 삭제 API [DELETE] /api/reviews/{id}
     *
     * @return ResponseEntity<Response<Object>>
     */

    @DeleteMapping("/{id}")
    public ResponseEntity<Response<Object>> deleteReview(@PathVariable("id") int reviewId) {
        log.info("[DELETE] /api/reviews");
        return reviewService.deleteReview(reviewId);
    }

}
