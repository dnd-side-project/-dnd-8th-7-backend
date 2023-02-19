package com.dnd8th.service;

import com.dnd8th.dto.MainWeekDTO;
import com.dnd8th.dto.WeekDTO;
import com.dnd8th.error.exception.ErrorCode;
import com.dnd8th.error.exception.block.DateFormatInvalidException;
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

    public MainWeekDTO getBlockWeek(String id, String date){
        MainWeekDTO mainWeekDTO = new MainWeekDTO();
        List<WeekDTO> weekDTO = new ArrayList<>();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date targetDate = null;
        try {
            targetDate = format.parse(date);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        targetDate.setDate(targetDate.getDate() - 4);
        for (int i = 0; i < 7; i++){
            targetDate.setDate(targetDate.getDate() +  1);
            List<String> color = blockRepository.findByIdAndDate(Long.parseLong(id), targetDate);
            WeekDTO week = convertToWeekDTO(color, targetDate);
            weekDTO.add(week);
        }
        mainWeekDTO.setUser("user");
        mainWeekDTO.setDailyBlocks(weekDTO);
        return mainWeekDTO;
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

}

