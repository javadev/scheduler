package com.github.scheduler.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.github.scheduler.model.domain.task.Task;

/**
 * This is an implementation of TaskDao interface
 */
@Repository
public class TaskDaoImpl implements TaskDao{

    @PersistenceContext
    private EntityManager em;
    
    @Override
    public Task getTask(long taskId) {

        return em.find(Task.class, taskId);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Task> getTasks(String username) {
        
        List<Task> tasks = null;
        
        Query query = em.createQuery(
                "SELECT c FROM Task c WHERE c.user.id = "
                + "(SELECT e.id FROM User e WHERE e.name = :name)"
                );
        
        query.setParameter("name", username);
            
        tasks = (List<Task>)query.getResultList();
    
        return tasks;
    }

    @Override
    public void saveOrUpdateTask(Task task) {

        em.merge(task);
    }

    /**
     * In this realization it doesn't delete Task from
     * User's set of task
     */
    @Override
    public void deleteTask(long taskId) {
        
        Task toDelete = em.find(Task.class, taskId);
        
        if(toDelete != null){
            em.remove(toDelete);
        }
    }
}
