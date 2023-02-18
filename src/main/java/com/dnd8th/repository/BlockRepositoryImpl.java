package com.dnd8th.repository;

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

    public List<String> findByIdAndDate(long id, Date date) {
        QBlock block = QBlock.block;
        List<String> color = queryFactory.select(block.blockColor)
                .from(block)
                .where(block.user.id.eq(id), block.date.eq(date))
                .fetch();

        return color;
    }
}
