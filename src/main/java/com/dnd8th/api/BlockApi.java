package com.dnd8th.api;


import com.dnd8th.dto.BlockDTO;
import com.dnd8th.dto.MainDTO;
import com.dnd8th.dto.MainWeekDTO;
import com.dnd8th.entity.Block;
import com.dnd8th.error.exception.block.DateFormatInvalidException;
import com.dnd8th.service.BlockService;
import com.querydsl.core.Tuple;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/block")
@RequiredArgsConstructor
public class BlockApi {

    private final BlockService blockService;

    @GetMapping("/{date}")
    public ResponseEntity
            <MainWeekDTO> getMainWeek(@AuthenticationPrincipal UserDetails userDetails, @PathVariable("date") String date){
        String email = userDetails.getUsername();
        MainWeekDTO mainWeek = blockService.getBlockWeek(email, date);
        return ResponseEntity.status(HttpStatus.OK).body(mainWeek);
    }

    @GetMapping("/detail/{date}")
    public ResponseEntity
            <MainDTO> getMainDetail(@AuthenticationPrincipal UserDetails userDetails, @PathVariable("date") String date){
        String email = userDetails.getUsername();
        MainDTO mainDto = blockService.getBlockDetail(email, date);
        return ResponseEntity.status(HttpStatus.OK).body(mainDto);
    }
}
