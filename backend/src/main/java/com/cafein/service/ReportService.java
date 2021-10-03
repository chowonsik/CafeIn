package com.cafein.service;

import com.cafein.dto.report.createreport.CreateReportInput;
import com.cafein.dto.report.selectreport.SelectReportInput;
import com.cafein.dto.report.selectreport.SelectReportOutput;
import com.cafein.response.PageResponse;
import com.cafein.response.Response;
import org.springframework.http.ResponseEntity;

public interface ReportService {
    ResponseEntity<Response<Object>> createReport(CreateReportInput createReportInput);
    ResponseEntity<PageResponse<SelectReportOutput>> selectReport(SelectReportInput selectReportInput);
    ResponseEntity<Response<Object>> deleteReport(int reportId);
}
