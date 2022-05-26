package com.revature.thevault.presentation.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
<<<<<<< HEAD
=======

import com.revature.thevault.presentation.model.request.CreateAccountRequest;
import com.revature.thevault.presentation.model.request.TransferRequest;
import com.revature.thevault.presentation.model.request.UpdateAccountRequest;
import com.revature.thevault.presentation.model.response.builder.DeleteResponse;
import com.revature.thevault.presentation.model.response.builder.GetResponse;
import com.revature.thevault.presentation.model.response.builder.PostResponse;
import com.revature.thevault.presentation.model.response.builder.PutResponse;
import com.revature.thevault.service.classes.AccountService;
>>>>>>> 64f8b9ae0f6865205fdcbd0304034fcf6c382dd2

import com.revature.thevault.presentation.model.request.CreateAccountRequest;
import com.revature.thevault.presentation.model.request.TransferRequest;
import com.revature.thevault.presentation.model.request.UpdateAccountRequest;
import com.revature.thevault.presentation.model.response.builder.DeleteResponse;
import com.revature.thevault.presentation.model.response.builder.GetResponse;
import com.revature.thevault.presentation.model.response.builder.PostResponse;
import com.revature.thevault.presentation.model.response.builder.PutResponse;
import com.revature.thevault.service.classes.AccountService;
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
    
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(path = "/test")
    public String testController(){
        return "You have pinged the Vault 2 Server";
    }

}
