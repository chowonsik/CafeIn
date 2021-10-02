package com.cafein.dao;

import com.cafein.dto.bookmark.seleteBookmark.QSelectBookmarkOutput;
import com.cafein.dto.bookmark.seleteBookmark.SelectBookmarkOutput;
import com.cafein.entity.QBhour;
import com.cafein.entity.QBookmark;
import com.cafein.entity.QCafe;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

@RequiredArgsConstructor
public class BookmarkRepositoryImpl implements BookmarkRepositoryCustom {
    private final JPAQueryFactory queryFactory;

	QBookmark qBookmark = QBookmark.bookmark;
    QCafe qCafe = QCafe.cafe;
    QBhour qBhour = QBhour.bhour;

    @Override
    public Page<SelectBookmarkOutput> findByUserIdCustum(int userId, Pageable pageable) {
        QueryResults<SelectBookmarkOutput> queryResult = queryFactory
                .select(new QSelectBookmarkOutput(qBookmark.id, Expressions.constant(userId), qCafe.id, qCafe.name,
                        qCafe.branch, qCafe.area, qCafe.tel, qCafe.address, qCafe.latitude, qCafe.longitude, qCafe.imgUrl,
                        qBhour.type, qBhour.week_type, qBhour.mon, qBhour.tue, qBhour.wed, qBhour.thu, qBhour.fri,
                        qBhour.sat, qBhour.sun, qBhour.startTime, qBhour.endTime, qBhour.etc
                ))
                .from(qBookmark)
                .join(qCafe)
                .on(qBookmark.cafe.id.eq(qCafe.id))
                .leftJoin(qBhour)
                .on(qCafe.id.eq(qBhour.cafe.id))
                .offset(pageable.getOffset()).limit(pageable.getPageSize())
                .fetchResults();
        long totalCount = queryResult.getTotal();
        List<SelectBookmarkOutput> content = queryResult.getResults();

        return new PageImpl<>(content, pageable, totalCount);
    }
}

