package com.dnd8th.dao;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.dnd8th.entity.QUser.user;

@Service
@Transactional
@RequiredArgsConstructor
public class UserFindDao {
    private final JPAQueryFactory queryFactory;

    public String findByEmail(String email) {
        return queryFactory.select(user.name)
                .from(user)
                .where(user.email.eq(email))
                .fetchOne();
    }
}
