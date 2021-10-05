package com.cafein.dto.cafe.suggest;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CafeCurationInput {
    private int type;           // 1이면 category로 추천, 2면 user의 찜 목록으로 추천
    private String category;    // 추천 속성
    private String latitude;    // 위도
    private String longitude;   // 경도
    private String distance;    // 거리 제한
    private int page;
    private int size;
}
