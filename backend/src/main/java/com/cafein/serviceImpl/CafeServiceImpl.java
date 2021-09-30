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
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import static com.cafein.response.ResponseStatus.*;

@Service("CafeService")
@RequiredArgsConstructor
@Slf4j
public class CafeServiceImpl implements CafeService {

    private final CafeRepository cafeRepository;

    @Override
    public Response<SelectCafeDetailOutput> selectCafe(int id) {
        // 값 형식 체크
        if(!ValidationCheck.isValidId(id))
            return new Response<>(NO_VALUES);

        SelectCafeDetailOutput selectCafeDetailOutput = cafeRepository.findByIdCustom(id);
        if(selectCafeDetailOutput==null) return new Response<>(BAD_ID_VALUE);

        // 결과 return
        return new Response<>(selectCafeDetailOutput, SUCCESS_SELECT_CAFE);

    }

    @Override
    public PageResponse<CafeSearchOutput> selectCafeListByWord(CafeSearchInput cafeSearchInput, Pageable pageable) {

        // 값 형식 체크
        if(cafeSearchInput == null)
            return new PageResponse<>(NO_VALUES);

        Page<CafeSearchOutput> cafeSearchOutput;
        try {
            cafeSearchOutput = cafeRepository.findByWordCustom(cafeSearchInput, pageable);
        } catch (Exception e) {
            log.error("[GET]/cafe database error", e);
            return new PageResponse<>(DATABASE_ERROR);
        }
        // 결과 return
        return new PageResponse<>(cafeSearchOutput, SUCCESS_SELECT_CAFE);
    }



}
