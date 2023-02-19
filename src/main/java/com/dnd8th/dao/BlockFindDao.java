package com.dnd8th.dao;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

import static com.dnd8th.entity.QBlock.block;


@Service
@Transactional
@RequiredArgsConstructor
public class BlockFindDao{
    private final JPAQueryFactory queryFactory;

    public List<String> findByEmailAndDate(String email, Date date) {
        return queryFactory.select(block.blockColor)
                .from(block)
                .where(block.user.email.eq(email), block.date.eq(date))
                .fetch();
    }
}
