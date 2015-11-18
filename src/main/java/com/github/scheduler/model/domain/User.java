package com.github.scheduler.model.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.github.scheduler.model.domain.task.Task;

/**
 * It represents users with their scheduled days and
 * tasks. Keeps in database.
 * 
 * In this implementation all the CRUD operations on
 * Tasks must be performed through TaskDao.
 */
@Entity
@Table(name = "user")
public class User {
        
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="user_id")
    private long id;

    // while username is unique in application
    @Column(name="username", unique=true)
    private String name;//username
    
    @Column(name="password")
    private String password;
    
    @Column(name="ROLE_USER")
    //@Column(name="ROLE_USER", columnDefinition="ENUM('USER', 'ADMIN')")
    private String role;
    
    /*
     * Tasks which user scheduled to do
     */
    @OneToMany(
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            mappedBy="user") 
    private Set<Task> tasks = new HashSet<Task>();

    
    //getters and setters
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Task> getTasks() {
        return new HashSet<Task>(tasks);
    }
    
    //it's necessary for JPA querying
    //TODO think maybe throw exception if task owner != null 
    public void addTask(Task task){
        if(task.getUser() != this){
            task.setUser(this);
        }
        
        tasks.add(task);
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public long getId() {
        return id;
    }
}
