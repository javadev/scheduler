package com.github.scheduler.service;

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

import com.github.scheduler.model.domain.User;
import com.github.scheduler.model.domain.task.Task;
import com.github.scheduler.repository.UserDao;
import com.github.scheduler.service.Service;
import com.github.scheduler.service.Interfaces.TaskRepresentation;
import com.github.scheduler.service.auxiliaryobjects.TaskRepresentationImpl;
import com.github.scheduler.service.auxiliaryobjects.UserFactory;
import com.github.scheduler.configuration.JpaTestConfig;

/*
 * Service implementation test
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={JpaTestConfig.class})
@TestExecutionListeners({
    DependencyInjectionTestExecutionListener.class,
    DirtiesContextTestExecutionListener.class,
    TransactionalTestExecutionListener.class,
    DbUnitTestExecutionListener.class})
@Transactional
public class ServiceTest {
        
    @Autowired
    private UserDao userDao;
    
    @Autowired
    private Service service;
    
    @Test
    public void testGetSchedule(){
        
System.out.println(new java.io.File("."));
        //creates new user
        User user = UserFactory.createStandartNewUser(
                "test_user", "USER", "password");
        
        userDao.saveNewUser(user);
        
        Task task = null;
        
        //obtains necessary interval for Schedule
        for(Task t: user.getTasks()){
            task = t;
            break;
        }

        Assert.assertNotNull(service.getSchedule(
                task.getInterval(), "test_user"));
    }
    
    @Test
    public void testAddNewUser(){
        Assert.assertTrue(service.addNewUser("test_user", "password"));
        Assert.assertFalse(service.addNewUser("test_user", "password"));
        Assert.assertEquals(userDao.getUserByUserName("test_user").
                getName(), "test_user");
    }
    
    @Test
    @DatabaseSetup("/com/javadev/scheduler/repository/serviceTest.xml")
    public void testGetTask(){
        Assert.assertEquals(service.getTaskRepresentation(200).getTitle(), "testtask_1");
    }
    
    @Test
    @DatabaseSetup("/com/javadev/scheduler/repository/serviceTest.xml")
    public void testGetTasks(){
        List<TaskRepresentation> repr = 
                service.getTaskRepresentations("test_user_1");
            
        Assert.assertTrue(repr.get(0).getTitle().equals("testtask_1") ||
                repr.get(0).getTitle().equals("testtask_2"));
        
        Assert.assertTrue(repr.get(1).getTitle().equals("testtask_1") ||
                repr.get(1).getTitle().equals("testtask_2"));
    }
    
    @Test
    @DatabaseSetup("/com/javadev/scheduler/repository/serviceTest.xml")
    public void saveOrUpdateTask(){
        Task task = new Task();
        System.out.println();
        task.setUser(userDao.getUserByUserName("test_user_1"));
        task.setTitle("added_task");
        
        //tests add new task
        
        service.saveOrUpdateTask(new TaskRepresentationImpl(task));
        
        boolean exists = false;
        
        for(Task t: userDao.getUserByUserName("test_user_1").getTasks()){
            System.out.println("task_title: " + t.getTitle());
            if(t.getTitle().equals("added_task")){
                exists = true;
                break;
            }
        }
        
        Assert.assertTrue(exists);
    }
    
    @Test
    @DatabaseSetup("/com/javadev/scheduler/repository/serviceTest.xml")
    public void deleteTask(){
        
        List<TaskRepresentation> tasks = service.getTaskRepresentations("test_user_1");
        Assert.assertEquals(2, tasks.size());
        
        service.deleteTask(200);
        
        tasks = service.getTaskRepresentations("test_user_1");
        Assert.assertEquals(1, tasks.size());
    }
}
