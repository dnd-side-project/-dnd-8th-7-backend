package com.dnd8th.dao;

import com.dnd8th.dto.user.UserGetDto;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.dnd8th.entity.QUser.user;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class UserUpdateDao {

    private final JPAQueryFactory queryFactory;

    public void updateUser(String email, UserGetDto userGetDto) {
        queryFactory.update(user)
                .set(user.imagePath, userGetDto.getImgPath())
                .set(user.name, userGetDto.getUser())
                .set(user.introduction, userGetDto.getIntroduction())
                .set(user.userLock, userGetDto.getLock())
                .where(user.email.eq(email))
                .execute();
    }
}
