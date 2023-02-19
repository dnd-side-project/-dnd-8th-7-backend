package com.dnd8th.api;


import com.dnd8th.dto.MainWeekDTO;
import com.dnd8th.error.exception.ErrorCode;
import com.dnd8th.error.exception.block.DateFormatInvalidException;
import com.dnd8th.service.BlockService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity
            <MainWeekDTO> findUser(@PathVariable("date") String date){
        MainWeekDTO mainWeek;
        try {
            mainWeek = blockService.getBlockWeek("1", date);
        } catch (RuntimeException e) {
            throw new DateFormatInvalidException(ErrorCode.INVALID_TYPE_VALUE);
        }
        return ResponseEntity.status(HttpStatus.OK).body(mainWeek);
    }
}
