package com.github.scheduler.repository;

import javax.transaction.Transactional;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.github.scheduler.model.domain.User;
import com.github.scheduler.repository.UserDao;
import com.github.scheduler.configuration.JpaTestConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={JpaTestConfig.class})
@Transactional
public class UserDaoImplTest{
    
    @Autowired
    private UserDao dao;
    
    @Test
    public void saveAndGetUser(){
        
        //saves
        User user = new User();
        user.setName("testUser");
        dao.saveNewUser(user);
        
        //wrong get
        user = dao.getUserByUserName("");
        Assert.assertNull(user);
        
        //right get
        user = dao.getUserByUserName("testUser");
        Assert.assertEquals("testUser", user.getName());
    }
    
    @Test
    public void updateUser(){
        
        //saves
        User user = new User();
        user.setName("testUser");
        dao.saveNewUser(user);
        
        //update
        user = dao.getUserByUserName("testUser");
        user.setName("new_name");
        user = dao.getUserByUserName("new_name");
        Assert.assertEquals("new_name", user.getName());
    }
}
