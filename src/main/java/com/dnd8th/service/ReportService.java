package com.dnd8th.service;

import com.dnd8th.dao.report.MonthlyBlockTaskGetDao;
import com.dnd8th.dto.report.MonthlyBlockGetDTO;
import com.dnd8th.dto.report.MonthlyTaskGetDTO;
import com.dnd8th.dto.report.ReportBlockGetResponse;
import com.dnd8th.error.exception.report.MonthInputInvalidException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ReportService {

    private final MonthlyBlockTaskGetDao monthlyBlockTaskGetDao;

    private static void isMonthValid(Integer month) {
        if (month < 1 || month > 12) {
            throw new MonthInputInvalidException();
        }
    }

    public ReportBlockGetResponse getMostTaskRateBlock(String userEmail, Integer month) {
        isMonthValid(month);

        List<MonthlyBlockGetDTO> monthlyBlock = getMonthlyBlock(userEmail, month);
        if (monthlyBlock != null) {
            String mostTaskRateBlockContent = getMostTaskRateBlockContent(monthlyBlock);

            return new ReportBlockGetResponse(mostTaskRateBlockContent);
        }

        return new ReportBlockGetResponse(null);
    }

    public ReportBlockGetResponse getMostMadeBlock(String userEmail, Integer month) {
        return null;
    }

    private String getMostTaskRateBlockContent(List<MonthlyBlockGetDTO> monthlyBlock) {
        //hash map key: content string, value: task achieved rate
        Map<String, List<Double>> blockTaskRateMap = new HashMap<>();
        for (MonthlyBlockGetDTO block : monthlyBlock) {
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

    private List<MonthlyBlockGetDTO> getMonthlyBlock(String userEmail, Integer month) {
        return monthlyBlockTaskGetDao.getMonthlyBlockAndTask(userEmail, month);
    }

}
