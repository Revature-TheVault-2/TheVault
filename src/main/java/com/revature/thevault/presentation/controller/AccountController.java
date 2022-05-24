package com.revature.thevault.presentation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import com.revature.thevault.presentation.model.request.CreateAccountRequest;
import com.revature.thevault.presentation.model.request.TransferRequest;
import com.revature.thevault.presentation.model.request.UpdateAccountRequest;
import com.revature.thevault.presentation.model.response.builder.DeleteResponse;
import com.revature.thevault.presentation.model.response.builder.GetResponse;
import com.revature.thevault.presentation.model.response.builder.PostResponse;
import com.revature.thevault.presentation.model.response.builder.PutResponse;
import com.revature.thevault.repository.entity.LoginCredentialEntity;
import com.revature.thevault.service.classes.AccountService;
import com.revature.thevault.service.exceptions.InvalidAuthorizationError;
import com.revature.thevault.utility.JWTInfo;
import com.revature.thevault.utility.JWTUtility;
<<<<<<< HEAD
=======

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
>>>>>>> 384355c41b043888f4bc7d8b78858e89b0d359ce

@RestController("accountController")
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private AccountService accountService;
    
    
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(path = "/find", produces = MediaType.APPLICATION_JSON_VALUE)
    public GetResponse getAccount(@RequestHeader("Authorization") String token, @RequestParam int accountId){
        return accountService.getAccount(accountId);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(path = "/users-accounts", produces = MediaType.APPLICATION_JSON_VALUE)
    public GetResponse getUserAccountList(@RequestParam int userId, HttpSession sess){
    	return accountService.getAccounts(userId);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path = "/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public PostResponse createAccount(@RequestHeader("Authorization") String token, @RequestBody CreateAccountRequest createAccountRequest){
    	return accountService.createAccount(createAccountRequest);
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping(path = "/delete", produces = MediaType.APPLICATION_JSON_VALUE)
    public DeleteResponse deleteAccount(@RequestHeader("Authorization") String token, @RequestParam int accountId){
        return accountService.deleteAccount(accountId);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping(path = "/update", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public PutResponse updateAccount(@RequestHeader("Authorization") String token, @RequestBody UpdateAccountRequest updateAccountRequest){
        return accountService.updateAccount(updateAccountRequest);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping(path = "/transfer", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public PutResponse transferToAccount(@RequestHeader("Authorization") String token, @RequestBody TransferRequest transferRequest){
        return accountService.transferToAnotherAccount(transferRequest);
    }

}
