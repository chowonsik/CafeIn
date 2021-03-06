package com.cafein.controller;

import com.cafein.dto.cafe.search.CafeSearchOutput;
import com.cafein.dto.cafe.search.CafeSearchInput;
import com.cafein.dto.cafe.selectCafeDetail.SelectCafeDetailOutput;
import com.cafein.dto.cafe.suggest.CafeCurationInput;
import com.cafein.dto.cafe.suggest.CafeCurationOutput;
import com.cafein.response.PageResponse;
import com.cafein.response.Response;
import com.cafein.service.CafeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cafes")
@RequiredArgsConstructor
@Slf4j
public class CafeController {

    private final CafeService cafeService;

    /**
     * 카페 상세 정보 조회 API [GET] /api/cafes/{id}
     * @param id 검색하고 싶은 카페의 id
     * @return ResponseEntity<Response<SelectCafeDetailOutput>>
     */
    // Params
    @GetMapping("/{id}")
    public ResponseEntity<Response<SelectCafeDetailOutput>> getCafeInfo(@PathVariable int id){
        log.info("[GET] /cafes/{id}");
        return cafeService.selectCafe(id);
    }

    /**
     * 카페 단어로 검색 API [GET] /api/cafes
     *
     * @return ResponseEntity<PageResponse<CafeSearchOutput>>
     */
    // Params
    @GetMapping
    public ResponseEntity<PageResponse<CafeSearchOutput>> searchCafeByWord(CafeSearchInput cafeSearchInput) {
        log.info("[GET] /cafes");
        return cafeService.selectCafeListByWord(cafeSearchInput);
    }

    /**
     * 카페 카테로기로 추천 API [GET] /api/cafes/suggest/
     *
     * @return ResponseEntity<PageResponse<CafeSearchOutput>>
     */
    // Params
    @GetMapping("/curation")
    public ResponseEntity<PageResponse<CafeCurationOutput>> suggestByCategory(CafeCurationInput suggestByCategoryInput) {
        log.info("[GET] /cafes/curation");
        return cafeService.curationCafe(suggestByCategoryInput);
    }

}
