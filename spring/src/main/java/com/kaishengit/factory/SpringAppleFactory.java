package com.kaishengit.factory;

import org.springframework.beans.factory.FactoryBean;

public class SpringAppleFactory implements FactoryBean<Apple> {

    public Apple getObject() throws Exception {
        return new Apple();
    }

    public Class<?> getObjectType() {
        return Apple.class;
    }

    public boolean isSingleton() {
        return true;
    }
}
