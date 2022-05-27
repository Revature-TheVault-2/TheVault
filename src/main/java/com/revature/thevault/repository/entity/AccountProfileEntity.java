package com.revature.thevault.repository.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "account_profile_table")
public class AccountProfileEntity {
	
	
    public AccountProfileEntity(int i, LoginCredentialEntity findUserByUserId, Object firstName, Object lastName,
            Object email2, Object phoneNumber, Object address2) {
    }

    @Id
    @Column(name = "pk_profile_id")
    @GeneratedValue(generator = "account_profile_table_pk_profile_id_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(allocationSize = 1, name = "account_profile_table_pk_profile_id_seq", sequenceName = "account_profile_table_pk_profile_id_seq")
    int pk_profile_id;
    
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_user_id")
//    int userId;
    LoginCredentialEntity logincredential;
    
    @Column(name = "first_name")
    String first_name;
    
    @Column(name = "last_name")
    String last_name;
    
    @Column(name = "email")
    String email;
    
    @Column(name = "phone_number")
    String phone_number;
    
    @Column(name = "address")
    String address;
    
    @Column(name = "notification_amount", nullable=true)
    float notificationAmount;

    
    
    
    
}