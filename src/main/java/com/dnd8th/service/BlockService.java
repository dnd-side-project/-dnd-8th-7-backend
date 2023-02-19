package com.dnd8th.service;

import com.dnd8th.dao.BlockFindDao;
import com.dnd8th.dao.UserFindDao;
import com.dnd8th.dto.*;
import com.dnd8th.entity.Block;
import com.dnd8th.entity.Task;
import com.dnd8th.error.exception.block.DateFormatInvalidException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class BlockService {

    private final BlockFindDao blockFindDao;
    private final UserFindDao userFindDao;

    public MainWeekDTO getBlockWeek(String email, String date){
        MainWeekDTO mainWeekDTO = new MainWeekDTO();
        List<WeekDTO> weekDTO = new ArrayList<>();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date targetDate = null;
        try {
            targetDate = format.parse(date);
        } catch (ParseException e) {
            throw new DateFormatInvalidException();
        }
        targetDate.setDate(targetDate.getDate() - 4);
        for (int i = 0; i < 7; i++){
            targetDate.setDate(targetDate.getDate() +  1);
            List<String> color = blockFindDao.findByEmailAndDate(email, targetDate);
            WeekDTO week = convertToWeekDTO(color, targetDate);
            weekDTO.add(week);
        }
        String name = userFindDao.findByEmail(email);
        mainWeekDTO.setUser(name);
        mainWeekDTO.setDailyBlocks(weekDTO);
        return mainWeekDTO;
    }

    public MainDTO getBlockDetail(String email, String date){
        MainDTO mainDto = new MainDTO();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date targetDate = null;
        try {
            targetDate = format.parse(date);
        } catch (ParseException e) {
            throw new DateFormatInvalidException();
        }

        List<Block> blocks = blockFindDao.getDailyBlock(email, targetDate);
        SumBlock sumBlock = convertToSumBlock(blocks);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("M월 d일 E요일");
        String dateFormat = simpleDateFormat.format(targetDate);
        mainDto.setDate(dateFormat);
        mainDto.setTotalBlock(sumBlock.getTotalBlock());
        mainDto.setTotalTask(sumBlock.getTotalTask());
        mainDto.setBlocks(sumBlock.getBlocks());
        return mainDto;
    }

    private WeekDTO convertToWeekDTO(List<String> colors, Date date){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dateFormat = simpleDateFormat.format(date);
        if (colors.isEmpty()){
            WeekDTO week = new WeekDTO();
            week.setDate(dateFormat);
            return week;
        }
        WeekDTO week = new WeekDTO();
        week.setDate(dateFormat);
        week.setColor(colors);
        return week;
    }

    private SumBlock convertToSumBlock(List<Block> blocks){
        SumBlock sumBlock = new SumBlock();
        Integer totalTask = 0;
        List<BlockDTO> blocksDto = new ArrayList<>();
        for (Block block:blocks){
            SumTask task = convertToSumTask(block.getId());
            BlockDTO blockdto = new BlockDTO(
                    block.getBlockColor(),block.getEmotion(),block.getTitle(),task.getSumOfTask(),task.getSumOfDoneTask(),task.getTasks());
            blocksDto.add(blockdto);
            totalTask = totalTask + task.getSumOfTask();
        }
        sumBlock.setTotalTask(totalTask);
        sumBlock.setTotalBlock(blocksDto.size());
        sumBlock.setBlocks(blocksDto);
        return sumBlock;
    }

    private SumTask convertToSumTask(long blockId){
        SumTask sumTask = new SumTask();
        List<TaskDTO> tasksDto = new ArrayList<>();
        List<Task> tasks = blockFindDao.getDailyTask(blockId);
        Integer done = 0;
        for (Task task: tasks){
            TaskDTO taskDto= new TaskDTO(task.getContents(),task.getStatus());
            tasksDto.add(taskDto);
            if (task.getStatus()) {
                done = done + 1;
            }
        }
        sumTask.setSumOfDoneTask(done);
        sumTask.setSumOfTask(tasks.size());
        sumTask.setTasks(tasksDto);
        return sumTask;
    }

}

