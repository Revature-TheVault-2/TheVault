package com.revature.thevault.repository.dao;
import com.revature.thevault.repository.entity.LoginCredentialEntity;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository("loginRepository")
public interface LoginRepository extends JpaRepository<LoginCredentialEntity, Integer> {

    LoginCredentialEntity findByUsername(String username);

   <S extends LoginCredentialEntity> S save(S entity);

   //left this just in case, not sure what the Optional was about
    
    LoginCredentialEntity findByPkUserId(int integer);


    @Query("select l from LoginCredentialEntity l where l.username= ?1 and l.password = ?2")
//    LoginCredentialEntity findByLoginCredential(String username, String password);

    LoginCredentialEntity findByUsernameAndPassword(String username, String password);



//    @Query("select l.userid from LoginCredentialEntity l where l.username = ?1")
//    int findIdByUsername(String memberUsername);
}
