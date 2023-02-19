package com.dnd8th.dao;

import com.dnd8th.repository.Interface.BlockRepositoryCustom;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

import static com.dnd8th.entity.QBlock.block;

@Repository
@RequiredArgsConstructor
public class BlockFindDao implements BlockRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    public List<String> findByIdAndDate(long id, Date date) {
        return queryFactory.select(block.blockColor)
                .from(block)
                .where(block.user.id.eq(id), block.date.eq(date))
                .fetch();
    }
}
