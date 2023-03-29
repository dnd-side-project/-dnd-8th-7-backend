package com.dnd8th.api;

import com.dnd8th.dto.report.ReportBlockGetResponse;
import com.dnd8th.dto.report.ReportMonthlyComparisonGetResponse;
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

    @GetMapping("/most-task-rate/{year}/{month}")
    public ResponseEntity<ReportBlockGetResponse> getMostTaskRateBlock(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Integer year,
            @PathVariable Integer month) {

        String userEmail = userDetails.getUsername();
        ReportBlockGetResponse mostTaskRateBlock = reportService.getMostTaskRateBlock(
                userEmail, year, month);

        return ResponseEntity.ok().body(mostTaskRateBlock);
    }

    @GetMapping("/most-made-block/{year}/{month}")
    public ResponseEntity<ReportBlockGetResponse> getMostMadeBlock(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Integer year,
            @PathVariable Integer month) {

        String userEmail = userDetails.getUsername();
        ReportBlockGetResponse mostMadeBlock = reportService.getMostMadeBlock(
                userEmail, year, month);

        return ResponseEntity.ok().body(mostMadeBlock);
    }

    @GetMapping("/least-task-rate/{year}/{month}")
    public ResponseEntity<ReportBlockGetResponse> getLeastTaskRateBlock(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Integer year,
            @PathVariable Integer month) {

        String userEmail = userDetails.getUsername();
        ReportBlockGetResponse leastTaskRateBlock = reportService.getLeastTaskRateBlock(
                userEmail, year, month);

        return ResponseEntity.ok().body(leastTaskRateBlock);
    }

    @GetMapping("/monthly-comparison/{year}/{month}/{day}")
    public ResponseEntity<ReportMonthlyComparisonGetResponse> getMonthlyComparison(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Integer year,
            @PathVariable Integer month,
            @PathVariable Integer day) {

        String userEmail = userDetails.getUsername();
        ReportMonthlyComparisonGetResponse monthlyComparison = reportService.getMonthlyComparison(
                userEmail, year, month, day);

        return ResponseEntity.ok().body(monthlyComparison);
    }
}
