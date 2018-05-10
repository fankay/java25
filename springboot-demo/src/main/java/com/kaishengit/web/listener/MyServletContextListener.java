package com.kaishengit.web.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class MyServletContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        System.out.println("ServletContext Init.................");
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
