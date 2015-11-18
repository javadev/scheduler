package com.github.scheduler.service;

import java.util.List;

import org.joda.time.Interval;

import com.github.scheduler.model.outinterfaces.Schedule;
import com.github.scheduler.service.Interfaces.TaskRepresentation;

/**
 * Service layer of application. Keeps all logic of application
 * except "buisness logic" which are in the Model implementation.
 */
public interface Service{
    
    /**
     * @return calculated schedule uses 
     * @param requested interval  of dates and 
     * @param certain user
     */
    public Schedule getSchedule(Interval interval, String userName);
    
    /**
     * Adds new user to application
     * @param String username - new user's username
     * @param String passwor - password of new user
     */
    public boolean addNewUser(String username, String password);
    
    /**
     * @return TaskRepresentation object
     * @param long taskId - id number of user's task
     */
    public TaskRepresentation getTaskRepresentation(long taskId);
    
    /**
     * @return List<TaskRepresentation> all the tasks from user
     * like task representations
     * @param username - username for necessary user
     */
    public List<TaskRepresentation> getTaskRepresentations(String username);
    
    /**
     * Add or updates task for specified user
     * @param username name of user which task will be added or updated
     * @param taskRepe parameter for task to add or update
     */
    public void saveOrUpdateTask(TaskRepresentation taskRepresentation);
    
    /**
     * Deletes task from specified
     * @param id of task to delete
     */
    public void deleteTask(long taskId);
}
