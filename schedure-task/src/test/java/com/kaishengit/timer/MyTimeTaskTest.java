package com.kaishengit.timer;

import org.junit.Test;

import java.io.IOException;
import java.util.Date;
import java.util.Timer;

public class MyTimeTaskTest {

    @Test
    public void test() throws IOException {
        MyTimeTask timeTask = new MyTimeTask();

        Timer timer = new Timer();
        //将任务延迟3秒执行
        //timer.schedule(timeTask,3000);
        //在指定的时间去执行任务
        //timer.schedule(timeTask,new Date());
        //延迟0毫秒，并每秒钟执行一次
        //timer.schedule(timeTask,0,1000);
        //从指定时间开始执行，每隔2秒钟执行一次
        //timer.schedule(timeTask,new Date(),2000);
        timer.scheduleAtFixedRate(timeTask,0,100);

        System.in.read();
    }

}