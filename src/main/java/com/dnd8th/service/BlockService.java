package com.dnd8th.service;

import com.dnd8th.dao.BlockFindDao;
import com.dnd8th.dao.UserFindDao;
import com.dnd8th.dto.BlockDTO;
import com.dnd8th.dto.MainDTO;
import com.dnd8th.dto.MainWeekDTO;
import com.dnd8th.dto.SumBlock;
import com.dnd8th.dto.SumTask;
import com.dnd8th.dto.TaskDTO;
import com.dnd8th.dto.WeekDTO;
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

    public MainWeekDTO getBlockWeek(String email, String date) {
        MainWeekDTO mainWeekDTO = new MainWeekDTO();
        List<WeekDTO> weekDTO = new ArrayList<>();
        Date targetDate = dateParser.parseDate(date);
        targetDate.setDate(targetDate.getDate() - 4);
        for (int i = 0; i < 7; i++) {
            targetDate.setDate(targetDate.getDate() + 1);
            List<String> color = blockFindDao.findByEmailAndDate(email, targetDate);
            WeekDTO week = convertToWeekDTO(color, targetDate);
            weekDTO.add(week);
        }
        String name = userFindDao.findByEmail(email);
        mainWeekDTO.setUser(name);
        mainWeekDTO.setDailyBlocks(weekDTO);
        return mainWeekDTO;
    }

    public MainDTO getBlockDetail(String email, String date) {
        Date targetDate = dateParser.parseDate(date);

        List<Block> blocks = blockFindDao.getDailyBlock(email, targetDate);
        SumBlock sumBlock = convertToSumBlock(blocks);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("M월 d일 E요일");
        String dateFormat = simpleDateFormat.format(targetDate);
        return new MainDTO(dateFormat, sumBlock.getTotalBlock(), sumBlock.getTotalTask(),
                sumBlock.getBlocks());
    }

    private WeekDTO convertToWeekDTO(List<String> colors, Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dateFormat = simpleDateFormat.format(date);

        return new WeekDTO(dateFormat, colors);
    }

    private SumBlock convertToSumBlock(List<Block> blocks) {
        Integer totalTask = 0;
        List<BlockDTO> blocksDto = new ArrayList<>();
        for (Block block : blocks) {
            SumTask task = convertToSumTask(block.getId());
            BlockDTO blockdto = new BlockDTO(
                    block.getBlockColor(), block.getEmotion(), block.getTitle(),
                    task.getSumOfTask(), task.getSumOfDoneTask(), task.getTasks());
            blocksDto.add(blockdto);
            totalTask = totalTask + task.getSumOfTask();
        }
        return new SumBlock(blocksDto.size(), totalTask, blocksDto);
    }

    private SumTask convertToSumTask(long blockId) {
        List<TaskDTO> tasksDto = new ArrayList<>();
        List<Task> tasks = blockFindDao.getDailyTask(blockId);
        Integer done = 0;
        for (Task task : tasks) {
            TaskDTO taskDto = new TaskDTO(task.getContents(), task.getStatus());
            tasksDto.add(taskDto);
            if (task.getStatus()) {
                done = done + 1;
            }
        }
        return new SumTask(tasks.size(), done, tasksDto);
    }

}

