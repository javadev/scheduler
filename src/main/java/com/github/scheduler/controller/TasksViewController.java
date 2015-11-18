package com.github.scheduler.controller;

import com.github.scheduler.service.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/*
 * This controller shows page, which contains all the tasks
 * of authenticated user.
 */
@Controller
@RequestMapping("/app/task")
public class TasksViewController {
    
    @Autowired
    private Service service;
    
    /*
     * Processes get requests
     */
    @RequestMapping(value="/review", method = RequestMethod.GET)
    public String showTaska(ModelMap model){
        
        Authentication auth = SecurityContextHolder.getContext().
                getAuthentication();
        
        model.put("taskRepr", service.getTaskRepresentations(
                ((User)auth.getPrincipal()).getUsername()));

        return "tasks_view_page";
    }
    
    /*
     * Processes delete requests
     */
    /*
     * TODO add verifying is task to delete belongs to current user
     * otherwise 
     */
    @RequestMapping(value="/delete", method = RequestMethod.GET)
    public String deleteTask(@RequestParam(value="task_id") 
            long taskId){
        
        service.deleteTask(taskId);

        return "redirect:/app/task/review";
    }
}
