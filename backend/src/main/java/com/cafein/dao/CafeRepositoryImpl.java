package com.cafein.dao;

import java.util.ArrayList;
import java.util.List;

import com.cafein.dto.cafe.search.CafeSearchOutput;
import com.cafein.dto.cafe.search.CafeSearchInput;
import com.cafein.dto.cafe.search.QCafeSearchOutput;
import com.cafein.dto.cafe.selectCafeDetail.QSelectCafeDetailOutput;
import com.cafein.dto.cafe.selectCafeDetail.SelectCafeDetailOutput;
import com.cafein.entity.QBhour;
import com.cafein.entity.QCafe;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.*;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;

import org.hibernate.criterion.Projections;
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
	QBhour qBhour = QBhour.bhour;

	@Override
	public SelectCafeDetailOutput findByIdCustom(int id) {
		SelectCafeDetailOutput queryResult = queryFactory
				.select(new QSelectCafeDetailOutput(qCafe.id, qCafe.name, qCafe.branch, qCafe.area, qCafe.tel,
						qCafe.address, qCafe.latitude, qCafe.longitude,
						qBhour.type, qBhour.week_type, qBhour.mon, qBhour.tue, qBhour.wed, qBhour.thu, qBhour.fri,
						qBhour.sat, qBhour.sun, qBhour.startTime, qBhour.endTime, qBhour.etc
				))
				.from(qCafe)
				.leftJoin(qBhour)
				.on(qCafe.id.eq(qBhour.cafe.id))
				.where(qCafe.id.eq(id))
				.fetchOne();
		System.out.println(queryResult);
		return queryResult;
	}

	@Override
	public Page<CafeSearchOutput> findByWordCustom(CafeSearchInput cafeSearchInput, Pageable pageable) {
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
						qBhour.type, qBhour.week_type, qBhour.mon, qBhour.tue, qBhour.wed, qBhour.thu, qBhour.fri,
						qBhour.sat, qBhour.sun, qBhour.startTime, qBhour.endTime, qBhour.etc
				))
				.from(qCafe)
				.leftJoin(qBhour)
				.on(qCafe.id.eq(qBhour.cafe.id))
				.where(qCafe.name.contains(cafeSearchInput.getSearch()))
				.orderBy(Expressions.stringPath("distance").asc())
				.offset(pageable.getOffset()).limit(pageable.getPageSize())
				.fetchResults();
		long totalCount = queryResult.getTotal();
		List<CafeSearchOutput> content = queryResult.getResults();

		return new PageImpl<>(content, pageable, totalCount);
	}
}

