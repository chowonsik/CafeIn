package com.cafein.serviceImpl;

import com.cafein.configuration.AES128;
import com.cafein.configuration.ValidationCheck;
import com.cafein.dao.CafeRepository;
import com.cafein.dao.ReviewRepository;
import com.cafein.dao.UserRepository;
import com.cafein.dto.review.createreview.CreateReviewInput;
import com.cafein.dto.user.email.EmailInput;
import com.cafein.dto.user.email.EmailOutput;
import com.cafein.dto.user.selectprofile.SelectProfileOutput;
import com.cafein.dto.user.signin.SignInInput;
import com.cafein.dto.user.signin.SignInOutput;
import com.cafein.dto.user.signup.SignUpInput;
import com.cafein.dto.user.signup.SignUpOutput;
import com.cafein.dto.user.updateprofile.UpdateProfileInput;
import com.cafein.entity.Cafe;
import com.cafein.entity.Review;
import com.cafein.entity.User;
import com.cafein.response.Response;
import com.cafein.service.JwtService;
import com.cafein.service.ReviewService;
import com.cafein.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
}
