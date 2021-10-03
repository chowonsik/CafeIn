package com.cafein.dto.report.selectreport;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SelectReportOutput {
    private Integer reportId;
    private Integer userId;
    private Integer reviewId;
    private String reportContent;
    private Date reportCreatedAt;
}
