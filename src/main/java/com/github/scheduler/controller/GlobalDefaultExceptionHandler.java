package com.github.scheduler.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/*
 * This is an exeption handler.
 * Handles all excrptions in applications.
 */
@ControllerAdvice
public class GlobalDefaultExceptionHandler {
    
    /*
     * Exception.class - which exception method
     * will be intercept
     */
    @ExceptionHandler(value = Exception.class)
    public String processException(Exception e){
        
        e.printStackTrace();
        
        return "exception_page";
    }
}
