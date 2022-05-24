package com.revature.thevault;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource({"classpath*:applicationContext.xml"})
public class ThevaultApplication {

	public static void main(String[] args) {
		SpringApplication.run(ThevaultApplication.class, args);
	}

}
