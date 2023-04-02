package com.dnd8th.dao.block;

import com.dnd8th.dto.block.BlockCalendarGetDTO;
import com.dnd8th.entity.Block;
import com.dnd8th.entity.Task;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static com.dnd8th.entity.QBlock.block;
import static com.dnd8th.entity.QKeep.keep;
import static com.dnd8th.entity.QTask.task;


@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class BlockFindDao{
    private final JPAQueryFactory queryFactory;

    public List<BlockCalendarGetDTO> getBlocksByDate(String email, Date startedAt, Date endedAt) {
        List<Block> blocks = queryFactory.selectFrom(block)
                .where(block.user.email.eq(email), block.date.between(startedAt, endedAt))
                .leftJoin(block.keep, keep).fetchJoin()
                .orderBy(block.createdAt.asc())
                .fetch();

        return blocks.stream().map(BlockCalendarGetDTO::new)
                .collect(Collectors.toList());
    }

    public List<Block> getDailyBlock(String email, Date date) {
        return queryFactory
                .selectFrom(block)
                .where(block.user.email.eq(email), block.date.eq(date))
                .orderBy(block.createdAt.asc())
                .fetch();
    }

    public List<Task> getDailyTask(long bockId) {
        return queryFactory
                .selectFrom(task)
                .where(task.block.id.eq(bockId))
                .orderBy(task.createdAt.asc())
                .fetch();
    }
}
