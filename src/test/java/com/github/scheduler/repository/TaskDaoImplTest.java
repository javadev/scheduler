package com.github.scheduler.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;

import com.github.scheduler.model.domain.task.Task;
import com.github.scheduler.repository.TaskDao;
import com.github.scheduler.repository.UserDao;
import com.github.scheduler.configuration.JpaTestConfig;

/*
 * Task dao implementation test
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={JpaTestConfig.class})
@TestExecutionListeners({
    DependencyInjectionTestExecutionListener.class,
    DirtiesContextTestExecutionListener.class,
    TransactionalTestExecutionListener.class,
    DbUnitTestExecutionListener.class})
@Transactional
public class TaskDaoImplTest {

    @Autowired
    TaskDao taskDao;
    
    @Autowired
    UserDao userDao;
    
    @Test
    @DatabaseSetup("/com/javadev/scheduler/repository/serviceTest.xml")
    public void addTask(){
        
        Task task = new Task();
        
        task.setUser(userDao.getUserByUserName("test_user_1"));
        
        taskDao.saveOrUpdateTask(task);
        
        Assert.assertEquals(3, taskDao.getTasks("test_user_1").size());
        
        Assert.assertEquals(3, userDao.getUserByUserName("test_user_1").
                getTasks().size());
    }
    
    @Test
    @DatabaseSetup("/com/javadev/scheduler/repository/serviceTest.xml")
    public void updateTask(){
        
        Task task = taskDao.getTask(200);
        
        task.setTitle("new_test_title");
        
        taskDao.saveOrUpdateTask(task);
        
        task = taskDao.getTask(200);
        
        Assert.assertEquals("new_test_title", task.getTitle());
    }
    
    @Test
    @DatabaseSetup("/com/javadev/scheduler/repository/serviceTest.xml")
    public void getTask(){
        
        Task task = taskDao.getTask(200);
        
        Assert.assertNotNull(task);
    }
    
    @Test
    @DatabaseSetup("/com/javadev/scheduler/repository/serviceTest.xml")
    public void getTasks(){
        
        List<Task> taskList = taskDao.getTasks("test_user_1");
        Assert.assertTrue(taskList.size() == 2);
    }
    
    @Test
    @DatabaseSetup("/com/javadev/scheduler/repository/serviceTest.xml")
    public void deleteTest(){
        
        taskDao.deleteTask(200);
        
        Assert.assertEquals(1, taskDao.getTasks("test_user_1").size());
        Assert.assertEquals("testtask_2", taskDao.getTask(201).getTitle());
    }
}
