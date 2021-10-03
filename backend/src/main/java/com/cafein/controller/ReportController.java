package com.cafein.controller;

import com.cafein.dto.report.createreport.CreateReportInput;
import com.cafein.dto.report.selectreport.SelectReportInput;
import com.cafein.dto.report.selectreport.SelectReportOutput;
import com.cafein.dto.review.selectreview.SelectReviewInput;
import com.cafein.dto.review.selectreview.SelectReviewOutput;
import com.cafein.response.PageResponse;
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

    /**
     * 리뷰 신고 조회 API [GET] /api/reports
     *
     * @return ResponseEntity<PageResponse<SelectReportOutput>>
     */

    @GetMapping
    public ResponseEntity<PageResponse<SelectReportOutput>> selectReview(SelectReportInput selectReportInput) {
        log.info("[GET] /api/reports");
        return reportService.selectReport(selectReportInput);
    }

    /**
     * 리뷰 신고 삭제 API [DELETE] /api/reports/{id}
     *
     * @return ResponseEntity<Response<Object>>
     */

    @DeleteMapping("/{id}")
    public ResponseEntity<Response<Object>> deleteReport(@PathVariable("id") int reportId) {
        log.info("[DELETE] /api/reports");
        return reportService.deleteReport(reportId);
    }

}
