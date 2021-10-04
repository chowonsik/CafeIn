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
    private String imgUrl;
    private Integer isBookMark;
    //카페 영업시간 관련
    private Integer bhourType;
    private Integer bhourWeekType;
    private Integer bhourMon;
    private Integer bhourTue;
    private Integer bhourWed;
    private Integer bhourThu;
    private Integer bhourFri;
    private Integer bhourSat;
    private Integer bhourSun;
    private String bhourStartTime;
    private String bhourEndTime;
    private String bhourEtc;

    @QueryProjection
    public SelectCafeDetailOutput(Integer cafeId, String cafeName, String cafeBranch, String cafeArea, String cafeTel, String cafeAddress, String cafeLatitude, String cafeLongitude, String imgUrl, Integer isBookMark, Integer bhourType, Integer bhourWeekType, Integer bhourMon, Integer bhourTue, Integer bhourWed, Integer bhourThu, Integer bhourFri, Integer bhourSat, Integer bhourSun, String bhourStartTime, String bhourEndTime, String bhourEtc) {
        this.cafeId = cafeId;
        this.cafeName = cafeName;
        this.cafeBranch = cafeBranch;
        this.cafeArea = cafeArea;
        this.cafeTel = cafeTel;
        this.cafeAddress = cafeAddress;
        this.cafeLatitude = cafeLatitude;
        this.cafeLongitude = cafeLongitude;
        this.imgUrl = imgUrl;
        this.isBookMark = isBookMark;
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
