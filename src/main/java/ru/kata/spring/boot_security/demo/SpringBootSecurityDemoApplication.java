package ru.kata.spring.boot_security.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.reactive.context.AnnotationConfigReactiveWebServerApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

@SpringBootApplication
public class SpringBootSecurityDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootSecurityDemoApplication.class, args);
	}

//public static void main(String[] args) {
//	AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MyConfig2.class);
//	Communication communication=context.getBean("communication", Communication.class);
//	List<User> users = communication.showAllUsers();
//	System.out.println(users);
//
//}

}
