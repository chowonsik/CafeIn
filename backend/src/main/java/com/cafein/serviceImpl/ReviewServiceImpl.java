package com.cafein.serviceImpl;

import com.cafein.configuration.ValidationCheck;
import com.cafein.dao.CafeRepository;
import com.cafein.dao.ReviewRepository;
import com.cafein.dto.review.createreview.CreateReviewInput;
import com.cafein.dto.review.selectreview.SelectReviewInput;
import com.cafein.dto.review.selectreview.SelectReviewOutput;
import com.cafein.dto.review.updatereview.UpdateReviewInput;
import com.cafein.entity.Cafe;
import com.cafein.entity.Review;
import com.cafein.response.PageResponse;
import com.cafein.response.Response;
import com.cafein.service.JwtService;
import com.cafein.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.cafein.response.ResponseStatus.*;

@Service("ReviewService")
@RequiredArgsConstructor
@Slf4j
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final CafeRepository cafeRepository;
    private final JwtService jwtService;

    @Override
    @Transactional
    public ResponseEntity<Response<Object>> createReview(CreateReviewInput createReviewInput) {
        // 1. 값 형식 체크
        if (createReviewInput == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new Response<>(NO_VALUES));
        if (!ValidationCheck.isValidId(createReviewInput.getCafeId()))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new Response<>(BAD_CAFE_ID));
        if (!ValidationCheck.isValid(createReviewInput.getContent()))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new Response<>(NO_CONTENTS));
        if (!ValidationCheck.isValidScore(createReviewInput.getTotalScore()))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new Response<>(BAD_SCORE_VALUE));

        // 2. 리뷰 생성
        Review review;
        try {
            Cafe cafe = cafeRepository.findById(createReviewInput.getCafeId()).orElse(null);
            review = Review.builder()
                    .user(jwtService.getUser())
                    .cafe(cafe)
                    .content(createReviewInput.getContent())
                    .totalScore(createReviewInput.getTotalScore())
                    .build();

            reviewRepository.save(review);

        } catch (Exception e) {
            log.error("[reviews/post] database error", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Response<>(DATABASE_ERROR));
        }

        // 3. 결과 return
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new Response<>(null, CREATED_REVIEW));
    }

    @Override
    public ResponseEntity<PageResponse<SelectReviewOutput>> selectReview(SelectReviewInput selectReviewInput) {
        // 1. 값 형식 체크
        if (selectReviewInput == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new PageResponse<>(NO_VALUES));

        // 2. 리뷰 조회
        Pageable pageable = PageRequest.of(selectReviewInput.getPage() - 1, selectReviewInput.getSize());
        Page<SelectReviewOutput> selectReviewOutput;
        try {
            selectReviewOutput = reviewRepository.findByDynamicQuery(selectReviewInput, pageable);

        } catch (Exception e) {
            log.error("[reviews/get] database error", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new PageResponse<>(DATABASE_ERROR));
        }

        // 3. 결과 return
        return ResponseEntity.status(HttpStatus.OK)
                .body(new PageResponse<>(selectReviewOutput, SUCCESS_SELECT_REVIEW));
    }

    @Override
    @Transactional
    public ResponseEntity<Response<Object>> updateReview(UpdateReviewInput updateReviewInput, int reviewId) {
        try {
            // 1. 리뷰 조회
            Review review = reviewRepository.findById(reviewId).orElse(null);

            // 2. 리뷰 수정
            if (review == null)
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new Response<>(BAD_ID_VALUE));
            if (StringUtils.isNotBlank(updateReviewInput.getContent()))
                review.setContent(updateReviewInput.getContent());
            if (updateReviewInput.getTotalScore() != null) {
                if (!ValidationCheck.isValidScore(updateReviewInput.getTotalScore()))
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body(new Response<>(BAD_SCORE_VALUE));
                review.setTotalScore(updateReviewInput.getTotalScore());
            }

            reviewRepository.save(review);

        } catch (Exception e) {
            log.error("[reviews/patch] database error", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Response<>(DATABASE_ERROR));
        }

        // 3. 결과 return
        return ResponseEntity.status(HttpStatus.OK)
                .body(new Response<>(null, SUCCESS_UPDATE_REVIEW));
    }

    @Override
    @Transactional
    public ResponseEntity<Response<Object>> deleteReview(int reviewId) {
        try {
            // 1. 리뷰 조회
            Review review = reviewRepository.findById(reviewId).orElse(null);

            // 2. 리뷰 삭제
            if (review == null)
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new Response<>(BAD_ID_VALUE));

            reviewRepository.delete(review);

        } catch (Exception e) {
            log.error("[reviews/delete] database error", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Response<>(DATABASE_ERROR));
        }

        // 3. 결과 return
        return ResponseEntity.status(HttpStatus.OK)
                .body(new Response<>(null, SUCCESS_DELETE_REVIEW));
    }
}
