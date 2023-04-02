package com.dnd8th.service;

import com.dnd8th.dao.report.MonthlyBlockGetDao;
import com.dnd8th.dao.report.MonthlyBlockTaskGetDao;
import com.dnd8th.dto.report.MonthlyBlockAndTaskGetDTO;
import com.dnd8th.dto.report.MonthlyBlockGetDTO;
import com.dnd8th.dto.report.MonthlyBlockReportGetResponse;
import com.dnd8th.dto.report.MonthlyDayComparisonGetResponse;
import com.dnd8th.dto.report.MonthlyTaskGetDTO;
import com.dnd8th.dto.report.ReportBlockGetResponse;
import com.dnd8th.dto.report.ReportMonthlyComparisonGetResponse;
import com.dnd8th.error.exception.report.DayInputInvalidException;
import com.dnd8th.error.exception.report.MonthInputInvalidException;
import com.dnd8th.error.exception.report.SortInputInvalidException;
import com.dnd8th.util.DateParser;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
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
    private final DateParser dateParser;

    private static void isMonthValid(Integer month) {
        if (month < 1 || month > 12) {
            throw new MonthInputInvalidException();
        }
    }

    private static void isDateValid(Integer Date) {
        if (Date < 1 || Date > 31) {
            throw new DayInputInvalidException();
        }
    }

    private static Map<String, List<Double>> getContentAchieveRateMap(
            List<MonthlyBlockAndTaskGetDTO> monthlyBlock) {
        Map<String, List<Double>> blockTaskRateMap = new HashMap<>();
        for (MonthlyBlockAndTaskGetDTO block : monthlyBlock) {
            List<MonthlyTaskGetDTO> tasks = block.getTasks();
            if (tasks.size() == 0) { //task 미 존재시 달성도 0.0으로 처리
                String blockContent = block.getTitle();

                List<Double> rates = blockTaskRateMap.getOrDefault(blockContent, new ArrayList<>());
                rates.add(0.0);

                blockTaskRateMap.put(blockContent, rates);
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
        return blockTaskRateMap;
    }

    private static Integer getAchieveRateAverage(
            List<MonthlyBlockAndTaskGetDTO> monthlyBlock) {
        List<Double> achieveRates = new ArrayList<>();
        for (MonthlyBlockAndTaskGetDTO block : monthlyBlock) {
            List<MonthlyTaskGetDTO> tasks = block.getTasks();
            if (tasks.size() == 0) { //task 미 존재시 달성도 0.0으로 처리
                achieveRates.add(0.0);
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
            achieveRates.add(taskAchievedRate);
        }

        if (achieveRates.isEmpty()) {
            return 0;
        }
        return (int) (achieveRates.stream().mapToDouble(Double::doubleValue).average().getAsDouble()
                * 100);
    }

    private static void extractMonthlyBlockAndTask(List<MonthlyBlockAndTaskGetDTO> monthlyBlock,
            Map<String, List<Double>> blockTaskRateMap,
            Map<String, List<Integer>> blockAchievedCountMap,
            Map<String, List<Integer>> blockTotalCountMap) {
        for (MonthlyBlockAndTaskGetDTO block : monthlyBlock) {
            List<MonthlyTaskGetDTO> tasks = block.getTasks();
            if (tasks.size() == 0) { //task 미 존재시 강제 누락
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

            List<Integer> achievedCounts = blockAchievedCountMap.getOrDefault(blockContent,
                    new ArrayList<>());
            achievedCounts.add(achievedTaskCount);

            List<Integer> totalCounts = blockTotalCountMap.getOrDefault(blockContent,
                    new ArrayList<>());
            totalCounts.add(totalTaskCount);

            blockTaskRateMap.put(blockContent, rates);
            blockAchievedCountMap.put(blockContent, achievedCounts);
            blockTotalCountMap.put(blockContent, totalCounts);
        }
    }

    public ReportBlockGetResponse getMostTaskRateBlock(String userEmail, Integer year,
            Integer month) {
        isMonthValid(month);

        List<MonthlyBlockAndTaskGetDTO> monthlyBlock = getMonthlyBlockAndTask(userEmail, year,
                month);
        if (monthlyBlock.isEmpty()) {
            return new ReportBlockGetResponse(null);
        }

        String mostTaskRateBlockContent = getMostTaskRateBlockContent(monthlyBlock);
        return new ReportBlockGetResponse(mostTaskRateBlockContent);
    }

    public ReportBlockGetResponse getMostMadeBlock(String userEmail, Integer year, Integer month) {
        isMonthValid(month);

        List<MonthlyBlockGetDTO> monthlyBlock = getMonthlyBlock(userEmail, year, month);
        if (monthlyBlock.isEmpty()) {
            return new ReportBlockGetResponse(null);
        }

        String mostMadeBlockContent = getMostMadeBlockContent(monthlyBlock);
        return new ReportBlockGetResponse(mostMadeBlockContent);
    }

    public ReportBlockGetResponse getLeastTaskRateBlock(String userEmail, Integer year,
            Integer month) {
        isMonthValid(month);

        List<MonthlyBlockAndTaskGetDTO> monthlyBlock = getMonthlyBlockAndTask(userEmail, year,
                month);
        if (monthlyBlock.isEmpty()) {
            return new ReportBlockGetResponse(null);
        }

        String leastTaskRateBlockContent = getLeastTaskRateBlockContent(monthlyBlock);
        return new ReportBlockGetResponse(leastTaskRateBlockContent);
    }

    public ReportMonthlyComparisonGetResponse getMonthlyComparison(String userEmail, Integer year,
            Integer month,
            Integer day) {
        isMonthValid(month);
        isDateValid(day);

        List<MonthlyBlockAndTaskGetDTO> monthlyBlock = getMonthlyBlockAndTaskWithDate(userEmail,
                year,
                month, day);

        Integer compareYear = getCompareYear(year, month);
        Integer compareMonth = getCompareMonth(month);

        List<MonthlyBlockAndTaskGetDTO> prevMonthlyBlock = getMonthlyBlockAndTaskWithDate(userEmail,
                compareYear, compareMonth, day);

        return ReportMonthlyComparisonGetResponse.builder()
                .nowMonthAchievementRate(getAchieveRateAverage(monthlyBlock))
                .lastMonthAchievementRate(getAchieveRateAverage(prevMonthlyBlock))
                .build();
    }

    public MonthlyDayComparisonGetResponse getMonthlyDayComparison(String userEmail, Integer year,
            Integer month) {
        isMonthValid(month);

        List<MonthlyBlockAndTaskGetDTO> monthlyBlock = getMonthlyBlockAndTask(userEmail, year,
                month);

        Map<Integer, List<Double>> monthlyDayStatus = getMonthlyDayStatus(monthlyBlock);
        return monthlyDayStatusToPercentage(monthlyDayStatus);
    }

    public List<MonthlyBlockReportGetResponse> getBlockReport(String userEmail, String sort,
            Integer year, Integer month) {
        isMonthValid(month);
        isSortValid(sort);

        List<MonthlyBlockAndTaskGetDTO> monthlyBlock = getMonthlyBlockAndTask(userEmail, year,
                month);

        return monthlyBlockExtractAndSort(monthlyBlock, sort);
    }

    private List<MonthlyBlockReportGetResponse> monthlyBlockExtractAndSort(
            List<MonthlyBlockAndTaskGetDTO> monthlyBlock, String sort) {
        //data extract
        Map<String, List<Double>> blockTaskRateMap = new HashMap<>();
        Map<String, List<Integer>> blockAchievedCountMap = new HashMap<>();
        Map<String, List<Integer>> blockTotalCountMap = new HashMap<>();
        extractMonthlyBlockAndTask(monthlyBlock, blockTaskRateMap, blockAchievedCountMap,
                blockTotalCountMap);

        List<MonthlyBlockReportGetResponse> monthlyBlockReportGetResponses = new ArrayList<>();
        extractedDataToResponseDTO(blockTaskRateMap, blockAchievedCountMap, blockTotalCountMap,
                monthlyBlockReportGetResponses);

        //sort
        sortMonthlyBlockReportGetResponse(sort, monthlyBlockReportGetResponses);

        return monthlyBlockReportGetResponses;
    }

    private void sortMonthlyBlockReportGetResponse(String sort,
            List<MonthlyBlockReportGetResponse> monthlyBlockReportGetResponses) {
        if (sort.equals("task-rate")) {
            monthlyBlockReportGetResponses.sort(
                    Comparator.comparing(MonthlyBlockReportGetResponse::getTaskRatePercentage)
                            .reversed());
        } else {
            monthlyBlockReportGetResponses.sort(
                    Comparator.comparing(MonthlyBlockReportGetResponse::getBlockCount).reversed());
        }
    }

    private void extractedDataToResponseDTO(Map<String, List<Double>> blockTaskRateMap,
            Map<String, List<Integer>> blockAchievedCountMap,
            Map<String, List<Integer>> blockTotalCountMap,
            List<MonthlyBlockReportGetResponse> monthlyBlockReportGetResponses) {
        for (String s : blockTaskRateMap.keySet()) {
            List<Double> rates = blockTaskRateMap.get(s);
            List<Integer> achievedCounts = blockAchievedCountMap.getOrDefault(s, new ArrayList<>());
            List<Integer> totalCounts = blockTotalCountMap.getOrDefault(s, new ArrayList<>());
            Double average = rates.stream().mapToDouble(Double::doubleValue).average()
                    .getAsDouble();
            Integer averagePercent = (int) (average * 100);
            monthlyBlockReportGetResponses.add(
                    MonthlyBlockReportGetResponse.builder()
                            .title(s)
                            .blockCount(rates.size())
                            .achievedTaskCount(
                                    achievedCounts.stream().mapToInt(Integer::intValue).sum())
                            .totalTaskCount(totalCounts.stream().mapToInt(Integer::intValue).sum())
                            .taskRatePercentage(averagePercent)
                            .build()
            );
        }
    }

    private void isSortValid(String sort) {
        if (!sort.equals("task-rate") && !sort.equals("most-block")) {
            throw new SortInputInvalidException();
        }
    }

    private MonthlyDayComparisonGetResponse monthlyDayStatusToPercentage(
            Map<Integer, List<Double>> monthlyDayStatus) {
        List<Integer> monthlyDayStatusPercentage = new ArrayList<>(Collections.nCopies(8, 0));
        for (Integer dayOfWeek : monthlyDayStatus.keySet()) {
            List<Double> rates = monthlyDayStatus.get(dayOfWeek);
            Double average = rates.stream().mapToDouble(Double::doubleValue).average()
                    .getAsDouble();
            monthlyDayStatusPercentage.set(dayOfWeek, (int) (average * 100));
        }

        return MonthlyDayComparisonGetResponse.builder()
                .sunday(monthlyDayStatusPercentage.get(1))
                .monday(monthlyDayStatusPercentage.get(2))
                .tuesday(monthlyDayStatusPercentage.get(3))
                .wednesday(monthlyDayStatusPercentage.get(4))
                .thursday(monthlyDayStatusPercentage.get(5))
                .friday(monthlyDayStatusPercentage.get(6))
                .saturday(monthlyDayStatusPercentage.get(7))
                .build();
    }

    private Map<Integer, List<Double>> getMonthlyDayStatus(
            List<MonthlyBlockAndTaskGetDTO> monthlyBlock) {
        Map<Integer, List<Double>> blockTaskRateMap = new HashMap<>();
        for (MonthlyBlockAndTaskGetDTO block : monthlyBlock) {
            List<MonthlyTaskGetDTO> tasks = block.getTasks();
            if (tasks.size() == 0) { //task 미 존재시 달성도 0.0으로 처리
                Date date = block.getDate();
                Integer dayOfWeek = dateParser.getDayOfWeekInt(date);

                List<Double> rates = blockTaskRateMap.getOrDefault(dayOfWeek,
                        new ArrayList<>());
                rates.add(0.0);

                blockTaskRateMap.put(dayOfWeek, rates);
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

            Date date = block.getDate();
            Integer dayOfWeek = dateParser.getDayOfWeekInt(date);

            List<Double> rates = blockTaskRateMap.getOrDefault(dayOfWeek, new ArrayList<>());
            rates.add(taskAchievedRate);

            blockTaskRateMap.put(dayOfWeek, rates);
        }

        return blockTaskRateMap;
    }

    private Integer getCompareMonth(Integer month) {
        if (month == 1) {
            return 12;
        }
        return month - 1;
    }

    private Integer getCompareYear(Integer year, Integer month) {
        if (month == 1) {
            return year - 1;
        }
        return year;
    }


    private String getLeastTaskRateBlockContent(List<MonthlyBlockAndTaskGetDTO> monthlyBlock) {
        //hash map key: content string, value: task achieved rate
        Map<String, List<Double>> blockTaskRateMap = getContentAchieveRateMap(monthlyBlock);

        //one more loop and get the highest value(task achieved rate)
        String leastTaskRateBlockContent = null;
        Double lowestAvgRate = 1.0;

        for (Map.Entry<String, List<Double>> entry : blockTaskRateMap.entrySet()) {
            List<Double> rates = entry.getValue();

            Double avgRate = rates.stream().mapToDouble(rate -> rate).average().orElse(0.0);

            if (avgRate < lowestAvgRate) {
                lowestAvgRate = avgRate;
                leastTaskRateBlockContent = entry.getKey();
            }
        }

        //return the key(content string)
        return leastTaskRateBlockContent;
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

    private List<MonthlyBlockGetDTO> getMonthlyBlock(String userEmail, Integer year,
            Integer month) {
        return monthlyBlockGetDao.getMonthlyBlockList(userEmail, year, month);
    }

    private String getMostTaskRateBlockContent(List<MonthlyBlockAndTaskGetDTO> monthlyBlock) {
        //hash map key: content string, value: task achieved rate
        Map<String, List<Double>> blockTaskRateMap = getContentAchieveRateMap(
                monthlyBlock);

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

    private List<MonthlyBlockAndTaskGetDTO> getMonthlyBlockAndTask(String userEmail, Integer year,
            Integer month) {
        return monthlyBlockTaskGetDao.getMonthlyBlockAndTask(userEmail, year, month);
    }

    private List<MonthlyBlockAndTaskGetDTO> getMonthlyBlockAndTaskWithDate(String userEmail,
            Integer year,
            Integer month, Integer day) {
        return monthlyBlockTaskGetDao.getMonthlyBlockAndTaskWithDate(userEmail, year, month,
                day);
    }
}
