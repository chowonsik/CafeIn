package com.cafein.service;

import com.cafein.dto.cafe.search.CafeSearchOutput;
import com.cafein.dto.cafe.search.CafeSearchInput;
import com.cafein.dto.cafe.selectCafeDetail.SelectCafeDetailOutput;
import com.cafein.entity.Cafe;
import com.cafein.response.PageResponse;
import com.cafein.response.Response;
import org.springframework.data.domain.Pageable;

public interface CafeService {
    Response<SelectCafeDetailOutput> selectCafe(int id);
    PageResponse<CafeSearchOutput> selectCafeListByWord(CafeSearchInput cafeSearchInput, Pageable pageable);
}
