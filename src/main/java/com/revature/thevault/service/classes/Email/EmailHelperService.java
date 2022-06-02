package com.revature.thevault.service.classes.Email;

import java.io.File;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;


@Service
public class EmailHelperService {

	private JavaMailSender emailSender;
	// Session

//	String token = UUID.randomUUID().toString();
	// My random generator for random passwords maybe

//	public static void main(String[] args) {
//		overdraftEmail();
//		transactionAmountEmail(-501.00f);
//		
//	}

	@Autowired
	public EmailHelperService(JavaMailSender emailSender) {
		super();
		this.emailSender = emailSender;
		// Add whatever for session/random stuff
	}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//Helper Methods	
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * This helper method sends AN EMAIL WITHOUT AN ATTACHMENT. Gets all values from
	 * template methods above.
	 * 
	 * @param toEmail
	 * @param subject
	 * @param body
	 * @author Brody and Gibbons
	 */
	public boolean sendEmail(String toEmail, String subject, String body) {

		String signOff = "\n\n\n\tThe Vault Team" + "\n\t(800) 555-0000";
		body = body + signOff;

		SimpleMailMessage message = new SimpleMailMessage();

		message.setFrom("socialmedianow63@gmail.com");
		message.setTo(toEmail);
		message.setText(body);
		message.setSubject(subject);
		try {
			emailSender.send(message);
			return true;

		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * This helper methods send an EMAIL WITH AN ATTACHMENT. Gets all values from
	 * the template method above. Not sure if this works with multiple attachments,
	 * We can test it later.
	 * 
	 * @param toEmail
	 * @param subject
	 * @param body
	 * @param pathToFileAttachment
	 * @throws MessagingException
	 * @throws If                 pathToFileAttachment is null, will throw a null
	 *                            pointer exception
	 * @author Brody and Gibbons
	 */
	public boolean sendEmailWithAttachment(String toEmail, String subject, String body, String pathToFileAttachment)
			throws MessagingException {

		String signOff = "\n\n\n\tThe Vault Team" + "\n\t(800) 555-0000";
		body = body + signOff;

		MimeMessage message = emailSender.createMimeMessage();

		MimeMessageHelper helper = new MimeMessageHelper(message, true);

		helper.setFrom("Someemail@email.com");
		helper.setTo(toEmail);
		helper.setText(body);
		helper.setSubject(subject);

		FileSystemResource file = new FileSystemResource(new File(pathToFileAttachment));
		helper.addAttachment("Report.pdf", file); // Calling it a report right now.

		try {
			emailSender.send(message);
			return true;

		} catch (Exception e) {
			return false;
		}
	}
}
