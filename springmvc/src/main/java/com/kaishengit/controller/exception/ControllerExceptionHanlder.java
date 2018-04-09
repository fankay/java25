package com.kaishengit.controller.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.IOException;

@ControllerAdvice
public class ControllerExceptionHanlder {

    @ExceptionHandler(IOException.class)
    public String ioExceptionHandler() {
        return "error/500";
    }

}
