package com.example.aopproject.service;

import com.example.aopproject.dto.*;
import com.example.aopproject.entity.MethodExecutionTime;
import com.example.aopproject.repository.MethodExecutionTimeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MethodExecutionTimeService {
    private final MethodExecutionTimeRepository repository;

    @Async
    public void saveMethodExecutionTime(String methodName, Double executionTime) {
        MethodExecutionTime methodExecutionTime = new MethodExecutionTime();
        methodExecutionTime.setMethodName(methodName);
        methodExecutionTime.setExecutionTime(executionTime);
        methodExecutionTime.setExecutionDateTime(LocalDateTime.now());
        repository.save(methodExecutionTime);
    }


    public GeneralInfoForEveryMethodDto getGeneralInfo() {
        List<Object[]> methodStats = repository.findGeneralMethodStatistics();
        List<GeneralMethodInfoDto> infoList = new ArrayList<>();

        for (Object[] stat : methodStats) {
            GeneralMethodInfoDto infoDto = new GeneralMethodInfoDto();
            infoDto.setMethodName((String) stat[0]);
            infoDto.setCallQuantity(((Number) stat[1]).intValue());
            infoDto.setAvgRunningTime(((Number) stat[2]).doubleValue());
            infoList.add(infoDto);
        }

        GeneralInfoForEveryMethodDto generalInfoDto = new GeneralInfoForEveryMethodDto();
        generalInfoDto.setInfo(infoList);

        return generalInfoDto;
    }

    public MethodNames getMethodNames() {
        List<String> methodsNames = repository.findDistinctMethodNames();
        MethodNames names = new MethodNames();
        names.setMethodNames(methodsNames);
        return names;
    }
    private Double calculateMedianRunningTime(String methodName) {
        List<Double> executionTimes = repository.findExecutionTimesByMethodName(methodName).stream()
                .map(obj -> (Double) obj).toList();

        List<Double> sortedExecutionTimes = executionTimes.stream().sorted().toList();

        int size = sortedExecutionTimes.size();
        if (size % 2 == 0) {
            return (sortedExecutionTimes.get(size / 2 - 1) + sortedExecutionTimes.get(size / 2)) / 2.0;
        } else {
            return sortedExecutionTimes.get(size / 2);
        }
    }

    public DetailedInfoForEveryMethodDto getDetailedStats() {
        List<Object[]> methodStats = repository.findAllDetailedMethodStatistics();
        List<DetailedMethodInfoDto> infoList = new ArrayList<>();

        for (Object[] stat : methodStats) {
            DetailedMethodInfoDto infoDto = convert(stat);
            infoList.add(infoDto);
        }

        DetailedInfoForEveryMethodDto detailedInfoDto = new DetailedInfoForEveryMethodDto();
        detailedInfoDto.setInfo(infoList);

        return detailedInfoDto;
    }

    public DetailedMethodInfoDto getDetailedMethodStats(String methodName){
        Object[] stat = repository.findDetailedMethodStatistic(methodName);
        if (stat.length == 0){
            return null;
        }
        return convert((Object[]) stat[0]);
    }

    private DetailedMethodInfoDto convert(Object[] stat){
        DetailedMethodInfoDto infoDto = new DetailedMethodInfoDto();
        infoDto.setMethodName((String) stat[0]);
        infoDto.setCallQuantity(((Number) stat[1]).intValue());
        infoDto.setMinRunningTime(((Number) stat[2]).doubleValue());
        infoDto.setMaxRunningTime(((Number) stat[3]).doubleValue());
        infoDto.setAvgRunningTime(((Number) stat[4]).doubleValue());
        infoDto.setMedianRunningTime(calculateMedianRunningTime((String) stat[0]));

        return infoDto;
    }


}
