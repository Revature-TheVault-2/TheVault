package com.revature.thevault.service.classes.Email;

import java.io.File;
import java.text.DecimalFormat;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.thevault.repository.entity.AccountProfileEntity;

/**
 * Stretch goals ToDo: See about sending a logo, test multiple attachments.
 * 
 * 
 * @author Brody and Gibbons
 *
 */
@Service
public class EmailService {
	private EmailHelperService emailHelper;
	
	@Autowired
	public EmailService(EmailHelperService emailHelper) {
		super();
		this.emailHelper = emailHelper;
		// Add whatever for session/random stuff
	}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//		Template Methods	
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Template method that notifies the receiver of an overdrawn account. Pulls
	 * name, email, and current balance from the session when called. Only called if
	 * the withdrawal will leave the balance in the negatives.
	 * 
	 * @param float balancePostWithdrawal the balance in the negative after a
	 *              withdrawal is finished.
	 * @author Brody and Gibbons
	 */
	public void overdraftEmail(float balancePostWithdrawal, AccountProfileEntity currentUserProfile) {

		String name = currentUserProfile.getFirst_name();
		String userEmail = currentUserProfile.getEmail();

		DecimalFormat df = new DecimalFormat();
		df.setMinimumFractionDigits(2);
		String amount = df.format(balancePostWithdrawal);

		String subject = "Account Overdrawn";
		String body = "Hello " + name + ",\n\n" + "Your account have been overdrawn, your current balance is: " + amount
				+ ".\n\n"
				+ "If you did not submit this transaction, please contact your local bank at your earliest convenience.\n\n"
				+ "If not corrected within two business days, your account will be charged an overdraft fee of $35.00.";
		emailHelper.sendEmail(userEmail, subject, body);
	}

	/**
	 * Template method that sends an email based on user preferences for
	 * transactions deposits greater than transaction amount or withdrawals greater
	 * than transaction amount.
	 * 
	 * @param transactionAmount
	 * @author Brody and Gibbons
	 */
	public void transactionAmountEmail(float transactionAmount, AccountProfileEntity currentUserProfile) {
		String transactionType;
		if (transactionAmount > 0) {
			transactionType = "Deposit";
		} else {
			transactionType = "Withdrawl";
		}
		String name = currentUserProfile.getFirst_name();

		float notificationAmount = currentUserProfile.getNotificationAmount();
		DecimalFormat df = new DecimalFormat();
		df.setMinimumFractionDigits(2);
		String amount = df.format(notificationAmount);

		String userEmail = currentUserProfile.getEmail();

		String subject = "Your Recent " + transactionType + " Notification";
		String body = "Hello " + name + ",\n\n" + "Your recent " + transactionType.toLowerCase()
				+ " has exceeded your notification amount of " + amount + ".\n\n" + "If you did not submit this "
				+ transactionType.toLowerCase() + ", please contact your local bank at your earliest convenience.\n\n"
				+ "If you would like to change your notification settings, you can do so from your profile on the website.";

		emailHelper.sendEmail(userEmail, subject, body);

	}

	/**
	 * Template method to send emails with an attached pdf of the user's banking
	 * reports.
	 * 
	 * @param A                range of dates, not implemented yet.
	 * @param pathToAttachment
	 * @author Brody and Gibbons
	 * @throws MessagingException
	 */
	public void sendReportPdfEmail(String pathToAttachment, String dateRange, String userEmail, String name) {
		String subject = "Copy of Banking Reports from " + dateRange;
		String body = "Hello " + name +",\n\n"
				+ "Attached are your reports that you requested from " + dateRange + "." 
				+ "If you did not request this report, please contact your local bank at your earliest convenience.";
		try {
			emailHelper.sendEmailWithAttachment(userEmail, subject, body, pathToAttachment);
		} catch (Exception e) {
			// catch exception and delete temp file
			File file = new File(pathToAttachment);
			file.delete();
			e.printStackTrace();
		}
		// Delete the file after sending it
		File file = new File(pathToAttachment);
		file.delete();
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
		
		emailHelper.sendEmail(toEmail,subject, body);
	}

}
