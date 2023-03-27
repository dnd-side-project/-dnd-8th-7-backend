package com.dnd8th.service;

import com.dnd8th.dao.report.MonthlyBlockGetDao;
import com.dnd8th.dao.report.MonthlyBlockTaskGetDao;
import com.dnd8th.dto.report.MonthlyBlockAndTaskGetDTO;
import com.dnd8th.dto.report.MonthlyBlockGetDTO;
import com.dnd8th.dto.report.MonthlyTaskGetDTO;
import com.dnd8th.dto.report.ReportBlockGetResponse;
import com.dnd8th.error.exception.report.MonthInputInvalidException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ReportService {

    private final MonthlyBlockTaskGetDao monthlyBlockTaskGetDao;
    private final MonthlyBlockGetDao monthlyBlockGetDao;

    private static void isMonthValid(Integer month) {
        if (month < 1 || month > 12) {
            throw new MonthInputInvalidException();
        }
    }

    public ReportBlockGetResponse getMostTaskRateBlock(String userEmail, Integer month) {
        isMonthValid(month);

        List<MonthlyBlockAndTaskGetDTO> monthlyBlock = getMonthlyBlockAndTask(userEmail, month);
        if (monthlyBlock.isEmpty()) {
            return new ReportBlockGetResponse(null);
        }

        String mostTaskRateBlockContent = getMostTaskRateBlockContent(monthlyBlock);

        return new ReportBlockGetResponse(mostTaskRateBlockContent);
    }

    public ReportBlockGetResponse getMostMadeBlock(String userEmail, Integer month) {
        isMonthValid(month);

        List<MonthlyBlockGetDTO> monthlyBlock = getMonthlyBlock(userEmail, month);

        //business logic
        if (monthlyBlock.isEmpty()) {
            return new ReportBlockGetResponse(null);
        }

        String mostMadeBlockContent = getMostMadeBlockContent(monthlyBlock);
        return new ReportBlockGetResponse(mostMadeBlockContent);
    }

    private String getMostMadeBlockContent(List<MonthlyBlockGetDTO> monthlyBlock) {
        List<String> blockContentList = monthlyBlock.stream().map(MonthlyBlockGetDTO::getTitle)
                .collect(Collectors.toList());

        Map<String, Integer> map = new HashMap<>();
        for (String s : blockContentList) {
            map.merge(s, 1, Integer::sum);
        }

        return map.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);
    }

    private List<MonthlyBlockGetDTO> getMonthlyBlock(String userEmail, Integer month) {
        return monthlyBlockGetDao.getMonthlyBlockList(userEmail, month);
    }

    private String getMostTaskRateBlockContent(List<MonthlyBlockAndTaskGetDTO> monthlyBlock) {
        //hash map key: content string, value: task achieved rate
        Map<String, List<Double>> blockTaskRateMap = new HashMap<>();
        for (MonthlyBlockAndTaskGetDTO block : monthlyBlock) {
            List<MonthlyTaskGetDTO> tasks = block.getTasks();
            if (tasks.size() == 0) {
                continue;
            }

            Integer totalTaskCount = tasks.size();
            Integer achievedTaskCount = 0;
            for (MonthlyTaskGetDTO task : tasks) {
                if (task.getStatus()) {
                    achievedTaskCount++;
                }
            }

            Double taskAchievedRate = (double) achievedTaskCount / totalTaskCount;

            String blockContent = block.getTitle();

            List<Double> rates = blockTaskRateMap.getOrDefault(blockContent, new ArrayList<>());
            rates.add(taskAchievedRate);

            blockTaskRateMap.put(blockContent, rates);
        }

        //one more loop and get the highest value(task achieved rate)
        String mostTaskRateBlockContent = null;
        Double highestAvgRate = 0.0;

        for (Map.Entry<String, List<Double>> entry : blockTaskRateMap.entrySet()) {
            List<Double> rates = entry.getValue();

            Double avgRate = rates.stream().mapToDouble(rate -> rate).average().orElse(0.0);

            if (avgRate > highestAvgRate) {
                highestAvgRate = avgRate;
                mostTaskRateBlockContent = entry.getKey();
            }
        }

        //return the key(content string)
        return mostTaskRateBlockContent;
    }

    private List<MonthlyBlockAndTaskGetDTO> getMonthlyBlockAndTask(String userEmail,
            Integer month) {
        return monthlyBlockTaskGetDao.getMonthlyBlockAndTask(userEmail, month);
    }

}
