package com.cafein.serviceImpl;

import com.cafein.configuration.ValidationCheck;
import com.cafein.dao.ReportRepository;
import com.cafein.dao.ReviewRepository;
import com.cafein.dto.report.createreport.CreateReportInput;
import com.cafein.dto.report.selectreport.SelectReportInput;
import com.cafein.dto.report.selectreport.SelectReportOutput;
import com.cafein.dto.review.selectreview.SelectReviewInput;
import com.cafein.dto.review.selectreview.SelectReviewOutput;
import com.cafein.entity.Report;
import com.cafein.entity.Review;
import com.cafein.response.PageResponse;
import com.cafein.response.Response;
import com.cafein.service.JwtService;
import com.cafein.service.ReportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.cafein.response.ResponseStatus.*;

@Service("ReportService")
@RequiredArgsConstructor
@Slf4j
public class ReportServiceImpl implements ReportService {

    private final ReviewRepository reviewRepository;
    private final ReportRepository reportRepository;
    private final JwtService jwtService;

    @Override
    @Transactional
    public ResponseEntity<Response<Object>> createReport(CreateReportInput createReportInput) {
        // 1. 값 형식 체크
        if (createReportInput == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new Response<>(NO_VALUES));
        if (!ValidationCheck.isValidId(createReportInput.getReviewId()))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new Response<>(BAD_CAFE_ID));
        if (!ValidationCheck.isValid(createReportInput.getContent()))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new Response<>(NO_CONTENTS));

        // 2. 리뷰 신고 생성
        Report report;
        try {
            Review review = reviewRepository.findById(createReportInput.getReviewId()).orElse(null);
            report = Report.builder()
                    .user(jwtService.getUser())
                    .review(review)
                    .content(createReportInput.getContent())
                    .build();

            reportRepository.save(report);

        } catch (Exception e) {
            log.error("[reports/post] database error", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Response<>(DATABASE_ERROR));
        }

        // 3. 결과 return
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new Response<>(null, CREATED_REPORT));
    }

    @Override
    public ResponseEntity<PageResponse<SelectReportOutput>> selectReport(SelectReportInput selectReportInput) {
        // 1. 값 형식 체크
        if (selectReportInput == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new PageResponse<>(NO_VALUES));

        // 2. 리뷰 신고 조회
        Pageable pageable = PageRequest.of(selectReportInput.getPage() - 1, selectReportInput.getSize());
        Page<SelectReportOutput> selectReportOutput;
        try {
            selectReportOutput = reportRepository.findReportList(pageable);

        } catch (Exception e) {
            log.error("[reports/get] database error", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new PageResponse<>(DATABASE_ERROR));
        }

        // 3. 결과 return
        return ResponseEntity.status(HttpStatus.OK)
                .body(new PageResponse<>(selectReportOutput, SUCCESS_SELECT_REPORT));
    }

    @Override
    @Transactional
    public ResponseEntity<Response<Object>> deleteReport(int reportId) {
        try {
            // 1. 리뷰 신고 조회
            Report report = reportRepository.findById(reportId).orElse(null);

            // 2. 리뷰 삭제
            if (report == null)
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new Response<>(BAD_ID_VALUE));

            reportRepository.delete(report);

        } catch (Exception e) {
            log.error("[reports/delete] database error", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Response<>(DATABASE_ERROR));
        }

        // 3. 결과 return
        return ResponseEntity.status(HttpStatus.OK)
                .body(new Response<>(null, SUCCESS_DELETE_REPORT));
    }
}
