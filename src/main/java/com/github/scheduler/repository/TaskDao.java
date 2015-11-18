package com.github.scheduler.repository;

import java.util.List;

import com.github.scheduler.model.domain.task.Task;

/**
 * This is the Task DAO interface
 */
public interface TaskDao {
    
    /**
     * @return TaskRepresentation object
     * @param long taskId - id number of user's task
     */
    public Task getTask(long taskId);
    
    /**
     * @param String username which tasks to return
     * @return List<TaskRepresentation> all the tasks from user
     * like task representations
     */
    public List<Task> getTasks(String username);
    
    /**
     * Add or updates task for specified user
     * @param Task to persist or update in db
     */
    public void saveOrUpdateTask(Task task);
    
    /**
     * Deletes task from specified
     * @param id of task to delete
     */
    public void deleteTask(long taskId);
}
