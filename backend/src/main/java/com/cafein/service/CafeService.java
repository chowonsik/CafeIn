package com.cafein.service;

import com.cafein.dto.cafe.search.CafeSearchOutput;
import com.cafein.dto.cafe.search.CafeSearchInput;
import com.cafein.dto.cafe.selectCafeDetail.SelectCafeDetailOutput;
import com.cafein.dto.cafe.suggest.CafeCurationInput;
import com.cafein.dto.cafe.suggest.CafeCurationOutput;
import com.cafein.response.PageResponse;
import com.cafein.response.Response;
import org.springframework.http.ResponseEntity;

public interface CafeService {
    ResponseEntity<Response<SelectCafeDetailOutput>> selectCafe(int id);
    ResponseEntity<PageResponse<CafeSearchOutput>> selectCafeListByWord(CafeSearchInput cafeSearchInput);
    ResponseEntity<PageResponse<CafeCurationOutput>> curationCafe(CafeCurationInput suggestByCategoryInput);
}
