package com.cafein.dto.bookmark.seleteBookmark;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@NoArgsConstructor
@Getter
@Setter
public class SelectBookmarkOutput {
    //찜 id
    private int bookmarkId;
    //유저 정보
    private int userId;
    //카페 정보
    private int cafeId;
    private String cafeName;
    private String cafeBranch;
    private String cafeArea;
    private String cafeTel;
    private String cafeAddress;
    private String cafeLatitude;
    private String cafeLongitude;
    private String cafeImgUrl;
    private Integer bookmarkCnt; //찜 되어진 수
    private Integer reviewCnt; //리뷰 수

    @QueryProjection
    public SelectBookmarkOutput(int bookmarkId, int userId, int cafeId, String cafeName, String cafeBranch, String cafeArea, String cafeTel, String cafeAddress, String cafeLatitude, String cafeLongitude, String cafeImgUrl, Integer bookmarkCnt, Integer reviewCnt) {
        this.bookmarkId = bookmarkId;
        this.userId = userId;
        this.cafeId = cafeId;
        this.cafeName = cafeName;
        this.cafeBranch = cafeBranch;
        this.cafeArea = cafeArea;
        this.cafeTel = cafeTel;
        this.cafeAddress = cafeAddress;
        this.cafeLatitude = cafeLatitude;
        this.cafeLongitude = cafeLongitude;
        this.cafeImgUrl = cafeImgUrl;
        this.bookmarkCnt = bookmarkCnt;
        this.reviewCnt = reviewCnt;
    }
}
