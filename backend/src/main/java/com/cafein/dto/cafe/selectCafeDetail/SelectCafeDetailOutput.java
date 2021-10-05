package com.cafein.dto.cafe.selectCafeDetail;

import com.cafein.entity.Bhour;
import com.cafein.entity.Cafe;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.Date;

@NoArgsConstructor
@Getter
@Setter
public class SelectCafeDetailOutput {
    private Integer cafeId;
    private String cafeName;
    private String cafeBranch;
    private String cafeArea;
    private String cafeTel;
    private String cafeAddress;
    private String cafeLatitude;
    private String cafeLongitude;
    private String cafeImgUrl;
    private Double cafeAvgScore;    // 카페 평균 평점
    private Integer isBookMark;     // 북마크 한 상태인지 아닌지
    private Integer bookmarkCnt;    // 찜 되어진 수
    private Integer reviewCnt;      // 리뷰 수

    @QueryProjection
    public SelectCafeDetailOutput(Integer cafeId, String cafeName, String cafeBranch, String cafeArea, String cafeTel, String cafeAddress, String cafeLatitude, String cafeLongitude, String cafeImgUrl, Double cafeAvgScore, Integer isBookMark, Integer bookmarkCnt, Integer reviewCnt) {
        this.cafeId = cafeId;
        this.cafeName = cafeName;
        this.cafeBranch = cafeBranch;
        this.cafeArea = cafeArea;
        this.cafeTel = cafeTel;
        this.cafeAddress = cafeAddress;
        this.cafeLatitude = cafeLatitude;
        this.cafeLongitude = cafeLongitude;
        this.cafeImgUrl = cafeImgUrl;
        this.cafeAvgScore = cafeAvgScore;
        this.isBookMark = isBookMark;
        this.bookmarkCnt = bookmarkCnt;
        this.reviewCnt = reviewCnt;
    }
}
