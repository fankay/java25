package com.kaishengit.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import javax.sql.DataSource;

@Configuration
public class QuartzConfig {

    @Bean
    public SchedulerFactoryBean schedulerFactoryBean(DataSource dataSource,
                                                     DataSourceTransactionManager transactionManager) {
        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
        //设置数据库练级池
        schedulerFactoryBean.setDataSource(dataSource);
        //设置事务管理器
        schedulerFactoryBean.setTransactionManager(transactionManager);
        //配置文件的位置
        schedulerFactoryBean.setConfigLocation(new ClassPathResource("quartz.properties"));
        //覆盖现有的任务
        schedulerFactoryBean.setOverwriteExistingJobs(true);
        //设置Spring容器的对象
        schedulerFactoryBean.setApplicationContextSchedulerContextKey("applicationContext");
        return schedulerFactoryBean;
    }


}