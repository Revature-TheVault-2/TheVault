package com.revature.thevault.repository.entity;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "password_reset")
public class PasswordReset {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="token_id")
	private int id;
	
	@Column(name="token_value", unique = true, nullable = false)
	private String token;
	
	
//	@OneToOne(targetEntity = LoginCredentialEntity.class, fetch = FetchType.EAGER)
//	@JoinColumn(nullable = false, name = "fk_user_id")
//	private LoginCredentialEntity loginCredentialEntity;
	private Integer fkUserId;
	
	private Date expirationDate;
	
	public PasswordReset() {}
	
	public PasswordReset(String token, int fkUserId) {
		super();
		this.token = token;
		this.fkUserId = fkUserId;
	}

}
