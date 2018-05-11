package com.kaishengit.job;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class SpringScheduJob {

    /*@Scheduled(fixedRate = 3000)
    @Async("taskExecutor")
    public void fixedRateJob() {
        System.out.println("fixedRateJob :" + System.currentTimeMillis());
    }*/

}
