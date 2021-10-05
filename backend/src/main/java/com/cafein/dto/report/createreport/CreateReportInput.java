package com.cafein.dto.report.createreport;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter

public class CreateReportInput {
    private int reviewId;
    private String content;
}
