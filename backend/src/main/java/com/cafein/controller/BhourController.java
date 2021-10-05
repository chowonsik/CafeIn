package com.cafein.controller;

import com.cafein.dto.bhour.selectbhour.SelectBhourInput;
import com.cafein.dto.bhour.selectbhour.SelectBhourOutput;
import com.cafein.response.Response;
import com.cafein.service.BhourService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/bhours")
@RequiredArgsConstructor
@Slf4j
public class BhourController {

    private final BhourService bhourService;

    /**
     * 영업시간 조회 API [GET] /api/bhours
     *
     * @return ResponseEntity<Response<List<SelectBhourOutput>>>
     */

    @GetMapping
    public ResponseEntity<Response<List<SelectBhourOutput>>> selectBhour(SelectBhourInput selectBhourInput) {
        log.info("[GET] /api/bhours");
        return bhourService.selectBhour(selectBhourInput);
    }

}
