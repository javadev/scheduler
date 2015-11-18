package com.github.scheduler.service.auxiliaryobjects;

import org.joda.time.Interval;
import org.joda.time.LocalDate;

import com.github.scheduler.model.domain.User;
import com.github.scheduler.model.domain.task.Task;
import com.github.scheduler.model.domain.task.TypeOfTask;



/**
 * It generates new user with given parameters.
 */
public class UserFactory {
    
    private static UserFactory instance;

    //hidden constructor by default
    private UserFactory(){
    }
    
    /**
     * Its creates new User object with filled -2 months 
     * backward and 1 year forward filled days, to make
     * possible show new User in view. Thread safe.
     */
    public static synchronized User createStandartNewUser(String userName,
            String role, String password){
        
        if(instance == null){
            instance = new UserFactory();
        }
        
        LocalDate startDate = new LocalDate().minusMonths(2);
        LocalDate endDate = new LocalDate().plusYears(1);
        
        Interval interval = new Interval(startDate.toDate().getTime()
                , endDate.toDate().getTime());
        
        User user =  instance.createUserWithRoutine(interval);
        
        user.setName(userName);
        user.setRole(role);
        user.setPassword(password);
        
        return user;
    }

    /*
     * Creates new user, then creates simple routine tasks
     * and fills user with them 
     */
    private User createUserWithRoutine(Interval interval) {
        
        User user = new User();
        
        Task sleep = new Task();
        
        user.addTask(sleep);
        
        sleep.setTitle("sleeping");
        sleep.setInterval(interval);
        sleep.setType(TypeOfTask.Routine);
        sleep.setNecessaryTime(1);
        
        for(int i = 1; i < 8; i++){
            sleep.setActiveDayAt(i, true);
        }
        
        for(int j = 22; j < 24; j++){
            sleep.setActiveHourAt(j, true);
        }
        
        for(int j = 0; j < 6; j++){
            sleep.setActiveHourAt(j, true);
        }
        
        makeTasksActive(user);
        
        return user;
    }
    
    //makes all task active 
    private void makeTasksActive(User user){
        for(Task t: user.getTasks()){
            t.setActive(true);
        }
    }
}
