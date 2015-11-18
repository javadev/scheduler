package com.github.scheduler.configuration;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.web.filter.CharacterEncodingFilter;

/*
 * This is a Spring Security configuration class
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    //for Security database access
    @Autowired
    DataSource dataSource;//for security authorization
    
    /*
     * Tells Spring Security to use table security.
     * "#sch$" it's just random char sequence, must match with
     * another one in password encrypting method
     */
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) 
            throws Exception{
        
        auth.jdbcAuthentication().dataSource(dataSource).
                usersByUsernameQuery("select username, password,"
                        + " true from user where username=?").
                authoritiesByUsernameQuery("select username, "
                        + "'ROLE_USER' from user where username=?").
                passwordEncoder(new StandardPasswordEncoder("#sch$"));
    }
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        //makes right utf-8 processing for not Latin characters
        CharacterEncodingFilter filter = new CharacterEncodingFilter();
        filter.setEncoding("UTF-8");
        filter.setForceEncoding(true);
        http.addFilterBefore(filter,CsrfFilter.class);
        
        http.authorizeRequests()
            .antMatchers("/app/**").access("hasRole('USER')").and()
            .formLogin().defaultSuccessUrl("/app/days", false).and()
            //link on logout
            .logout().logoutUrl("/logout")
            .logoutSuccessUrl("/login").and()
            .formLogin().loginPage("/login").and()
            ;
    }
}
