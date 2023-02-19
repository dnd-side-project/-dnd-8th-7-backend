package com.dnd8th.api;


import com.dnd8th.dto.MainWeekDTO;
import com.dnd8th.service.BlockService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@Slf4j
@RestController
@RequestMapping("/api/block")
@RequiredArgsConstructor
public class Block {

    private final BlockService blockService;

    @GetMapping("/{date}")
    public MainWeekDTO findUser(@PathVariable("date") String date) throws ParseException {
        return blockService.getBlockWeek("1",date);
    }
}
