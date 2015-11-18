package com.github.scheduler.dto;

import javax.validation.constraints.Size;

/*
 * This is a object, which jsp page passes to
 * controller wit filled fields
 */
public class REGPageMessage {
    
    @Size(min=4, max=20, message="incorrect username")
    private String username;
    
    @Size(min=4, max=20, message="incorrect password")
    private String password;
    
    
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}
