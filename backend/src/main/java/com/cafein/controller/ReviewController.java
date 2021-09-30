package com.cafein.controller;

import com.cafein.dto.review.createreview.CreateReviewInput;
import com.cafein.dto.user.email.EmailInput;
import com.cafein.dto.user.email.EmailOutput;
import com.cafein.dto.user.jwt.JwtOutput;
import com.cafein.dto.user.selectprofile.SelectProfileOutput;
import com.cafein.dto.user.signin.SignInInput;
import com.cafein.dto.user.signin.SignInOutput;
import com.cafein.dto.user.signup.SignUpInput;
import com.cafein.dto.user.signup.SignUpOutput;
import com.cafein.dto.user.updateprofile.UpdateProfileInput;
import com.cafein.response.Response;
import com.cafein.service.JwtService;
import com.cafein.service.ReviewService;
import com.cafein.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.cafein.response.ResponseStatus.*;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
@Slf4j
public class ReviewController {

    private final ReviewService reviewService;

    /**
     * 리뷰 작성 API [POST] /api/reviews
     * 
     * @return ResponseEntity<Response<SignUpOutput>>
     */
    // Body
    @PostMapping
    public ResponseEntity<Response<Object>> createReview(@RequestBody CreateReviewInput createReviewInput) {
        log.info("[POST] /api/reviews");
        return reviewService.createReview(createReviewInput);
    }

}
