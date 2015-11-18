package com.github.scheduler.repository;

import com.github.scheduler.model.domain.User;

/**
 * This is the User DAO interface.
 */
public interface UserDao{
    
    /**
     * @return User object retrieved from db by user name
     */
    public User getUserByUserName(String userName);
    
    //public void deleteUser(User user);
    
    /**
     * @param User to save in database.
     */
    public void saveNewUser(User user);
}
