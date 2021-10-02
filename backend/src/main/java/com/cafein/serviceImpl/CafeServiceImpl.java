package com.cafein.serviceImpl;

import com.cafein.configuration.ValidationCheck;
import com.cafein.dao.CafeRepository;
import com.cafein.dto.cafe.search.CafeSearchOutput;
import com.cafein.dto.cafe.search.CafeSearchInput;
import com.cafein.dto.cafe.selectCafeDetail.SelectCafeDetailOutput;
import com.cafein.entity.Cafe;
import com.cafein.response.PageResponse;
import com.cafein.response.Response;
import com.cafein.service.CafeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import static com.cafein.response.ResponseStatus.*;

@Service("CafeService")
@RequiredArgsConstructor
@Slf4j
public class CafeServiceImpl implements CafeService {

    private final CafeRepository cafeRepository;

    @Override
    public ResponseEntity<Response<SelectCafeDetailOutput>> selectCafe(int id) {
        // 값 형식 체크
        if(!ValidationCheck.isValidId(id))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new Response<>(NO_VALUES));

        SelectCafeDetailOutput selectCafeDetailOutput = cafeRepository.findByIdCustom(id);
        if(selectCafeDetailOutput==null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new Response<>(BAD_ID_VALUE));

        // 결과 return
        return ResponseEntity.status(HttpStatus.OK)
                .body(new Response<>(selectCafeDetailOutput, SUCCESS_SELECT_CAFE));
    }

    @Override
    public ResponseEntity<PageResponse<CafeSearchOutput>> selectCafeListByWord(CafeSearchInput cafeSearchInput) {

        // 값 형식 체크
        if(cafeSearchInput == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new PageResponse<>(NO_VALUES));
        if(cafeSearchInput.getSearch() == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new PageResponse<>(NO_VALUES));

        Page<CafeSearchOutput> cafeSearchOutput;
        Pageable pageable = PageRequest.of(cafeSearchInput.getPage()-1, cafeSearchInput.getSize(), Sort.Direction.ASC,
                "cafeDistance");

        try {
            cafeSearchOutput = cafeRepository.findByWordCustom(cafeSearchInput, pageable);
        } catch (Exception e) {
            log.error("[GET]/cafe database error", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new PageResponse<>(DATABASE_ERROR));
        }
        // 결과 return
        return ResponseEntity.status(HttpStatus.OK)
                .body(new PageResponse<>(cafeSearchOutput, SUCCESS_SELECT_CAFE));
    }



}
