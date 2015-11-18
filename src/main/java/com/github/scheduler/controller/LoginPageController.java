package com.github.scheduler.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/*
 * This is the login page controller
 */
//Controller of the login page
@Controller
@RequestMapping({"/", "/login"})
public class LoginPageController {
    
    @RequestMapping(method = RequestMethod.GET)
    public String login() {
        
        return "login_page";
    }
}
