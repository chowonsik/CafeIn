package com.cafein.dao;

import com.cafein.dto.cafe.search.CafeSearchOutput;
import com.cafein.dto.cafe.selectCafeDetail.SelectCafeDetailOutput;
import com.cafein.dto.cafe.suggest.CafeCurationInput;
import com.cafein.dto.cafe.suggest.CafeCurationOutput;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.cafein.dto.cafe.search.CafeSearchInput;

public interface CafeRepositoryCustom {
    SelectCafeDetailOutput findByIdCustom(int id, int userId);
    Page<CafeSearchOutput> findByWordCustom(CafeSearchInput cafeSearchInput, int userId, Pageable pageable);
    Page<CafeCurationOutput> curationCafeByCategory(CafeCurationInput suggestByCategoryInput, int userId, Pageable pageable);
}
