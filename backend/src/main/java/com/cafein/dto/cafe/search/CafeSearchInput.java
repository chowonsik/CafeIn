package com.cafein.dto.cafe.search;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CafeSearchInput {
    String latitude; //유저 현재 위치 - 위도
    String longitude; //유저 현재 위치 - 경도
	String search; //검색 단어
    int page;
    int size;
}
