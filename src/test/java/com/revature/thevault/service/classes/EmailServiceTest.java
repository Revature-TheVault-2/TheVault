package com.revature.thevault.service.classes;

import javax.mail.MessagingException;

import org.junit.Ignore;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.revature.thevault.repository.entity.AccountProfileEntity;
import com.revature.thevault.service.classes.Email.EmailHelperService;
import com.revature.thevault.service.classes.Email.EmailService;


/**
 * @author Brody and Gibbons
 */
@Ignore
@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
public class EmailServiceTest {

	private EmailService emailService;

	EmailHelperService mockHelper;
	AccountProfileEntity currentUserProfile;


	public EmailServiceTest() {
		this.emailService = new EmailService(mockHelper);
	}



	@BeforeAll
	void setup() {
		String emailto = "TheVaultBankTeam@gmail.com";
		currentUserProfile = new AccountProfileEntity();
		currentUserProfile.setPkProfileId(1);
		currentUserProfile.setLogincredential(null);
		currentUserProfile.setFirst_name("john");
		currentUserProfile.setLast_name("test");
		currentUserProfile.setEmail(emailto);
		currentUserProfile.setNotificationAmount(50.00f);
	}
	
	@BeforeEach
	void before() {
		mockHelper = Mockito.mock(EmailHelperService.class);
		emailService = new EmailService(mockHelper);
	}

	
	@Test
	void testOverdraftEmail() {
		Mockito.when(mockHelper.sendEmail(Mockito.anyString(), Mockito.anyString(), Mockito.anyString())).thenReturn(true);
		emailService.overdraftEmail(50.00f, currentUserProfile);
		
		Mockito.verify(mockHelper, Mockito.times(1)).sendEmail(Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
	}
	
	@Test
	void testTransactionAmountEmail() {
		Mockito.when(mockHelper.sendEmail(Mockito.anyString(), Mockito.anyString(), Mockito.anyString())).thenReturn(true);
		emailService.transactionAmountEmail(50.00f, currentUserProfile);
		
		Mockito.verify(mockHelper, Mockito.times(1)).sendEmail(Mockito.anyString(), Mockito.anyString(), Mockito.anyString());

	}
	
	@Test
	void testSendReportPdfEmail() throws MessagingException {
		Mockito.when(mockHelper.sendEmailWithAttachment(Mockito.anyString(),Mockito.anyString(), Mockito.anyString(), Mockito.anyString())).thenReturn(true);
		emailService.sendReportPdfEmail(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
		
		Mockito.verify(mockHelper, Mockito.times(1)).sendEmailWithAttachment(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
	}
	
	
	
}
