package com.dnd8th.dao.report;

import static com.dnd8th.entity.QBlock.block;
import static com.dnd8th.entity.QKeep.keep;

import com.dnd8th.dto.report.MonthlyBlockGetDTO;
import com.dnd8th.entity.Block;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MonthlyBlockGetDao {

    private final JPAQueryFactory queryFactory;

    public List<MonthlyBlockGetDTO> getMonthlyBlockList(String email, Integer month) {
        List<Block> blocks = queryFactory.selectFrom(block)
                .where(block.user.email.eq(email)
                        .and(block.date.month().eq(month)))
                .leftJoin(block.keep, keep).fetchJoin()
                .orderBy(block.date.asc())
                .fetch();

        return blocks.stream().map(MonthlyBlockGetDTO::new)
                .collect(Collectors.toList());
    }
}
