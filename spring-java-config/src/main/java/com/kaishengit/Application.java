package com.kaishengit;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@ComponentScan//(basePackages = "com.kaishengit")
@EnableAspectJAutoProxy
public class Application {
}
