package com.cafein.dto.review.selectreview;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter

public class SelectReviewInput {
    private Integer cafeId;
    private Integer userId;
    private String search;
    private int page;
    private int size;
}
