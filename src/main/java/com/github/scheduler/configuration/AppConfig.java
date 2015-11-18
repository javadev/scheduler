package com.github.scheduler.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;

@Configuration
public class AppConfig {

    //Allows getting properties through application.properties file
    @Bean
    public static PropertySourcesPlaceholderConfigurer
            propertyPlaceholderConfigurer() {

        PropertySourcesPlaceholderConfigurer ppc =
                new PropertySourcesPlaceholderConfigurer();

        ppc.setLocation(new ClassPathResource("application.properties"));

        return ppc;
    }
}
