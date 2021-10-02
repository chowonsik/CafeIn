package com.cafein.dto.review.updatereview;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter

public class UpdateReviewInput {
    private Integer totalScore;
    private String content;
}
