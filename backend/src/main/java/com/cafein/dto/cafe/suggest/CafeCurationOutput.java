package com.cafein.dto.cafe.suggest;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CafeCurationOutput {
    private Integer cafeId;
    private String cafeName;
    private String cafeBranch;
    private String cafeArea;
    private String cafeTel;
    private String cafeAddress;
    private String cafeLatitude;
    private String cafeLongitude;
    private String cafeDistance;    // 유저 현재 위치에서 거리
    private String cafeImgUrl;
    private Double cafeAvgScore;    // 카페 평균 평점
    private Integer isBookMark;     // 북마크 한 상태인지 아닌지
    private Integer bookmarkCnt;    // 찜 되어진 수
    private Integer reviewCnt;      // 리뷰 수

    @QueryProjection
    public CafeCurationOutput(Integer cafeId, String cafeName, String cafeBranch, String cafeArea, String cafeTel, String cafeAddress, String cafeLatitude, String cafeLongitude, String cafeDistance, String cafeImgUrl, Double cafeAvgScore, Integer isBookMark, Integer bookmarkCnt, Integer reviewCnt) {
        this.cafeId = cafeId;
        this.cafeName = cafeName;
        this.cafeBranch = cafeBranch;
        this.cafeArea = cafeArea;
        this.cafeTel = cafeTel;
        this.cafeAddress = cafeAddress;
        this.cafeLatitude = cafeLatitude;
        this.cafeLongitude = cafeLongitude;
        this.cafeDistance = cafeDistance;
        this.cafeImgUrl = cafeImgUrl;
        this.cafeAvgScore = cafeAvgScore;
        this.isBookMark = isBookMark;
        this.bookmarkCnt = bookmarkCnt;
        this.reviewCnt = reviewCnt;
    }
}
