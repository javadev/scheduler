package com.github.scheduler.configuration;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/*
 * This is an application context and servlet context configuration class
 */
public class AppInit extends AbstractAnnotationConfigDispatcherServletInitializer {

    //Application context configuration
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class<?>[]{
                DataBaseConfig.class,
                SecurityConfig.class,
                AppConfig.class
        };
    }

    //Servlet context configuration
    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class<?>[]{
                WebConfig.class
        };
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }
}
