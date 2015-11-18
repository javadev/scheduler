package com.github.scheduler.controllers;

import static
org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static
org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static
org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

import org.junit.Test;
import org.springframework.test.web.servlet.MockMvc;

import com.github.scheduler.controller.LoginPageController;

/*
 * Login page Controller test
 */
public class LoginPageControllerTest {
    
    @Test
    public void login() throws Exception {
        
        LoginPageController controller = new LoginPageController();
        
        MockMvc mockMvc = standaloneSetup(controller).build();
        
        mockMvc.perform(get("/"))
                .andExpect(view().name("login_page"));
        
        mockMvc.perform(get("/login"))
        .andExpect(view().name("login_page"));
    }
}
