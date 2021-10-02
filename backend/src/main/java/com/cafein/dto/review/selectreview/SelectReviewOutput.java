package com.cafein.dto.review.selectreview;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@NoArgsConstructor
@Getter
@Setter
public class SelectReviewOutput {
    private Integer cafeId;
    private String cafeName;
    private Integer reviewId;
    private Integer userId;
    private String reviewContent;
    private Integer reviewScore;
    private Date reviewCreatedAt;

    @QueryProjection
    public SelectReviewOutput(Integer cafeId, String cafeName, Integer reviewId, Integer userId, String reviewContent, Integer reviewScore, Date reviewCreatedAt) {
        this.cafeId = cafeId;
        this.cafeName = cafeName;
        this.reviewId = reviewId;
        this.userId = userId;
        this.reviewContent = reviewContent;
        this.reviewScore = reviewScore;
        this.reviewCreatedAt = reviewCreatedAt;
    }
}
