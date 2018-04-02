package com.kaishengit.proxy;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.junit.Test;

import java.lang.reflect.Method;

public class SalesTest {

    @Test
    public void test() {
        SalesProxy salesProxy = new SalesProxy();
        salesProxy.sale();
    }


    @Test
    public void testCglib() {
        Enhancer enhancer = new Enhancer();
        //设置目标对象
        enhancer.setSuperclass(Sales.class);
        //设置代理对象的代理行为
        enhancer.setCallback(new MethodInterceptor() {
            /**
             * 添加代理行为
             * @param target 目标对象
             * @param method 目标的方法 一般不用
             * @param args 方法的参数
             * @param methodProxy 产生的子类方法代理
             * @return
             * @throws Throwable
             */
            public Object intercept(Object target, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
                try {
                    System.out.println("-----------------------------------");
                    //执行父类中的方法
                    Object result = methodProxy.invokeSuper(target,args);

                    System.out.println("************************************");
                    return result;
                } catch (Exception ex) {
                    ex.printStackTrace();
                    System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&");
                    return null;
                } finally {
                    System.out.println("((((((((((((())))))))))))))))))");
                }

            }
        });

        //产生代理对象
        Sales sales = (Sales) enhancer.create();
        sales.sale();
    }
}
