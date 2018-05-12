package com.kaishengit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class MovieServiceProviderApplication {

    public static void main(String[] args) throws IOException {
        SpringApplication.run(MovieServiceProviderApplication.class, args);
        System.out.println("Service staring....");
        System.in.read();
    }
}
