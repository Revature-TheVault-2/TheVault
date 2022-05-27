package com.revature.thevault.repository.dao;

<<<<<<< HEAD
=======
import com.revature.thevault.repository.entity.LoginCredentialEntity;
>>>>>>> afc7b6d03e9190d9860abef2e96d3461907316e2
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.revature.thevault.repository.entity.LoginCredentialEntity;

@Repository("loginRepository")
public interface LoginRepository extends JpaRepository<LoginCredentialEntity, Integer> {

    LoginCredentialEntity findByUsername(String username);

   <S extends LoginCredentialEntity> S save(S entity);

<<<<<<< HEAD

   //left this just in case, not sure what the Optional was about
//    Optional<LoginCredentialEntity> findByPkuserid(int integer);
    
    LoginCredentialEntity findByPkuserid(int integer);
//    Optional<LoginCredentialEntity> findByPkuserid(int integer);

=======
    Optional<LoginCredentialEntity> findByPkUserId(int integer);
>>>>>>> afc7b6d03e9190d9860abef2e96d3461907316e2


    @Query("select l from LoginCredentialEntity l where l.username= ?1 and l.password = ?2")
//    LoginCredentialEntity findByLoginCredential(String username, String password);

    LoginCredentialEntity findByUsernameAndPassword(String username, String password);



//    @Query("select l.userid from LoginCredentialEntity l where l.username = ?1")
//    int findIdByUsername(String memberUsername);
}
