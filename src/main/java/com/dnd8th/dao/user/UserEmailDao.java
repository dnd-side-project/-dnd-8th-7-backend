package com.dnd8th.dao.user;

import static com.dnd8th.entity.QUserEntity.userEntity;

import com.dnd8th.entity.UserEntity;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserEmailDao {

    private final JPAQueryFactory query;

    public UserEntity findByEmail(final String email) {
        Optional<UserEntity> user = Optional.ofNullable(query.selectFrom(userEntity)
                .where(userEntity.email.eq(email))
                .fetchOne());

        return user.get();
    }
}
