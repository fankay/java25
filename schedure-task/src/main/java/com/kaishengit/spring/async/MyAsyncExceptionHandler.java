package com.kaishengit.spring.async;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.aop.interceptor.SimpleAsyncUncaughtExceptionHandler;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Component
public class MyAsyncExceptionHandler extends SimpleAsyncUncaughtExceptionHandler {

    @Override
    public void handleUncaughtException(Throwable ex, Method method, Object... params) {
        System.out.println("####################################################");
        System.out.println("Exception message - " + ex.getMessage());
        System.out.println("Method name - " + method.getName());
        for (Object param : params) {
            System.out.println("Parameter value - " + param);
        }
        System.out.println("####################################################");
    }
}
