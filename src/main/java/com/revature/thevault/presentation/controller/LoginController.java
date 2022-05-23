package com.revature.thevault.presentation.controller;

import com.revature.thevault.presentation.model.request.LoginRequest;
import com.revature.thevault.presentation.model.request.NewLoginCredentialsRequest;
import com.revature.thevault.presentation.model.request.ResetPasswordRequest;
import com.revature.thevault.presentation.model.response.LoginResponse;
import com.revature.thevault.presentation.model.response.builder.PostResponse;

import com.revature.thevault.presentation.model.response.builder.PutResponse;
import com.revature.thevault.repository.entity.LoginCredentialEntity;
import com.revature.thevault.service.classes.AccountProfileService;
import com.revature.thevault.service.classes.LoginService;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController("loginController")
//@RequestMapping("/login")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @Autowired
    private AccountProfileService accountProfileService;
    
    /**
     * Controller that checks the login 
     * @param loginRequest
     * @return checks the login response and returns either true or false based of what the user inputs.
     */

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping("/check")
    public LoginResponse checkLogin(@RequestBody LoginRequest loginRequest){
        return loginService.checkLogin(loginRequest);
    }
    /**
     * Controller that creates a new login 
     * @param newLoginRequest
     * @return true when user inputs Username and Password
     * @throws Invalid input exception
     */

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/create")
    public PostResponse newLogin(@RequestBody NewLoginCredentialsRequest newLoginRequest){
        return loginService.createNewLogin(newLoginRequest);
    }
    /**
     * Controller that checks the user login by finding credentials 
     * @param loginRequest
     * @return true if User inputs correct login
     * @throws Http bad request message
     */

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/login")
    public PostResponse findLoginCredential(@RequestBody LoginRequest loginRequest){
        return loginService.getLoginCredentialFromLogin(loginRequest.getUsername());
    }
/**
 * Controller that resets the password 
 * @param resetPasswordRequest
 * @return true 
 * @throws nullpointerexception
 */
    @PutMapping ("/reset-password")
    public PutResponse resetPassword(@RequestBody ResetPasswordRequest resetPasswordRequest){
        return loginService.resetPassword(resetPasswordRequest);
    }
}
