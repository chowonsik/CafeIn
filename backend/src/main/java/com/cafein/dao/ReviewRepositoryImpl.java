package com.cafein.dao;

import com.cafein.dto.review.selectreview.SelectReviewInput;
import com.cafein.dto.review.selectreview.SelectReviewOutput;
import com.cafein.dto.review.selectreview.QSelectReviewOutput;
import com.cafein.entity.QCafe;
import com.cafein.entity.QReview;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import java.util.List;

@RequiredArgsConstructor
public class ReviewRepositoryImpl implements ReviewRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    QCafe qCafe = QCafe.cafe;
	QReview qReview = QReview.review;

	@Override
	public Page<SelectReviewOutput> findByDynamicQuery(SelectReviewInput selectReviewInput, Pageable pageable) {

		QueryResults<SelectReviewOutput> queryResult = queryFactory
				.select(new QSelectReviewOutput(
						qCafe.id,
						qCafe.name,
						qCafe.imgUrl,
						qReview.id,
						qReview.user.id,
						qReview.content,
						qReview.totalScore,
						qReview.created_at
				))
				.from(qReview)
				.join(qCafe)
				.on(qReview.cafe.id.eq(qCafe.id))
				.where(eqSearch(selectReviewInput.getSearch()), eqCafeId(selectReviewInput.getCafeId()), eqUserId(selectReviewInput.getUserId()))
				.orderBy(qReview.created_at.desc())
				.offset(pageable.getOffset()).limit(pageable.getPageSize())
				.fetchResults();
		long totalCount = queryResult.getTotal();
		List<SelectReviewOutput> content = queryResult.getResults();

		return new PageImpl<>(content, pageable, totalCount);
	}

	private BooleanExpression eqSearch(String search) {
		if (StringUtils.isEmpty(search)) {
			return null;
		}
		return qReview.content.contains(search);
	}

	private BooleanExpression eqCafeId(Integer cafeId) {
		if (StringUtils.isEmpty(cafeId)) {
			return null;
		}
		return qCafe.id.eq(cafeId);
	}

	private BooleanExpression eqUserId(Integer userId) {
		if (StringUtils.isEmpty(userId)) {
			return null;
		}
		return qReview.user.id.eq(userId);
	}

}

