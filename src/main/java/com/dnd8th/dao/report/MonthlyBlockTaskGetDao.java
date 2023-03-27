package com.dnd8th.dao.report;

import static com.dnd8th.entity.QBlock.block;
import static com.dnd8th.entity.QKeep.keep;
import static com.dnd8th.entity.QTask.task;

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
public class MonthlyBlockTaskGetDao {

    private final JPAQueryFactory queryFactory;

    public List<MonthlyBlockGetDTO> getMonthlyBlockAndTask(String userEmail, Integer month) {
        List<Block> blocks = queryFactory.selectFrom(block)
                .leftJoin(block.tasks, task).fetchJoin()
                .leftJoin(block.keep, keep).fetchJoin()
                .where(block.user.email.eq(userEmail)
                        .and(block.date.month().eq(month)))
                .orderBy(block.createdAt.asc())
                .fetch();

        List<Task> tasks = queryFactory.selectFrom(task)
                .where(task.block.in(blocks))
                .fetch();

        Map<Block, List<Task>> transform = tasks.stream()
                .collect(Collectors.groupingBy(Task::getBlock));

        return transform.entrySet().stream()
                .map(entry -> {
                    Block block = entry.getKey();
                    List<Task> taskList = entry.getValue();
                    List<MonthlyTaskGetDTO> taskGetDTOS = taskList.stream()
                            .map(MonthlyTaskGetDTO::new)
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
