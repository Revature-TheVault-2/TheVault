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
<<<<<<< HEAD
    public int getUserId() {
        return 0;
    }
    public Object getFirstName() {
        return null;
    }
    public Object getLastName() {
        return null;
    }
    public Object getEmail() {
        return null;
    }
    public Object getPhoneNumber() {
        return null;
    }
    public Object getAddress() {
        return null;
    }
=======
    float notificationAmount; // only added because tests were yelling at us
>>>>>>> 38f11cde1cbe751113059a7ef0020f8835f59afb
}
