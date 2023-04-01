package com.dnd8th.dao.report;

import static com.dnd8th.entity.QBlock.block;
import static com.dnd8th.entity.QKeep.keep;
import static com.dnd8th.entity.QTask.task;

import com.dnd8th.dto.report.MonthlyBlockAndTaskGetDTO;
import com.dnd8th.dto.report.MonthlyTaskGetDTO;
import com.dnd8th.entity.Block;
import com.dnd8th.entity.Task;
import com.dnd8th.util.DateParser;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.Comparator;
import java.util.Date;
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

    private final DateParser dateParser;

    public List<MonthlyBlockAndTaskGetDTO> getMonthlyBlockAndTask(String userEmail, Integer year,
            Integer month) {
        List<Block> blocks = queryFactory.selectFrom(block)
                .leftJoin(block.tasks, task).fetchJoin()
                .leftJoin(block.keep, keep).fetchJoin()
                .where(block.user.email.eq(userEmail),
                        block.date.year().eq(year),
                        block.date.month().eq(month))
                .orderBy(block.createdAt.asc())
                .fetch();

        return getMonthlyBlockAndTaskGetDTOS(blocks);
    }

    public List<MonthlyBlockAndTaskGetDTO> getMonthlyBlockAndTaskWithDate(String userEmail,
            Integer year,
            Integer month, Integer day) {

        Date dateStart = dateParser.getDate(year, month, 1);
        Date dateEnd = dateParser.getDate(year, month, day);

        List<Block> blocks = queryFactory.selectFrom(block)
                .leftJoin(block.tasks, task).fetchJoin()
                .leftJoin(block.keep, keep).fetchJoin()
                .where(block.user.email.eq(userEmail),
                        block.date.between(dateStart, dateEnd))
                .orderBy(block.createdAt.asc())
                .fetch();

        return getMonthlyBlockAndTaskGetDTOS(blocks);
    }

    private List<MonthlyBlockAndTaskGetDTO> getMonthlyBlockAndTaskGetDTOS(List<Block> blocks) {
        List<Task> tasks = getTaskList(blocks);

        Map<Block, List<Task>> transform = tasks.stream()
                .collect(Collectors.groupingBy(Task::getBlock));

        return transform.entrySet().stream()
                .map(entry -> {
                    Block block = entry.getKey();
                    List<Task> taskList = entry.getValue();
                    List<MonthlyTaskGetDTO> taskGetDTOS = taskList.stream()
                            .map(MonthlyTaskGetDTO::new)
                            .collect(Collectors.toList());
                    return MonthlyBlockAndTaskGetDTO.builder()
                            .id(block.getId())
                            .title(block.getTitle())
                            .date(block.getDate())
                            .tasks(taskGetDTOS)
                            .build();
                })
                .sorted(Comparator.comparing(MonthlyBlockAndTaskGetDTO::getDate))
                .collect(Collectors.toList());
    }


    private List<Task> getTaskList(List<Block> blocks) {
        List<Task> tasks = queryFactory.selectFrom(task)
                .where(task.block.in(blocks))
                .fetch();

        return tasks;
    }
}
