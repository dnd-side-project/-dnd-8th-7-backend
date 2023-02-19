package com.dnd8th.dao;

import com.dnd8th.dto.BlockDTO;
import com.dnd8th.dto.TaskDTO;
import com.dnd8th.entity.Block;
import com.dnd8th.entity.Task;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

import static com.dnd8th.entity.QBlock.block;
import static com.dnd8th.entity.QTask.task;


@Service
@Slf4j
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

    public List<Block> getDailyBlock(String email, Date date) {
        return queryFactory
                .select(block)
                .from(block)
                .where(block.user.email.eq(email), block.date.eq(date))
                .fetch();
    }

    public List<Task> getDailyTask(long bockId) {
        return queryFactory
                .select(task)
                .from(task)
                .where(task.block.id.eq(bockId))
                .fetch();
    }
}
