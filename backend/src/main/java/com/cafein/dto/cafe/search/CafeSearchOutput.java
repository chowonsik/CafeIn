package com.cafein.dto.cafe.search;

import com.cafein.entity.Bhour;
import com.querydsl.core.annotations.QueryProjection;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;

import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

@NoArgsConstructor
@Getter
@Setter
public class CafeSearchOutput {
    private Integer cafeId;
    private String cafeName;
    private String cafeBranch;
    private String cafeArea;
    private String cafeTel;
    private String cafeAddress;
    private String cafeLatitude;
    private String cafeLongitude;
    private String cafeDistance;
    private String imgUrl;
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
    private Date bhourStartTime;
    private Date bhourEndTime;
    private String bhourEtc;

    @QueryProjection
    public CafeSearchOutput(Integer cafeId, String cafeName, String cafeBranch, String cafeArea, String cafeTel, String cafeAddress, String cafeLatitude, String cafeLongitude, String cafeDistance, String imgUrl, Integer bhourType, Integer bhourWeekType, Integer bhourMon, Integer bhourTue, Integer bhourWed, Integer bhourThu, Integer bhourFri, Integer bhourSat, Integer bhourSun, Date bhourStartTime, Date bhourEndTime, String bhourEtc) {
        this.cafeId = cafeId;
        this.cafeName = cafeName;
        this.cafeBranch = cafeBranch;
        this.cafeArea = cafeArea;
        this.cafeTel = cafeTel;
        this.cafeAddress = cafeAddress;
        this.cafeLatitude = cafeLatitude;
        this.cafeLongitude = cafeLongitude;
        this.cafeDistance = cafeDistance;
        this.imgUrl = imgUrl;
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
