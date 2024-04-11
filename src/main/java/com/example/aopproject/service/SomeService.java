package com.example.aopproject.service;

import com.example.aopproject.annotation.TrackAsyncTime;
import com.example.aopproject.annotation.TrackTime;
import com.example.aopproject.utils.ThreadUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SomeService {

    @TrackTime
    @Scheduled(fixedDelay = 3_000)
    public void syncMethodOne() {
        int minTime = 10;
        int maxTime = 150;
        int gap = maxTime - minTime;
        ThreadUtils.waitTime(((int) (Math.random() * gap)) + minTime);
    }

    @TrackTime
    @Scheduled(fixedDelay = 4_200)
    public void syncMethodTwo() {
        int minTime = 20;
        int maxTime = 250;
        int gap = maxTime - minTime;
        ThreadUtils.waitTime(((int) (Math.random() * gap)) + minTime);
    }

    @Async
    @TrackAsyncTime
    @Scheduled(fixedDelay = 4_400)
    public void asyncMethodOne() {
        int minTime = 50;
        int maxTime = 500;
        int gap = maxTime - minTime;
        ThreadUtils.waitTime(((int) (Math.random() * gap)) + minTime);
    }

    @Async
    @TrackAsyncTime
    @Scheduled(fixedDelay = 5_700)
    public void asyncMethodTwo() {
        int minTime = 80;
        int maxTime = 900;
        int gap = maxTime - minTime;
        ThreadUtils.waitTime(((int) (Math.random() * gap)) + minTime);
    }
}
