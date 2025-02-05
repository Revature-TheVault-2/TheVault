package com.revature.thevault.presentation.model.request;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProfileCreateRequest {
	//this is from angular
    int userId; 
    
    //this is from input fields
    String firstName;
    String lastName;
    String email;
    String phoneNumber;
    String address;
    float notificationAmount; // only added because tests were yelling at us
}
