package com.cafein.dto.bhour.selectbhour;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SelectBhourOutput {
    private Integer bhourType;      //영업시간 종류
    private Integer bhourWeekType;  //주단위 종류
    private Integer bhourMon;       //월요일 포함유무
    private Integer bhourTue;       //화요일 포함유무
    private Integer bhourWed;       //수요일 포함유무
    private Integer bhourThu;       //목요일 포함유무
    private Integer bhourFri;       //금요일 포함유무
    private Integer bhourSat;       //토요일 포함유무
    private Integer bhourSun;       //일요일 포함유무
    private String bhourStartTime;  //시작시간
    private String bhourEndTime;    //종료시간
    private String bhourEtc;        //그외정보 ex) 연중무휴
}
