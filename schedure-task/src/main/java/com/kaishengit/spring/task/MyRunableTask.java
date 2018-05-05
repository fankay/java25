package com.kaishengit.spring.task;

public class MyRunableTask  implements Runnable {

    public void run() {
        System.out.println("job begin-----------------------------");
        System.out.println("Hello,Spring Thread Pool......." + Thread.currentThread().getName());
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("job end-----------------------------");
    }
}
