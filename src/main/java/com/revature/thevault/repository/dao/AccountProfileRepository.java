package com.revature.thevault.repository.dao;

import com.revature.thevault.repository.entity.AccountEntity;
import com.revature.thevault.repository.entity.AccountProfileEntity;
import com.revature.thevault.repository.entity.LoginCredentialEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import javax.persistence.NamedNativeQuery;

@Repository("accountProfileRepository")
public interface AccountProfileRepository extends JpaRepository<AccountProfileEntity, Integer> {

    AccountProfileEntity findByLogincredential(LoginCredentialEntity loginCredentialEntity);

    AccountProfileEntity deleteByLogincredential(LoginCredentialEntity loginCredentialEntity);
    
//    AccountProfileEntity findByPkProfileId(int id);
    

	AccountProfileEntity findByLogincredential(Optional<AccountEntity> findById);
	

	

//    @Query(value =
//            "UPDATE account_profile_table apt " +
//            "WHERE apt.pk_profile_id = 1? " +
//            "SET VALUES(default, 2?, 3?, 4?, 5?, 6?, 7?)",
//            nativeQuery = true
//    )
//   (LoginCredentialEntity loginCredentialEntity);

}
