package com.github.scheduler.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.joda.time.Interval;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.stereotype.Component;

import com.github.scheduler.model.domain.User;
import com.github.scheduler.model.domain.task.Task;
import com.github.scheduler.model.outinterfaces.Model;
import com.github.scheduler.model.outinterfaces.Schedule;
import com.github.scheduler.repository.TaskDao;
import com.github.scheduler.repository.UserDao;
import com.github.scheduler.service.Interfaces.TaskRepresentation;
import com.github.scheduler.service.auxiliaryobjects.TaskRepresentationImpl;
import com.github.scheduler.service.auxiliaryobjects.UserFactory;

@Transactional
@Component
public class ServiceImpl implements Service{

    @Autowired
    private Model model;
    
    @Autowired
    private UserDao userDao;
    
    @Autowired
    private TaskDao taskDao;
    
    @Override
    public Schedule getSchedule(Interval interval, String userName) {
        
        User user = userDao.getUserByUserName(userName);
        Schedule schedule = model.calculateSchedule(user, interval);
        
        return schedule;
    }

    @Override
    public boolean addNewUser(String username, String password) {
                
        User existUser = userDao.getUserByUserName(username);
                            
        //If user with the same username exists
        if(existUser != null){
            return false;
        }

        /*
         * encodes password to keeping it encoded in DB. 
         * "#sch$" it's just random char sequence, must match 
         * with another one in SecurityConfig configuer method.
         */
        PasswordEncoder encoder = new StandardPasswordEncoder("#sch$");
                
        //creates new user
        User user = UserFactory.createStandartNewUser(
                username, "USER", encoder.encode(password));
                
        //tries to save user
        userDao.saveNewUser(user);

        return true;
    }

    @Override
    public TaskRepresentation getTaskRepresentation(long taskId) {
        
        Task task = taskDao.getTask(taskId);
        
        return new TaskRepresentationImpl(task);
    }

    @Override
    public List<TaskRepresentation> getTaskRepresentations(String username) {
        List<TaskRepresentation> tasksRepr = 
                new ArrayList<TaskRepresentation>();
        
        //creates TaskRepresentations
        for(Task t: taskDao.getTasks(username)){
            tasksRepr.add(new TaskRepresentationImpl(t));
        }
            
        return tasksRepr;
    }

    @Override
    public void saveOrUpdateTask(TaskRepresentation taskRepresentation) {
        Task task = new Task();
        
        fillTaskWithTaskRepr(task, taskRepresentation);
        
        taskDao.saveOrUpdateTask(task);
    }

    @Override
    public void deleteTask(long taskId) {
        taskDao.deleteTask(taskId);
    }
    
    /*
     * Fills Task field with values from TaskRepresentation
     */
    private Task fillTaskWithTaskRepr(Task task, TaskRepresentation 
            taskRepr){
        
        task.setId(taskRepr.getId());
        task.setUser(userDao.getUserByUserName(taskRepr.getUserName()));
        //Updates all fields
        task.setTitle(taskRepr.getTitle());
        task.setDescription(taskRepr.getDescription());
        task.setType(taskRepr.getType());
        task.setActive(taskRepr.getIsActive());
        task.setNecessaryTime(taskRepr.getNecessaryTime());
                
        //it's safety because a LocalData is immutable
        task.setStartDate(taskRepr.getStartDate());
                
        //it's safety because an Interval is immutable
        task.setInterval(taskRepr.getInterval());
                
        for(int i = 1; i < 8; i++){
            task.setActiveDayAt(i, taskRepr.getActiveDayAt(i));
        }
                
        for(int i = 0; i < 24; i++){
            task.setActiveHourAt(i, taskRepr.getActiveHourAt(i));
        }
        
        return task;
    }
}
