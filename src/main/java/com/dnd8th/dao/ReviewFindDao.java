package com.dnd8th.dao;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

import static com.dnd8th.entity.QReview.review;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class ReviewFindDao {
    private final JPAQueryFactory queryFactory;

    public Long findByEmailAndDate(String email, Date date) {
        return queryFactory.select(review.id)
                .from(review)
                .where(review.user.email.eq(email), review.date.eq(date))
                .fetchOne();
    }
}