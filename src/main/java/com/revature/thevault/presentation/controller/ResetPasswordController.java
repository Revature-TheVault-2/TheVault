package com.revature.thevault.presentation.controller;

import java.util.Collections;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.revature.thevault.repository.dao.AccountProfileRepository;
import com.revature.thevault.repository.dao.LoginRepository;
import com.revature.thevault.repository.dao.ResetPasswordRepository;
import com.revature.thevault.repository.entity.LoginCredentialEntity;
import com.revature.thevault.repository.entity.PasswordReset;
import com.revature.thevault.repository.entity.PasswordResetRequest;
import com.revature.thevault.service.classes.EmailService;
import com.revature.thevault.service.classes.PwResetTokenService;

@CrossOrigin("*")
@RestController
public class ResetPasswordController {
	
	@Autowired
	ResetPasswordRepository resetPasswordRepository;
	
//	@Autowired
//	PasswordReset passwordReset;
	
	@Autowired
	LoginRepository loginRepository;
	
	@Autowired
	PwResetTokenService pwResetTokenService;
	
	@Autowired
	EmailService emailService;
	
	@Autowired
	AccountProfileRepository accountProfileRepository;
	
	@Autowired	
	public ResetPasswordController() {}
	
	/**
	 * creates a record in the password reset table containing a token and the user id
	 * @param loginCredentialEntity
	 * @return boolean success
	 */
	@PostMapping("/resetpassword")
	public boolean requestPasswordResetLink(@RequestBody LoginCredentialEntity loginCredentialEntity) {
		boolean success = true;
		
		LoginCredentialEntity findLogin = loginRepository.findByUsername(loginCredentialEntity.getUsername());
		
		try {
			int fkUserId = findLogin.getPkuserid();
			String token = pwResetTokenService.generateResetToken(fkUserId);
			System.out.println("THE TOKEN HAS BEEN CREATED");
			//creates a password reset record in the db with token and userID
			PasswordReset passwordReset = new PasswordReset(token,fkUserId);
			resetPasswordRepository.save(passwordReset);
			String email = accountProfileRepository.findByLogincredential(findLogin).getEmail();
			//uses email service to send an email
			emailService.sendPasswordResetLink(token, email);
			
		} catch(NullPointerException nullException) {
			System.out.println("Invalid email reset requested.");
			System.out.println(nullException);
			success= false;
			return success;
			}
		
		return success;
	}
	
	/**
	 * 
	 * @param token
	 * @return
	 */
	@GetMapping("/newpassword")
	public String submitPasswordReset(@RequestParam(name="token") String token) {
		System.out.println("user fwd to password reset page");
		return "reset password page goes here";
	}

	/**
	 * updates password for user after matching token to userid
	 * @param pwResetModel
	 * @return boolean success
	 */
	@PostMapping("/newpassword")
	public boolean submitPasswordReset(@RequestBody PasswordResetRequest pwResetModel) {
		boolean success = false;
		try {
			PasswordReset passReset = resetPasswordRepository.findByToken(pwResetModel.getToken());
			int userId = passReset.getFkUserId();
			LoginCredentialEntity loginCredentialEntity = loginRepository.findByPkuserid(userId);
			loginCredentialEntity.setPassword(pwResetModel.getPassword());
			loginRepository.save(loginCredentialEntity);

			
		}catch(Exception e) {
			System.out.println(e);
			return success;
		}
		success = true;
		return success;
	}
}
