package com.springvoyage.intro_springboot;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

//This will make Apple as a bean ensuring all objects of Apple class should be managed by the Spring framework.
//@Component
public class Apple {

    void eatApple(){
        System.out.println("I am eating an Apple");
    }

    @PostConstruct
    void callThisMethodBeforeAppleIsUsed(){
        System.out.println("Creating Apple before it is used");
    }

    @PreDestroy
    void callThisBeforeDestroy(){
        System.out.println("Destroying the Apple bean");
    }
}
/*
* The @PostConstruct will make the method callThis...() to be called whenever an
* object of Apple class is created.
* @PreDestroy method will run before the Apple bean is destroyed
* */

