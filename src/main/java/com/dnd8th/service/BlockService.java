package com.dnd8th.service;

import com.dnd8th.dto.MainWeekDTO;
import com.dnd8th.dto.WeekDTO;
import com.dnd8th.entity.Block;
import com.dnd8th.repository.BlockRepositoryImpl;
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

    private final BlockRepositoryImpl blockRepository;

    public MainWeekDTO getBlockWeek(String id, String date) throws ParseException {
        MainWeekDTO mainWeekDTO = new MainWeekDTO();
        List<WeekDTO> weekDTO = new ArrayList<>();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date targetDate = format.parse(date);
        targetDate.setDate(targetDate.getDate() - 4);
        for (int i = 0; i < 7; i++){
            targetDate.setDate(targetDate.getDate() +  1);
            List<Block> blocks = blockRepository.findByIdAndDate(Long.parseLong(id), targetDate);
            WeekDTO week = convertBlockToWeekDTO(blocks, targetDate);
            weekDTO.add(week);
        }
        mainWeekDTO.setUser("user");
        mainWeekDTO.setDailyBlocks(weekDTO);
        return mainWeekDTO;
    }

    private WeekDTO convertBlockToWeekDTO(List<Block> blocks, Date date){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dateFormat = simpleDateFormat.format(date);
        if (blocks.isEmpty()){
            WeekDTO week = new WeekDTO();
            week.setDate(dateFormat);
            return week;
        }
        WeekDTO week = new WeekDTO();
        List<String> color = new ArrayList<>();
        for (Block block:blocks){
            color.add(block.getBlockColor());
        }
        week.setDate(dateFormat);
        week.setColor(color);
        return week;
    }

}

