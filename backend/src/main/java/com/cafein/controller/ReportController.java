package com.cafein.controller;

import com.cafein.dto.report.CreateReport.CreateReportInput;
import com.cafein.response.Response;
import com.cafein.service.ReportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
@Slf4j
public class ReportController {

    private final ReportService reportService;

    /**
     * 리뷰 신고 등록 API [POST] /api/reports
     * 
     * @return ResponseEntity<Response<Object>>
     */
    // Body
    @PostMapping
    public ResponseEntity<Response<Object>> createReport(@RequestBody CreateReportInput createReportInput) {
        log.info("[POST] /api/reports");
        return reportService.createReport(createReportInput);
    }

}
