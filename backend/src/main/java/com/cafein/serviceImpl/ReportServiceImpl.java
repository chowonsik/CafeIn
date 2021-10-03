package com.cafein.serviceImpl;

import com.cafein.configuration.ValidationCheck;
import com.cafein.dao.ReportRepository;
import com.cafein.dao.ReviewRepository;
import com.cafein.dto.report.CreateReport.CreateReportInput;
import com.cafein.entity.Report;
import com.cafein.entity.Review;
import com.cafein.response.Response;
import com.cafein.service.JwtService;
import com.cafein.service.ReportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
}
