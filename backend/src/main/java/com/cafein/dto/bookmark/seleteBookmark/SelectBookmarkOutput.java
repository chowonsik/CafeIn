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
    //카페 영업시간 정보
    private Integer bhourType;
    private Integer bhourWeekType;
    private Integer bhourMon;
    private Integer bhourTue;
    private Integer bhourWed;
    private Integer bhourThu;
    private Integer bhourFri;
    private Integer bhourSat;
    private Integer bhourSun;
    private Date bhourStartTime;
    private Date bhourEndTime;
    private String bhourEtc;

    @QueryProjection
    public SelectBookmarkOutput(int bookmarkId, int userId, int cafeId, String cafeName, String cafeBranch, String cafeArea, String cafeTel, String cafeAddress, String cafeLatitude, String cafeLongitude, String cafeImgUrl, Integer bhourType, Integer bhourWeekType, Integer bhourMon, Integer bhourTue, Integer bhourWed, Integer bhourThu, Integer bhourFri, Integer bhourSat, Integer bhourSun, Date bhourStartTime, Date bhourEndTime, String bhourEtc) {
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
        this.bhourType = bhourType;
        this.bhourWeekType = bhourWeekType;
        this.bhourMon = bhourMon;
        this.bhourTue = bhourTue;
        this.bhourWed = bhourWed;
        this.bhourThu = bhourThu;
        this.bhourFri = bhourFri;
        this.bhourSat = bhourSat;
        this.bhourSun = bhourSun;
        this.bhourStartTime = bhourStartTime;
        this.bhourEndTime = bhourEndTime;
        this.bhourEtc = bhourEtc;
    }
}
