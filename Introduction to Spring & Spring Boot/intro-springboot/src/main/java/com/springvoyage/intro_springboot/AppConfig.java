package com.springvoyage.intro_springboot;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class AppConfig {

    @Bean
    @Scope("prototype")
    Apple getApple(){
        return new Apple();
    }
}

/*
Here we have configured a class to configure bean of Apple class.
In this method, the creation of the Bean is taken care by the coder but the
dependency injection part will be taken care by the Spring.
getApple() method won't be called from anywhere. This method is only for spring framework.
*/
