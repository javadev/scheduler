package com.github.scheduler.repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.github.scheduler.model.domain.User;

/**
 * This is an implementation of UserDao interface
 */
@Repository
public class UserDaoImpl implements UserDao{
    
    @PersistenceContext
    private EntityManager em;
    
    /**
     * @return User object by username
     */
    @Override
    public User getUserByUserName(String username) {
        
        try{
            
            Query query = em.createQuery(
                    "SELECT e FROM User e WHERE e.name = :name");
            
            query.setParameter("name", username);
            
            User user = (User)query.getSingleResult();
            
            return user;
            
        }catch(NoResultException e){//if user is absent
            return null;
        }
    }

    /**
     * Saves new user to database
     * @return User saved user.
     */
    @Override
    public void saveNewUser(User user){
        
        em.merge(user);
    }
}
