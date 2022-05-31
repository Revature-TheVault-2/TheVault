package com.revature.thevault.repository.dao;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.revature.thevault.repository.entity.AccountProfileEntity;
import com.revature.thevault.repository.entity.LoginCredentialEntity;

@Repository("accountProfileRepository")
public interface AccountProfileRepository extends JpaRepository<AccountProfileEntity, Integer> {

    AccountProfileEntity findByLogincredential(LoginCredentialEntity loginCredentialEntity);

    AccountProfileEntity deleteByLogincredential(LoginCredentialEntity loginCredentialEntity);
    
    AccountProfileEntity findBypkProfileId(int id);
	
	

	

//    @Query(value =
//            "UPDATE account_profile_table apt " +
//            "WHERE apt.pk_profile_id = 1? " +
//            "SET VALUES(default, 2?, 3?, 4?, 5?, 6?, 7?)",
//            nativeQuery = true
//    )
//   (LoginCredentialEntity loginCredentialEntity);

}
