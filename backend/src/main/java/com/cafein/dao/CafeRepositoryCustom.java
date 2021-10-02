package com.cafein.dao;

import com.cafein.dto.cafe.search.CafeSearchOutput;
import com.cafein.dto.cafe.selectCafeDetail.SelectCafeDetailOutput;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.cafein.dto.cafe.search.CafeSearchInput;

public interface CafeRepositoryCustom {
    SelectCafeDetailOutput findByIdCustom(int id);
    Page<CafeSearchOutput> findByWordCustom(CafeSearchInput cafeSearchInput, Pageable pageable);
}
