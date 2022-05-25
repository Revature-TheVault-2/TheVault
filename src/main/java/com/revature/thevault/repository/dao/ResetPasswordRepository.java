package com.revature.thevault.repository.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.revature.thevault.repository.entity.PasswordReset;


public interface ResetPasswordRepository extends JpaRepository<PasswordReset, Integer> {
	
	PasswordReset findByToken(String token);
	void deleteByToken(String token);


}
