package com.cafein.dao;

import com.cafein.dto.bookmark.seleteBookmark.QSelectBookmarkOutput;
import com.cafein.dto.bookmark.seleteBookmark.SelectBookmarkOutput;
import com.cafein.entity.QBhour;
import com.cafein.entity.QBookmark;
import com.cafein.entity.QCafe;
import com.cafein.entity.QReview;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
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
    QReview qReview = QReview.review;

    @Override
    public Page<SelectBookmarkOutput> findByUserIdCustum(int userId, Pageable pageable) {
        QueryResults<SelectBookmarkOutput> queryResult = queryFactory
                .select(new QSelectBookmarkOutput(qBookmark.id, Expressions.constant(userId), qCafe.id, qCafe.name,
                        qCafe.branch, qCafe.area, qCafe.tel, qCafe.address, qCafe.latitude, qCafe.longitude, qCafe.imgUrl,
                        // bookmarkCnt
                        JPAExpressions.select(qBookmark.count().castToNum(Integer.class)).from(qBookmark)
                                .where(qBookmark.cafe.id.eq(qCafe.id)),
                        // reviewCnt
                        JPAExpressions.select(qReview.count().castToNum(Integer.class)).from(qReview)
                                .where(qReview.cafe.id.eq(qCafe.id))
                ))
                .from(qBookmark)
                .join(qCafe)
                .on(qBookmark.cafe.id.eq(qCafe.id))
                .where(qBookmark.user.id.eq(userId))
                .orderBy(qBookmark.id.desc()) //최근에 등록한 북마크 순
                .offset(pageable.getOffset()).limit(pageable.getPageSize())
                .fetchResults();
        long totalCount = queryResult.getTotal();
        List<SelectBookmarkOutput> content = queryResult.getResults();

        return new PageImpl<>(content, pageable, totalCount);
    }
}

