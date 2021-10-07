package com.cafein.dao;

import java.util.ArrayList;
import java.util.List;

import com.cafein.dto.cafe.search.CafeSearchOutput;
import com.cafein.dto.cafe.search.CafeSearchInput;
import com.cafein.dto.cafe.search.QCafeSearchOutput;
import com.cafein.dto.cafe.selectCafeDetail.QSelectCafeDetailOutput;
import com.cafein.dto.cafe.selectCafeDetail.SelectCafeDetailOutput;
import com.cafein.dto.cafe.suggest.CafeCurationInput;
import com.cafein.dto.cafe.suggest.CafeCurationOutput;
import com.cafein.dto.cafe.suggest.QCafeCurationOutput;
import com.cafein.entity.*;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.*;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;

import static com.querydsl.core.types.dsl.MathExpressions.*;

@RequiredArgsConstructor
public class CafeRepositoryImpl implements CafeRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    QCafe qCafe = QCafe.cafe;
	QTags qTags = QTags.tags;
	QBookmark qBookmark = QBookmark.bookmark;
	QReview qReview = QReview.review;

	@Override
	public SelectCafeDetailOutput findByIdCustom(int id, int userId) {
		SelectCafeDetailOutput queryResult = queryFactory
				.select(new QSelectCafeDetailOutput(qCafe.id, qCafe.name, qCafe.branch, qCafe.area, qCafe.tel,
						qCafe.address, qCafe.latitude, qCafe.longitude, qCafe.imgUrl,
						// cafeAvgScore
						JPAExpressions.select(qReview.totalScore.avg().coalesce(0.0)).from(qReview)
								.where(qReview.cafe.id.eq(qCafe.id)),
						// isBookmark
						JPAExpressions.select(qBookmark.count().castToNum(Integer.class)).from(qBookmark)
								.where(qBookmark.user.id.eq(userId).and(qBookmark.cafe.id.eq(qCafe.id))),
						// bookmarkCnt
						JPAExpressions.select(qBookmark.count().castToNum(Integer.class)).from(qBookmark)
								.where(qBookmark.cafe.id.eq(qCafe.id)),
						// reviewCnt
						JPAExpressions.select(qReview.count().castToNum(Integer.class)).from(qReview)
								.where(qReview.cafe.id.eq(qCafe.id))
				))
				.from(qCafe)
				.where(qCafe.id.eq(id))
				.fetchOne();
		System.out.println(queryResult);
		return queryResult;
	}

	@Override
	public Page<CafeSearchOutput> findByWordCustom(CafeSearchInput cafeSearchInput, int userId, Pageable pageable) {
		double userLatitude = Double.parseDouble(cafeSearchInput.getLatitude());
		double userLongitude = Double.parseDouble(cafeSearchInput.getLongitude());

		QueryResults<CafeSearchOutput> queryResult = queryFactory
				.select(new QCafeSearchOutput(qCafe.id, qCafe.name, qCafe.branch, qCafe.area, qCafe.tel, qCafe.address,
						qCafe.latitude, qCafe.longitude,
						//거리 구하기
						Expressions.as(
								acos(cos(radians(Expressions.constant(userLatitude)))
										.multiply(cos(radians(qCafe.latitude.castToNum(Double.class))))
										.multiply(cos(radians(qCafe.longitude.castToNum(Double.class)).subtract(radians(Expressions.constant(userLongitude)))))
										.add((sin(radians(Expressions.constant(userLatitude))).multiply(sin(radians(qCafe.latitude.castToNum(Double.class))))))
						).multiply(Expressions.constant(6371)).stringValue(),"distance"),
						qCafe.imgUrl,
						// cafeAvgScore
						JPAExpressions.select(qReview.totalScore.avg().coalesce(0.0)).from(qReview)
								.where(qReview.cafe.id.eq(qCafe.id)),
						// isBookmark
						JPAExpressions.select(qBookmark.count().castToNum(Integer.class)).from(qBookmark)
								.where(qBookmark.user.id.eq(userId).and(qBookmark.cafe.id.eq(qCafe.id))),
						// bookmarkCnt
						JPAExpressions.select(qBookmark.count().castToNum(Integer.class)).from(qBookmark)
								.where(qBookmark.cafe.id.eq(qCafe.id)),
						// reviewCnt
						JPAExpressions.select(qReview.count().castToNum(Integer.class)).from(qReview)
								.where(qReview.cafe.id.eq(qCafe.id))
				))
				.from(qCafe)
				.where(qCafe.name.contains(cafeSearchInput.getSearch()))
				.orderBy(Expressions.stringPath("distance").asc())
				.offset(pageable.getOffset()).limit(pageable.getPageSize())
				.fetchResults();
		long totalCount = queryResult.getTotal();
		List<CafeSearchOutput> content = queryResult.getResults();

		return new PageImpl<>(content, pageable, totalCount);
	}

	@Override
	public Page<CafeCurationOutput> curationCafe(CafeCurationInput cafeCurationInput, int userId, Pageable pageable) {
		double userLatitude = Double.parseDouble(cafeCurationInput.getLatitude());
		double userLongitude = Double.parseDouble(cafeCurationInput.getLongitude());

		QueryResults<CafeCurationOutput> queryResult = queryFactory
				.select(new QCafeCurationOutput(qCafe.id, qCafe.name, qCafe.branch, qCafe.area, qCafe.tel, qCafe.address,
						qCafe.latitude, qCafe.longitude,
						//거리 구하기
						Expressions.as(
								acos(cos(radians(Expressions.constant(userLatitude)))
										.multiply(cos(radians(qCafe.latitude.castToNum(Double.class))))
										.multiply(cos(radians(qCafe.longitude.castToNum(Double.class)).subtract(radians(Expressions.constant(userLongitude)))))
										.add((sin(radians(Expressions.constant(userLatitude))).multiply(sin(radians(qCafe.latitude.castToNum(Double.class))))))
								).multiply(Expressions.constant(6371)).stringValue(),"distance"),
						qCafe.imgUrl,
						// cafeAvgScore
						JPAExpressions.select(qReview.totalScore.avg().coalesce(0.0)).from(qReview)
								.where(qReview.cafe.id.eq(qCafe.id)),
						// isBookmark
						JPAExpressions.select(qBookmark.count().castToNum(Integer.class)).from(qBookmark)
								.where(qBookmark.user.id.eq(userId).and(qBookmark.cafe.id.eq(qCafe.id))),
						// bookmarkCnt
						JPAExpressions.select(qBookmark.count().castToNum(Integer.class)).from(qBookmark)
								.where(qBookmark.cafe.id.eq(qCafe.id)),
						// reviewCnt
						JPAExpressions.select(qReview.count().castToNum(Integer.class)).from(qReview)
								.where(qReview.cafe.id.eq(qCafe.id))
				))
				.from(qCafe)
				.join(qTags)
				.on(qCafe.id.eq(qTags.cafe.id))
				//거리 제한보다 가까운 카페만
				.where(acos(cos(radians(Expressions.constant(userLatitude)))
						.multiply(cos(radians(qCafe.latitude.castToNum(Double.class))))
						.multiply(cos(radians(qCafe.longitude.castToNum(Double.class)).subtract(radians(Expressions.constant(userLongitude)))))
						.add((sin(radians(Expressions.constant(userLatitude))).multiply(sin(radians(qCafe.latitude.castToNum(Double.class))))))
				).multiply(Expressions.constant(6371)).stringValue().loe(cafeCurationInput.getDistance()))
				//찜하지 않은 카페만 추천
				.where(JPAExpressions.select(qBookmark.count().castToNum(Integer.class)).from(qBookmark)
						.where(qBookmark.user.id.eq(userId).and(qBookmark.cafe.id.eq(qCafe.id))).eq(0))
				.orderBy(hasCategory(cafeCurationInput.getCategory()), Expressions.stringPath("distance").asc())
				.offset(pageable.getOffset()).limit(pageable.getPageSize())
				.fetchResults();
		long totalCount = queryResult.getTotal();
		List<CafeCurationOutput> content = queryResult.getResults();

		return new PageImpl<>(content, pageable, totalCount);
	}

	private OrderSpecifier hasCategory(String category){
		if(category==null){ // 카테고리가 없으면 토탈점수 값이 높은거 우선
			return qTags.taste.add(qTags.view).add(qTags.wide).add(qTags.study).add(qTags.mood).add(qTags.nice).desc();
		}
		return Expressions.stringPath(category).desc();
	}
}

