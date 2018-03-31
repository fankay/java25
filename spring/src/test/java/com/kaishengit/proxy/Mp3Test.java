package com.kaishengit.proxy;

import org.junit.Test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class Mp3Test {

    @Test
    public void test() {
        Mp3 mp3 = new Mp3();
        Mp3LogProxy proxy = new Mp3LogProxy(mp3);
        Player player = new Mp3AdProxy(proxy);
        player.play("Hello");
    }

    @Test
    public void jdkProxy() {
        final Mp3 mp3 = new Mp3();
        //获取目标对象的类加载器
        ClassLoader classLoader = mp3.getClass().getClassLoader();
        //目标对象的接口数组
        Class[] interfaces = mp3.getClass().getInterfaces();

        Player player = (Player) Proxy.newProxyInstance(classLoader,
                interfaces, new InvocationHandler() {
                    /**
                     * 代理对象执行的方法
                     * @param proxy 正在执行的代理对象，没什么用
                     * @param method 客户端调用的方法（目标对象的方法）
                     * @param args 方法的参数列表
                     * @return 方法返回值
                     * @throws Throwable
                     */
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                String methodName = method.getName();
                System.out.println("方法:"+methodName+"开始调用");
                //调用目标对象的方法
                Object reuslt = method.invoke(mp3,args);
                System.out.println("方法:"+methodName+"调用结束");
                return reuslt;
            }
        });

        player.play("Hello");
        player.stop();


    }
}
