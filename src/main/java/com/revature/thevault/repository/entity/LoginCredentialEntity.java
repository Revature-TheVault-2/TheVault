package com.revature.thevault.repository.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

@Entity
@Table(name = "login_credential_table")
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LoginCredentialEntity {
	
    @Id
    @Column(name = "pk_user_id")
    @GeneratedValue(generator = "login_credential_table_pk_user_id_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(allocationSize = 1, name = "login_credential_table_pk_user_id_seq", sequenceName = "login_credential_table_pk_user_id_seq")
<<<<<<< HEAD
    Integer pkuserid;
=======
    int pkUserId;
>>>>>>> afc7b6d03e9190d9860abef2e96d3461907316e2
    

    @Column(name = "username")
    String username;
    
    @Column(name = "password")
    String password;
}
