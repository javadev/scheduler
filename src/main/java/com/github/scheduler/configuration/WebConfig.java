package com.github.scheduler.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

/*
 * This is a view resolver and recource handler configiration
 */
@Configuration//makes this class configurations
@EnableWebMvc//it used to enable Spring MVC
@ComponentScan("com.github.scheduler")
public class WebConfig extends WebMvcConfigurerAdapter{

    //add special recource handler. first-requested url,
    //second - real folder
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/css/**")
                .addResourceLocations("/WEB-INF/resources/css/");
        registry.addResourceHandler("/resources/images/**")
                .addResourceLocations("/WEB-INF/resources/images/");
    }

    @Bean
    public InternalResourceViewResolver setupViewResolver() {
        InternalResourceViewResolver resolver = 
                new InternalResourceViewResolver();
        resolver.setPrefix("/WEB-INF/pages/");
        resolver.setSuffix(".jsp");
        resolver.setViewClass(JstlView.class);

        return resolver;
    }
}
