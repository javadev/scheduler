package com.github.scheduler.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.joda.time.Interval;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.github.scheduler.dto.DVPageMessage;
import com.github.scheduler.model.outinterfaces.Schedule;
import com.github.scheduler.service.Service;

/*
 * This is a controller for Days view page
 */
@Controller
//@RequestMapping(value = "/user**", method = RequestMethod.POST)
@RequestMapping("/app/days")
public class DaysViewController {
    
    @Autowired
    private Service service;
    
    /*
     * Calls with request with parameters
     * Returns requested interval view.
     */
    //TODO verify to put message from method instead from session
    @RequestMapping(method = RequestMethod.POST)
    public String requestDatePage(@ModelAttribute("message")
            @Valid DVPageMessage message,
            Errors errors, ModelMap model, 
            HttpServletRequest request) {
        
        Schedule schedule = null;//schedule
        Interval interval = null;//old value of interval to count schedule
        
        LocalDate startInterval = null;//start of schedule interval
        LocalDate endInterval = null;//end of schedule interval
        
        /*
         * if there are no errors (without deep verification) in
         * entered dates, it makes new interval, otherwise use old
         * and @Validate will show message "invalid start or 
         * end date (see DVPMessage class)" 
         */
        if(!errors.hasErrors()){
            //set up 2 months interval
            startInterval = new LocalDate(message.getStartDay());
            endInterval = new LocalDate(message.getEndDay());
            
            //because Interval excludes last day
            endInterval = endInterval.plusDays(1);
            
            interval = new Interval(startInterval.toDate().getTime(),
                    endInterval.toDate().getTime());
            
            //stores last interval
            request.getSession().setAttribute("interval", interval);
            
        }else{
            interval = (Interval) request.getSession().
                    getAttribute("interval");
        }
        
        
        //retrieve username from Spring Security
        Authentication auth = SecurityContextHolder.getContext().
                getAuthentication();
        User user = (User)auth.getPrincipal();
        
        schedule = service.getSchedule(interval, user.getUsername());
        
        
        //makes them avaible for jsp
        model.put("interval", interval);
        model.put("schedule", schedule);
        
        return "days_view_page";
    }
    
    
    /*
     * Calls with request without parameters
     * Returns current and next months view.
     */
    @RequestMapping(method = RequestMethod.GET)
    public String defaultDatePage(ModelMap model, 
            HttpServletRequest request) {
        
        LocalDate today = new LocalDate();
        Interval interval = null;
        LocalDate startInterval = null;
        LocalDate endInterval = null;
        
        //set up 2 months interval
        startInterval = today.minusMonths(1);
        endInterval = today.plusMonths(1);
        
        interval = new Interval(startInterval.toDate().getTime(),
                endInterval.toDate().getTime());
        
        
        //retrieve userId from Spring Security
        Authentication auth = SecurityContextHolder.getContext().
                getAuthentication();
        User user = (User)auth.getPrincipal();
                
        //TODO make it work
        Schedule schedule = service.getSchedule(interval, user
                .getUsername());
        
        model.put("schedule", schedule);
        
        //puts interval for jsp rendering
        model.put("interval", interval);
        
        //store last interval
        request.getSession().setAttribute("interval", interval);
        
        return "days_view_page";
    }
    
    /*
     * adds DVPageMessage objects to JSP page (model). Not 
     * required, DVPageMessage can be put to model
     * clearly.
     */
    @ModelAttribute("message")
    public DVPageMessage getLoginForm() {
            return new DVPageMessage();
    }
}
