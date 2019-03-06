package org.dallaybatta.axondemo.spring.config;

import org.h2.server.web.WebServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// https://springframework.guru/using-the-h2-database-console-in-spring-boot-with-spring-security/
// The package name is changed for the ServletRegistrationBean in 2.1.2.BUILD-SNAPSHOT version. 
// Also the ServletRegistrationBean had type in the Constructor too, those changes are made.

@Configuration
public class H2DatabaseConfig {
	
    @Bean
    ServletRegistrationBean<WebServlet> h2servletRegistration(){
        ServletRegistrationBean<WebServlet> registrationBean = new ServletRegistrationBean<WebServlet>( new WebServlet());
        registrationBean.addUrlMappings("/h2console/*");
        return registrationBean;
    } 
       
}