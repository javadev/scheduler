package com.github.scheduler.configuration;

import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/*
 * This is a Hibernate configuration class
 */
@Configuration
@EnableTransactionManagement
@PropertySource("classpath:application.properties")
class DataBaseConfig{
    
    @Value("${datasource.driverClassName}")
    private String driverClassName;
    
    @Value("${datasource.url}")
    private String url;
    
    @Value("${datasource.username}")
    private String user;
    
    @Value("${datasource.password}")
    private String password;
    
    @Value("${hibernate.dialect}")
    private String dialect;
    
    @Value("${hbm2dll_auto}")
    private String hbm2dll_auto;
    
    @Bean
    public DataSource dataSource() {
        //DriverManagerDataSource ds = new DriverManagerDataSource();
        
        //dbcp connection pool
        BasicDataSource ds = new BasicDataSource();

        ds.setDriverClassName(driverClassName);
        ds.setUrl(url);
        ds.setUsername(user);
        ds.setPassword(password);

        return ds;
    }
    
    @Bean
    public LocalContainerEntityManagerFactoryBean configureEntityManagerFactory(
            DataSource dataSource) {
        
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = 
                new LocalContainerEntityManagerFactoryBean();
        
        entityManagerFactoryBean.setDataSource(dataSource);
        entityManagerFactoryBean.setPackagesToScan(
                "com.github.scheduler.model.domain");
        
        entityManagerFactoryBean.setJpaVendorAdapter(
                new HibernateJpaVendorAdapter());
       
        entityManagerFactoryBean.setJpaProperties(jpaProperties());

        return entityManagerFactoryBean;
    }
    
    private Properties jpaProperties() {
        Properties jpaProperties = new Properties();
        
        jpaProperties.put(org.hibernate.cfg.Environment.DIALECT, dialect);
        jpaProperties.put(org.hibernate.cfg.Environment.HBM2DDL_AUTO, hbm2dll_auto);
        
        return jpaProperties;
    }
    
    @Bean
    public PlatformTransactionManager annotationDrivenTransactionManager() {
        return new JpaTransactionManager();
    }
}