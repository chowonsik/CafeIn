package com.cafein.dto.review.createreview;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter

public class CreateReviewInput {
    private int cafeId;
    private int totalScore;
    private String content;
}
