package com.springvoyage.intro_springboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class IntroductionToSpringBootApplication implements CommandLineRunner {

	@Autowired //Spring Boot will inject this bean whenever it's required.
	Apple apple1;
	@Autowired
	Apple apple2;

	public static void main(String[] args) {
		SpringApplication.run(IntroductionToSpringBootApplication.class, args);

		// Creating an instance of Apple and calling the eatApple method
		/*Apple obj = new Apple();
		obj.eatApple();*/

	}
// This function is to run a non-static object (obj) outside the static main method
	@Override
	public void run(String... args) throws Exception {
		apple1.eatApple();
		apple2.eatApple();
		System.out.println("apple1 hashcode= " + apple1.hashCode());
		System.out.println("apple2 hashcode= " + apple2.hashCode());
	}
}
