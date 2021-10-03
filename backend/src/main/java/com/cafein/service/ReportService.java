package com.cafein.service;

import com.cafein.dto.report.CreateReport.CreateReportInput;
import com.cafein.response.Response;
import org.springframework.http.ResponseEntity;

public interface ReportService {
    ResponseEntity<Response<Object>> createReport(CreateReportInput createReportInput);
}
