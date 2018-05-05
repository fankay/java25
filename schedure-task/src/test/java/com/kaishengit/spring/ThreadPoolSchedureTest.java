package com.kaishengit.spring;

import com.kaishengit.spring.task.MyRunableTask;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.util.Date;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-task.xml")
public class ThreadPoolSchedureTest {

    @Autowired
    private ThreadPoolTaskScheduler taskScheduler;

    @Test
    public void test() throws IOException {
        MyRunableTask task = new MyRunableTask();

        //taskScheduler.execute(task);
        //taskScheduler.schedule(task,new Date(System.currentTimeMillis() + 5000));
        //taskScheduler.scheduleWithFixedDelay(task,100);

        //taskScheduler.scheduleAtFixedRate(task,100);
        taskScheduler.schedule(task,new CronTrigger("0/1 * * * * ?"));


        System.in.read();
    }

}
