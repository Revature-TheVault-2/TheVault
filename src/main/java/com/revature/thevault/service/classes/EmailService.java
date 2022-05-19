package com.revature.thevault.service.classes;

import java.text.DecimalFormat;

import org.springframework.mail.javamail.JavaMailSender;

public class EmailService {
	
	private JavaMailSender emailSender;
	// Session
	
	
//	String token = UUID.randomUUID().toString();
	// My random generator for random passwords maybe
	
	public static void main(String[] args) {
		overdraftEmail();
		transactionAmountEmail(-501.00f);
		
	}

	public EmailService(JavaMailSender emailSender) {
		super();
		this.emailSender = emailSender;
		// Add whatever for session/random stuff
	}
	
	
	
	
	public static void overdraftEmail() {
		
		String name = "John"; // Get from session
		String userEmail = "session value of email"; // Get from session
		float balance = -2000.00f; // Will pull balance from session
		 DecimalFormat df = new DecimalFormat();
		 df.setMinimumFractionDigits(2);
		 String amount = df.format(balance);
		
		String subject = "Account Overdrawn";
		String body = "Hello " + name +",\n\n"
				+ "Your account have been overdrawn, your current balance is: " + amount +".\n\n"
				+ "If you did not submit this transaction, please contact your local bank at your earliest convenience.\n\n"
				+ "If not corrected within two business days, your account will be charged an overdraft fee of $35.00.";
		
		sendEmail(userEmail, subject, body);
	}
	

	public static void transactionAmountEmail(float transactionAmount) {
		String transactionType;
		if(transactionAmount>0) {
			 transactionType = "Deposit";
		}else {
			 transactionType = "Withdrawl";
		}
		 String name = "John"; // Will get name from the session
		 
		 float notificationAmount = 500.00f; // Will get the amount from the session
		 DecimalFormat df = new DecimalFormat();
		 df.setMinimumFractionDigits(2);
		 String amount = df.format(notificationAmount);

		 
		 String userEmail = "session value of email"; // Will get from session
		
		String subject = "Your Recent " + transactionType + " Notification";
		String body = "Hello " + name +",\n\n"
				+ "Your recent " + transactionType.toLowerCase() +" has exceeded your notification amount of " + amount +".\n\n"
				+ "If you did not submit this "+ transactionType.toLowerCase() + ", please contact your local bank at your earliest convenience.\n\n"
				+ "If you would like to change your notification settings, you can do so from your profile on the website.";
		
		sendEmail(userEmail, subject, body);
		
	}
	
	
	
	public static void sendEmail(String toEmail, String subject, String body) {
		
		String signOff = "\n\n\n\tThe Vault Team" + "\n\t(800) 555-0000";
		body = body + signOff;

//		SimpleMailMessage message = new SimpleMailMessage();

//		message.setFrom("socialmedianow63@gmail.com");
//		message.setTo(toEmail);
//		message.setText(body);
//		message.setSubject(subject);
//
//		emailSender.send(message);
		System.out.println(subject);
		System.out.println(body);

	}


}
