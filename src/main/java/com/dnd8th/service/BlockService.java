package com.dnd8th.service;

import com.dnd8th.dao.block.BlockFindDao;
import com.dnd8th.dao.block.BlockUpdateDao;
import com.dnd8th.dao.review.ReviewFindDao;
import com.dnd8th.dto.block.*;
import com.dnd8th.dto.task.TaskPartDto;
import com.dnd8th.dto.task.TaskSumDto;
import com.dnd8th.entity.Block;
import com.dnd8th.entity.Task;
import com.dnd8th.entity.User;
import com.dnd8th.error.exception.block.BlockAccessDeniedException;
import com.dnd8th.error.exception.block.BlockNotFoundException;
import com.dnd8th.error.exception.user.UserNotFoundException;
import com.dnd8th.repository.BlockRepository;
import com.dnd8th.repository.UserRepository;
import com.dnd8th.util.DateParser;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class BlockService {

    private final BlockFindDao blockFindDao;
    private final BlockUpdateDao blockUpdateDao;
    private final ReviewFindDao reviewFindDao;
    private final UserRepository userRepository;
    private final BlockRepository blockRepository;
    private final DateParser dateParser;

    public void createBlock(BlockCreateRequest blockCreateRequest, String userEmail) {
        User user = userRepository.findByEmail(userEmail).orElseThrow(UserNotFoundException::new);
        Date date = dateParser.parseDate(blockCreateRequest.getDate());

        blockRepository.save(blockCreateRequest.toEntity(user, date));
    }

    public BlockGetResponse getBlock(String userEmail, Long blockId) {
        User user = userRepository.findByEmail(userEmail).orElseThrow(UserNotFoundException::new);
        Block block = blockRepository.findById(blockId).orElseThrow(BlockNotFoundException::new);

        User blockOwner = block.getUser();
        if (user != blockOwner) {
            throw new BlockAccessDeniedException();
        }
        String date = dateParser.toString(block.getDate());
        return BlockGetResponse.builder()
                .backgroundColor(block.getBlockColor())
                .secret(block.getBlockLock())
                .emoji(block.getEmotion())
                .title(block.getTitle())
                .date(date).build();
    }

    public void updateBlock(BlockUpdateRequest blockUpdateRequest, String userEmail, Long blockId) {
        User user = userRepository.findByEmail(userEmail).orElseThrow(UserNotFoundException::new);
        Block block = blockRepository.findById(blockId).orElseThrow(BlockNotFoundException::new);

        User blockOwner = block.getUser();
        if (user != blockOwner) {
            throw new BlockAccessDeniedException();
        }

        blockUpdateDao.updateBlock(blockId, blockUpdateRequest);
    }

    public void deleteBlock(Long blockId, String userEmail) {
        User user = userRepository.findByEmail(userEmail).orElseThrow(UserNotFoundException::new);
        Block block = blockRepository.findById(blockId).orElseThrow(BlockNotFoundException::new);

        User blockOwner = block.getUser();
        if (user != blockOwner) {
            throw new BlockAccessDeniedException();
        }
        blockRepository.delete(block);
    }

    public List<BlockCalendarResponse> getBlockWeek(String email, String date) {
        userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);

        Date targetDate = dateParser.parseDate(date);
        Date startedAt = dateParser.getDateXDaysAgo(targetDate,6);
        Date endedAt = dateParser.getDateXDaysLater(targetDate,7);
        List<Block> blocks = blockFindDao.getBlocksByDate(email, startedAt, endedAt);
        return getBlocksCalender(startedAt,endedAt,blocks);
    }

    public List<BlockCalendarResponse> getBlockMonth(String email, String date) {
        userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);

        LocalDate targetDate = LocalDate.parse(date);
        LocalDate firstDayOfMonth = targetDate.withDayOfMonth(1);
        LocalDate lastDayOfMonth = targetDate.withDayOfMonth(targetDate.lengthOfMonth());
        Date startedAt = Date.from(firstDayOfMonth.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date endedAt = Date.from(lastDayOfMonth.atStartOfDay(ZoneId.systemDefault()).toInstant());
        endedAt = dateParser.getDateXDaysLater(endedAt,1);
        List<Block> blocks = blockFindDao.getBlocksByDate(email, startedAt, endedAt);

        return getBlocksCalender(startedAt, endedAt, blocks);
    }

    public List<BlockCalendarResponse> getBlocksCalender(Date startedAt, Date endedAt, List<Block> blocks){
        Map<String, List<Block>> blocksByDate = blocks.stream()
                .collect(Collectors.groupingBy(block -> dateParser.toString(block.getDate())));

        Map<String,  List<Block>> blocksByWeek = new LinkedHashMap<>();
        List<Block> emptyBlocks = new ArrayList<>();
        while (startedAt.before(endedAt)) {
            String formattedDateTime = dateParser.toString(startedAt);
            blocksByWeek.put(formattedDateTime, blocksByDate.getOrDefault(formattedDateTime, emptyBlocks));
            startedAt = dateParser.getDateXDaysLater(startedAt, 1);
        }

        return blocksByWeek.entrySet().stream()
                .map(blockResponse -> {
                    return BlockCalendarResponse.builder()
                            .date(blockResponse.getKey())
                            .backgroundColors(blockResponse.getValue().stream()
                                    .map(blockCalender -> {
                                        return BlockCalendarDto.builder()
                                                .blockId(blockCalender.getId())
                                                .backgroundColor(blockCalender.getBlockColor()).build();
                                    })
                                    .collect(Collectors.toList())
                            ).build();
                })
                .collect(Collectors.toList());
    }

    public BlockMainGetResponse getBlockDetail(String email, String date) {
        Date targetDate = dateParser.parseDate(date);

        List<Block> blocks = blockFindDao.getDailyBlock(email, targetDate);
        BlockSumDto blockSumDto = convertToSumBlock(blocks);
        Long reviewId = reviewFindDao.findByEmailAndDate(email, targetDate);
        return BlockMainGetResponse.builder()
                .date(dateParser.toString(targetDate))
                .numOfTotalBlocks(blockSumDto.getNumOfTotalBlocks())
                .numOfTotalTasks(blockSumDto.getNumOfTotalTasks())
                .blocks(blockSumDto.getBlocks())
                .reviewId(reviewId).build();
    }

    private BlockSumDto convertToSumBlock(List<Block> blocks) {
        Integer totalTask = 0;
        List<BlockPartDto> blocksDto = new ArrayList<>();
        for (Block block : blocks) {
            TaskSumDto task = convertToSumTask(block.getId());
            BlockPartDto blockdto = BlockPartDto.builder()
                    .blockId(block.getId())
                    .title(block.getTitle())
                    .backgroundColor(block.getBlockColor())
                    .emoji(block.getEmotion())
                    .numOfTasks(task.getNumOfTasks())
                    .numOfDoneTask(task.getNumOfDoneTask())
                    .tasks(task.getTasks()).build();
            blocksDto.add(blockdto);
            totalTask = totalTask + task.getNumOfTasks();
        }
        return new BlockSumDto(blocksDto.size(), totalTask, blocksDto);
    }

    private TaskSumDto convertToSumTask(long blockId) {
        List<TaskPartDto> tasksDto = new ArrayList<>();
        List<Task> tasks = blockFindDao.getDailyTask(blockId);
        Integer done = 0;
        for (Task task : tasks) {
            TaskPartDto taskPartDto = TaskPartDto.builder()
                    .taskId(task.getId())
                    .done(task.getStatus())
                    .task(task.getContents()).build();
            tasksDto.add(taskPartDto);
            if (task.getStatus()) {
                done = done + 1;
            }
        }
        return new TaskSumDto(tasks.size(), done, tasksDto);
    }

}

