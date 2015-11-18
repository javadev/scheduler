package com.github.scheduler.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.github.scheduler.dto.REGPageMessage;
import com.github.scheduler.service.Service;

/*
 * This is a registration page Controller
 */
//Controller of the registration page
@Controller
@RequestMapping({"/registration"})
public class RegistrationPageController {

    @Autowired
    private Service service;
    
    //show registration page
    @RequestMapping(method = RequestMethod.GET)
    public String register() {
        
        return "registration_page";
    }
    
    /*
     * Calls with request with parameters.
     * Shows login page if registration done
     * or errors if inputed data were wrong.
     */
    @RequestMapping(method = RequestMethod.POST)
    public String verifyRegistrationData(@ModelAttribute("message")
            @Valid REGPageMessage message, Errors errors,
                    ModelMap model) {

        //if there are errors in password or username restrictions
        if(errors.hasErrors()){
            return "registration_page";
        }else{//if no errors
            
            //if Dao saved new user
            if(service.addNewUser(message.getUsername(),
                    message.getPassword())){
                
                return "redirect:login";
            }else{//if Dao didn't save new user
                
                //message to display on register page
                model.put("error_string", "this name is busy");
                
                return "registration_page";
            }
        }
    }
    
    //adds "message" object to model
    @ModelAttribute("message")
    public REGPageMessage getLoginForm() {
            return new REGPageMessage();
    }
}