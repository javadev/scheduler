package com.github.scheduler.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/*
 * This is a help page Controller
 */
//Controller of the registration page
@Controller
@RequestMapping({"/app/help"})
public class HelpPageController {
    
    //show registration page
    @RequestMapping(method = RequestMethod.GET)
    public String register() {
        
        return "help_page";
    }
}