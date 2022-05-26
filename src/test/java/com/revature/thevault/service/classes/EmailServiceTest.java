package com.revature.thevault.service.classes;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;

public class EmailServiceTest {
	
	private EmailService emailService;

	@Autowired
	public EmailServiceTest ( EmailService emailService) {
		this.emailService = emailService;
	}
	
	@BeforeEach
    void setUp(){
		
	}
	
}
