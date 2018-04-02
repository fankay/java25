package com.kaishengit.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;

import java.util.Arrays;

public class MyAdvice {

    /**
     * 前置通知
     */
    public void beforeAdvice(JoinPoint joinPoint) {
        //方法名称
        String methodName = joinPoint.getSignature().getName();
        //方法的参数列表
        Object[] args = joinPoint.getArgs();
        System.out.println(methodName + " -> 前置通知" + Arrays.asList(args));
    }

    public void afterAdvice(Object result) {
        System.out.println("后置通知 -> " + result);
    }
    public void exceptionAdvice(Exception ex) {
        System.out.println("异常通知 -> " + ex.getMessage());
    }
    public void finallyAdvice() {
        System.out.println("最终通知");
    }

    /**
     * 环绕通知
     */
    public Object aroundAdvice(ProceedingJoinPoint joinPoint) {
        Object result = null;
        try {
            System.out.println("--------------");
            //表示目标对象的方法执行
            result = joinPoint.proceed();
            System.out.println("*****************");
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            System.out.println("XXXXXXXXXXXXXXXXXXXX");
        } finally {
            System.out.println("$$$$$$$$$$$$$$$$$$$$$");
        }
        return result;
    }

}
