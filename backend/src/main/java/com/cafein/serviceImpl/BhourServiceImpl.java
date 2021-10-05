package com.cafein.serviceImpl;

import com.cafein.configuration.ValidationCheck;
import com.cafein.dao.BhourRepository;
import com.cafein.dto.bhour.selectbhour.SelectBhourInput;
import com.cafein.dto.bhour.selectbhour.SelectBhourOutput;
import com.cafein.response.Response;
import com.cafein.service.BhourService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.cafein.response.ResponseStatus.*;

@Service("BhourService")
@RequiredArgsConstructor
@Slf4j
public class BhourServiceImpl implements BhourService {

    private final BhourRepository bhourRepository;

    @Override
    public ResponseEntity<Response<List<SelectBhourOutput>>> selectBhour(SelectBhourInput selectBhourInput) {
        // 1. 값 형식 체크
        if (selectBhourInput == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new Response<>(NO_VALUES));
        if (!ValidationCheck.isValidId(selectBhourInput.getCafeId()))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new Response<>(BAD_CAFE_ID));

        // 2. 영업시간 조회
        List<SelectBhourOutput> selectBhourOutput;
        try {
            selectBhourOutput = bhourRepository.findByCafeId(selectBhourInput.getCafeId());

        } catch (Exception e) {
            log.error("[bhours/get] database error", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Response<>(DATABASE_ERROR));
        }

        // 3. 결과 return
        return ResponseEntity.status(HttpStatus.OK)
                .body(new Response<>(selectBhourOutput, SUCCESS_SELECT_BHOUR));
    }
}
