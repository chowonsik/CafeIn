package com.cafein.dao;

import com.cafein.dto.report.selectreport.SelectReportOutput;
import com.cafein.entity.Report;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportRepository extends JpaRepository<Report, Integer> {

    @Query("select new com.cafein.dto.report.selectreport.SelectReportOutput(r.id, r.user.id, r.review.id, r.content, r.created_at)"
            + " from Report r"
    )
    Page<SelectReportOutput> findReportList(Pageable pageable);
}