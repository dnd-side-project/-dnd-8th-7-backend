package com.dnd8th.repository;

import com.dnd8th.entity.Block;
import com.dnd8th.entity.QBlock;
import com.dnd8th.repository.Interface.BlockRepositoryCustom;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class BlockRepositoryImpl implements BlockRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public List<Block> findByIdAndDate(long id, Date date) {
        QBlock block = QBlock.block;
        return queryFactory.selectFrom(block)
                .where(block.user.id.eq(id), block.date.eq(date))
                .fetch();
    }
}
