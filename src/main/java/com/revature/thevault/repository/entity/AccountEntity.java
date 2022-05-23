package com.revature.thevault.repository.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "account_table")
public class AccountEntity {

	public AccountEntity(int accountId, LoginCredentialEntity loginCredentialEntity,
            AccountTypeEntity accountTypeEntity2, float f, float g) {
    }

    @Id
	@Column(name = "pk_account_id")
	@GeneratedValue(generator = "account_table_pk_account_id_seq", strategy = GenerationType.IDENTITY)
	//@SequenceGenerator(allocationSize = 1, name = "account_table_pk_account_id_seq", sequenceName = "account_table_pk_account_id_seq")
	int pk_account_id;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "fk_user_id")
	LoginCredentialEntity logincredentials;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "fk_account_type_id")
	AccountTypeEntity accountTypeEntity;

	@Column(name = "available_balance")
	float available_balance;
	
	@Column(name = "pending_balance")
	float pending_balance;

	
	
}
