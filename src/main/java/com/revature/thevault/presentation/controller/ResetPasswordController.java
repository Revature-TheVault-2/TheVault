package com.revature.thevault.presentation.controller;

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
	
	
	@PostMapping("/resetrequest")
	public @ResponseBody String requestPasswordResetLink(@RequestBody LoginCredentialEntity loginCredentialEntity) {
		LoginCredentialEntity findLogin = loginRepository.findByUsername(loginCredentialEntity.getUsername());
		System.out.println(findLogin);
		try {
			int fkUserId = findLogin.getPkuserid();
			System.out.println(fkUserId + " is the fkUserId _____________)_)_)_)____)_)_)___)_)__)__)_)_)_)_)__)_");
			String token = pwResetTokenService.generateResetToken(fkUserId);
			System.out.println("THE TOKEN HAS BEEN CREATED");
			PasswordReset passwordReset = new PasswordReset(token,fkUserId);
			
			System.out.println(passwordReset + "______that was the passwordReset object");
			System.out.println("After creating new PasswordReset(token,id)");
			resetPasswordRepository.save(passwordReset);
			System.out.println("Before email string created=================================");
			String email = accountProfileRepository.findByLogincredential(findLogin).getEmail();
			System.out.println(email + "  this is the email it found");
			System.out.println("After email string created---------------");			
			emailService.sendPasswordResetLink(token, email);
		} catch(NullPointerException nullException) {
			System.out.println("Invalid email reset requested.");
			System.out.println(nullException);
			}
		finally{}
		
		return "Sent";
	}
	
	@GetMapping("/change")
	public String submitPasswordReset(@RequestParam(name="token") String token) {
		System.out.println("user fwd to password reset page");
		return "reset password page goes here";
	}

	
	@PostMapping("/change")
	public boolean submitPasswordReset(@RequestParam(name="token") String token,@RequestBody PasswordResetRequest pwResetModel) {
		try {
			System.out.println(pwResetModel);
			System.out.println(token + "  is the token from the param");
			PasswordReset passReset = resetPasswordRepository.findByToken(token);
			int userId = passReset.getFkUserId();
			LoginCredentialEntity loginCredentialEntity = loginRepository.findByPkuserid(userId);
			loginCredentialEntity.setPassword(pwResetModel.getPassword());
			loginRepository.save(loginCredentialEntity);
			resetPasswordRepository.deleteByToken(token);
			
		}catch(Exception e) {
			
		}
		
		return false;
	}
}
