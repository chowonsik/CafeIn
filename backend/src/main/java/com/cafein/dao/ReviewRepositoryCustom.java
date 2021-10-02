package com.cafein.dao;

import com.cafein.dto.review.selectreview.SelectReviewInput;
import com.cafein.dto.review.selectreview.SelectReviewOutput;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReviewRepositoryCustom {
    Page<SelectReviewOutput> findByDynamicQuery(SelectReviewInput selectReviewInput, Pageable pageable);
}
