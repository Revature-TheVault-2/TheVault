package com.revature.thevault.service.classes;

import java.io.File;
import java.text.DecimalFormat;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

/**
 * Stretch goals ToDo: See about sending a logo, test multiple attachments.
 * 
 * 
 * @author Brody and Gibbons
 *
 */
@Service
public class EmailService {
	@Autowired
	private JavaMailSender emailSender;
	// Session
	
	
//	String token = UUID.randomUUID().toString();
	// My random generator for random passwords maybe
	
//	public static void main(String[] args) {
//		overdraftEmail();
//		transactionAmountEmail(-501.00f);
//		
//	}
//	public EmailService() {}

	public EmailService(JavaMailSender emailSender) {
		super();
		this.emailSender = emailSender;
		// Add whatever for session/random stuff
	}
	
	
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//		Template Methods	
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Template method that notifies the receiver of an overdrawn account. Pulls name, email, and current balance from the session when called.
	 * Only called if the withdrawal will leave the balance in the negatives.
	 * 
	 * @param float balancePostWithdrawal the balance in the negative after a withdrawal is finished.
	 * @author Brody and Gibbons
	 */
	public void overdraftEmail(float balancePostWithdrawal) {
		
		String name = "John"; // Get from session
		String userEmail = "session value of email"; // Get from session
		
		 DecimalFormat df = new DecimalFormat();
		 df.setMinimumFractionDigits(2);
		 String amount = df.format(balancePostWithdrawal);
		
		String subject = "Account Overdrawn";
		String body = "Hello " + name +",\n\n"
				+ "Your account have been overdrawn, your current balance is: " + amount +".\n\n"
				+ "If you did not submit this transaction, please contact your local bank at your earliest convenience.\n\n"
				+ "If not corrected within two business days, your account will be charged an overdraft fee of $35.00.";
		
		sendEmail(userEmail, subject, body);
	}
	
	/**
	 * Template method that sends an email based on user preferences for transactions deposits greater than transaction amount or
	 * withdrawals greater than transaction amount.
	 * 
	 * @param transactionAmount
	 * @author Brody and Gibbons
	 */
	public void transactionAmountEmail(float transactionAmount) {
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
	/**
	 * Template method to send emails with an attached pdf of the user's banking reports.
	 * 
	 * @param A range of dates, not implemented yet.
	 * @param pathToAttachment
	 * @author Brody and Gibbons
	 */
	public static void sendReportPdfEmail(String pathToAttachment /*, Date rangeOfDates */) {
		
		String name = "John"; // pull name from the session
		String dates = "dates range of report";
		
		String userEmail = "session email";
		String subject = "Copy of Banking Reports from " + dates;
		String body = "Hello " + name +",\n\n"
				+ "Attached are your reports that you requested from " + dates + "." 
				+ "If you did not request this report, please contact your local bank at your earliest convenience.";
		
	}
	
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//		Helper Methods	
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * This helper method sends AN EMAIL WITHOUT AN ATTACHMENT. Gets all values from template methods above.
	 * 
	 * @param toEmail
	 * @param subject
	 * @param body
	 * @author Brody and Gibbons
	 */
	public void sendEmail(String toEmail, String subject, String body) {
		
		String signOff = "\n\n\n\tThe Vault Team" + "\n\t(800) 555-0000";
		body = body + signOff;

		SimpleMailMessage message = new SimpleMailMessage();

		message.setFrom("thevaultbankteam@gmail.com");
		message.setTo(toEmail);
		message.setText(body);
		message.setSubject(subject);

		emailSender.send(message);
		System.out.println(subject);
		System.out.println(body);

	}
	
	/**
	 * This helper methods send an EMAIL WITH AN ATTACHMENT. Gets all values from the template method above.
	 * Not sure if this works with multiple attachments, We can test it later.
	 * 
	 * @param toEmail
	 * @param subject
	 * @param body
	 * @param pathToFileAttachment
	 * @throws MessagingException
	 * @throws If pathToFileAttachment is null, will throw a null pointer exception
	 * @author Brody and Gibbons
	 */
	public void sendEmailWithAttachment(String toEmail, String subject, String body, String pathToFileAttachment) throws MessagingException {
		
		String signOff = "\n\n\n\tThe Vault Team" + "\n\t(800) 555-0000";
		body = body + signOff;
		
		MimeMessage message = emailSender.createMimeMessage();
		
		MimeMessageHelper helper = new MimeMessageHelper(message, true);
		
		helper.setFrom("Someemail@email.com");
		helper.setTo(toEmail);
		helper.setText(body);
		helper.setSubject(subject);
		
		FileSystemResource file = new FileSystemResource(new File(pathToFileAttachment));
			helper.addAttachment("Report", file); // Calling it a report right now.
//			
		emailSender.send(message);	
	}
	
	
	/**
	 * generates a password-reset email containing a token and a link
	 * 
	 * @param token
	 * @param toEmail
	 *
	 */
	public void sendPasswordResetLink(String token, String toEmail) {
//		System.out.println("Inside the sendPasswordResetLink method");
		String subject = "The Vault: Password Reset Requested";
		String resetLink = "http://ec2-44-201-212-50.compute-1.amazonaws.com:9000/newpassword";
		String body = "Valued Customer:  A password reset has been requested for your account at The Vault. \n If this was from you, please copy the token below and follow this link to reset your password: " + resetLink + "\n"+ token;
//		System.out.println(toEmail);
		
		sendEmail(toEmail,subject, body);
	}


}
