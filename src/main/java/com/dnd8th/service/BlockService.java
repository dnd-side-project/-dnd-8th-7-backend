package com.dnd8th.service;

import com.dnd8th.dao.BlockFindDao;
import com.dnd8th.dao.ReviewFindDao;
import com.dnd8th.dao.UserFindDao;
import com.dnd8th.dto.block.BlockPartDto;
import com.dnd8th.dto.block.BlockMainGetResponse;
import com.dnd8th.dto.block.BlockMainWeekGetResponse;
import com.dnd8th.dto.block.BlockSumDto;
import com.dnd8th.dto.task.TaskSumDto;
import com.dnd8th.dto.task.TaskPartDto;
import com.dnd8th.dto.block.BlockWeekPartDto;
import com.dnd8th.dto.block.BlockCreateRequest;
import com.dnd8th.entity.Block;
import com.dnd8th.entity.Task;
import com.dnd8th.entity.User;
import com.dnd8th.error.exception.block.BlockAccessDeniedException;
import com.dnd8th.error.exception.block.BlockNotFoundException;
import com.dnd8th.error.exception.user.UserNotFoundException;
import com.dnd8th.repository.BlockRepository;
import com.dnd8th.repository.UserRepository;
import com.dnd8th.util.DateParser;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
    private final UserFindDao userFindDao;
    private final ReviewFindDao reviewFindDao;
    private final UserRepository userRepository;
    private final BlockRepository blockRepository;
    private final DateParser dateParser;

    public void createBlock(BlockCreateRequest blockCreateRequest, String userEmail) {
        User user = userRepository.findByEmail(userEmail).orElseThrow(UserNotFoundException::new);
        Date date = dateParser.parseDate(blockCreateRequest.getDate());

        blockRepository.save(blockCreateRequest.toEntity(user, date));
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

    public BlockMainWeekGetResponse getBlockWeek(String email, String date) {
        BlockMainWeekGetResponse blockMainWeekGetResponse = new BlockMainWeekGetResponse();
        List<BlockWeekPartDto> blockWeekPartDto = new ArrayList<>();
        Date targetDate = dateParser.parseDate(date);
        targetDate.setDate(targetDate.getDate() - 4);
        for (int i = 0; i < 7; i++) {
            targetDate.setDate(targetDate.getDate() + 1);
            List<String> color = blockFindDao.findByEmailAndDate(email, targetDate);
            BlockWeekPartDto week = convertToWeekDTO(color, targetDate);
            blockWeekPartDto.add(week);
        }
        String name = userFindDao.findByEmail(email);
        blockMainWeekGetResponse.setUser(name);
        blockMainWeekGetResponse.setDailyBlocks(blockWeekPartDto);
        return blockMainWeekGetResponse;
    }

    public BlockMainGetResponse getBlockDetail(String email, String date) {
        Date targetDate = dateParser.parseDate(date);

        List<Block> blocks = blockFindDao.getDailyBlock(email, targetDate);
        BlockSumDto blockSumDto = convertToSumBlock(blocks);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dateFormat = simpleDateFormat.format(targetDate);
        Long reviewId = reviewFindDao.findByEmailAndDate(email,targetDate);
        return new BlockMainGetResponse(dateFormat, blockSumDto.getTotalBlock(), blockSumDto.getTotalTask(),
                 reviewId, blockSumDto.getBlocks());
    }

    private BlockWeekPartDto convertToWeekDTO(List<String> colors, Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dateFormat = simpleDateFormat.format(date);

        return new BlockWeekPartDto(dateFormat, colors);
    }

    private BlockSumDto convertToSumBlock(List<Block> blocks) {
        Integer totalTask = 0;
        List<BlockPartDto> blocksDto = new ArrayList<>();
        for (Block block : blocks) {
            TaskSumDto task = convertToSumTask(block.getId());
            BlockPartDto blockdto = new BlockPartDto(
                    block.getId(), block.getBlockColor(), block.getEmotion(),
                    task.getSumOfTask(), task.getSumOfDoneTask(), task.getTasks());
            blocksDto.add(blockdto);
            totalTask = totalTask + task.getSumOfTask();
        }
        return new BlockSumDto(blocksDto.size(), totalTask, blocksDto);
    }

    private TaskSumDto convertToSumTask(long blockId) {
        List<TaskPartDto> tasksDto = new ArrayList<>();
        List<Task> tasks = blockFindDao.getDailyTask(blockId);
        Integer done = 0;
        for (Task task : tasks) {
            TaskPartDto taskPartDto = new TaskPartDto(task.getId(), task.getContents(), task.getStatus());
            tasksDto.add(taskPartDto);
            if (task.getStatus()) {
                done = done + 1;
            }
        }
        return new TaskSumDto(tasks.size(), done, tasksDto);
    }

}

