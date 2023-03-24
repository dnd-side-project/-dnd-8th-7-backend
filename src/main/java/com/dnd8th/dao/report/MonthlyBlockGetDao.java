package com.dnd8th.dao.report;

import static com.dnd8th.entity.QBlock.block;
import static com.dnd8th.entity.QTask.task;
import static com.querydsl.core.group.GroupBy.groupBy;
import static com.querydsl.core.group.GroupBy.list;

import com.dnd8th.dto.report.MonthlyBlockGetDTO;
import com.dnd8th.dto.report.MonthlyTaskGetDTO;
import com.dnd8th.entity.Block;
import com.dnd8th.entity.Task;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MonthlyBlockGetDao {

    private final JPAQueryFactory queryFactory;

    public List<MonthlyBlockGetDTO> getMonthlyBlock(String userEmail, Integer month) {
        Map<Block, List<Task>> transform = queryFactory.selectFrom(block)
                .where(block.user.email.eq(userEmail)
                        .and(block.date.month().eq(month)))
                .orderBy(block.createdAt.asc())
                .leftJoin(block.tasks, task)
                .transform(groupBy(block).as(list(task)));

        return transform.entrySet().stream()
                .map(entry -> {
                    Block block = entry.getKey();
                    List<Task> tasks = entry.getValue();
                    List<MonthlyTaskGetDTO> taskGetDTOS = tasks.stream().map(MonthlyTaskGetDTO::new)
                            .collect(Collectors.toList());
                    return MonthlyBlockGetDTO.builder()
                            .id(block.getId())
                            .title(block.getTitle())
                            .tasks(taskGetDTOS)
                            .build();
                })
                .collect(Collectors.toList());
    }
}
