package com.cafein.dto.report.selectreport;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter

public class SelectReportInput {
    private int page;
    private int size;
}
