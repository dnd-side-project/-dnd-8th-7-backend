package com.dnd8th.api;

import com.dnd8th.dto.report.MostTaskRateBlockGetResponse;
import com.dnd8th.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/report")
@RequiredArgsConstructor
public class ReportApi {

    private final ReportService reportService;

    @GetMapping("/most-task-rate/{month}")
    public ResponseEntity<MostTaskRateBlockGetResponse> getMostTaskRateBlock(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Integer month) {

        String userEmail = userDetails.getUsername();
        MostTaskRateBlockGetResponse mostTaskRateBlock = reportService.getMostTaskRateBlock(
                userEmail, month);

        return ResponseEntity.ok().body(mostTaskRateBlock);
    }
}
