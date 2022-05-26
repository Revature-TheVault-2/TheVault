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
import com.revature.thevault.service.classes.AccountService;
import com.revature.thevault.service.exceptions.InvalidAuthorizationError;
import com.revature.thevault.utility.JWTInfo;
import com.revature.thevault.utility.JWTUtility;

@CrossOrigin("*")
@RestController("accountController")
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private AccountService accountService;

    
	/**
	 * Receives a request header (token) and and int (account id). JWTInfo object is
	 * instantiated by the method JWTUtility.verifyUser(token) which contains a
	 * JWTInfo object with the values of (int) user Id and (String) username. If the
	 * object is null due to the JWTUtility.verifyUser(token) method throwing an
	 * exception, a 401 status code is thrown to the client with the message "No
	 * valid JWT".
	 * 
	 * If the JWTInfo object is not null, a GetResponse object is returned by the
	 * method accountService.getAccount(accountId parameter)
	 * 
	 * @Author Previous Batch
	 * @param token
	 * @param accountId
	 * @Author previous batch
	 * @return GetResponse object
	 */
    
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(path = "/find", produces = MediaType.APPLICATION_JSON_VALUE)
    public GetResponse getAccount(@RequestHeader("Authorization") String token, @RequestParam int accountId){
        JWTInfo parsedJWT = JWTUtility.verifyUser(token);
        if(parsedJWT != null) return accountService.getAccount(accountId);
        else throw new InvalidAuthorizationError(HttpStatus.UNAUTHORIZED, "No valid JWT");
    }

    /**
     * Receives a request header (token) and and int (user id). JWTInfo object is
	 * instantiated by the method JWTUtility.verifyUser(token) which contains a
	 * JWTInfo object with the values of (int) user Id and (String) username. If the
	 * object is null due to the JWTUtility.verifyUser(token) method throwing an
	 * exception, a 401 status code is thrown to the client with the message "No
	 * valid JWT".
	 * 
	 * If the JWTInfo object is not null, a GetResponse object is returned by the
	 * method accountService.getAccounts(userId parameter)
     * 
     * @Author Previous Batch
     * @param token
     * @param userId
     * @return
     */
    
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(path = "/users-accounts", produces = MediaType.APPLICATION_JSON_VALUE)
    public GetResponse getUserAccountList(@RequestHeader("Authorization") String token, @RequestParam int userId){
        JWTInfo parsedJWT = JWTUtility.verifyUser(token);
        if(parsedJWT != null) return accountService.getAccounts(userId);
        else throw new InvalidAuthorizationError(HttpStatus.UNAUTHORIZED, "No valid JWT");
    }

	/**
	 * Receives a request header (token) and a CreateAccountRequest object. JWTInfo
	 * object is instantiated by the method JWTUtility.verifyUser(token) which
	 * contains a JWTInfo object with the values of (int) user Id and (String)
	 * username. If the object is null due to the JWTUtility.verifyUser(token)
	 * method throwing an exception, a 401 status code is thrown to the client with
	 * the message "No valid JWT".
	 * 
	 * If the JWTInfo object is not null, a PostResponse object is returned by
	 * calling the method accountService.createAccount(createAccountRequest
	 * parameter)
	 * 
	 * @Author Previous Batch
	 * @param token
	 * @param userId
	 * @return
	 */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path = "/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public PostResponse createAccount(@RequestHeader("Authorization") String token, @RequestBody CreateAccountRequest createAccountRequest){
        JWTInfo parsedJWT = JWTUtility.verifyUser(token);
        if(parsedJWT != null) return accountService.createAccount(createAccountRequest);
        else throw new InvalidAuthorizationError(HttpStatus.UNAUTHORIZED, "No valid JWT");
    }

	/**
	 * Receives a request header (token) and a accountId int. JWTInfo object is
	 * instantiated by the method JWTUtility.verifyUser(token) which contains a
	 * JWTInfo object with the values of (int) user Id and (String) username. If the
	 * object is null due to the JWTUtility.verifyUser(token) method throwing an
	 * exception, a 401 status code is thrown to the client with the message "No
	 * valid JWT".
	 * 
	 * If the JWTInfo object is not null, a DeleteResponse object is returned by
	 * calling the method accountService.deleteAccount(accountId parameter)
	 * 
	 * @Author Previous Batch
	 * @param token
	 * @param accountId
	 * @return
	 */
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping(path = "/delete", produces = MediaType.APPLICATION_JSON_VALUE)
    public DeleteResponse deleteAccount(@RequestHeader("Authorization") String token, @RequestParam int accountId){
        JWTInfo parsedJWT = JWTUtility.verifyUser(token);
        if(parsedJWT != null) return accountService.deleteAccount(accountId);
        else throw new InvalidAuthorizationError(HttpStatus.UNAUTHORIZED, "No valid JWT");
    }

	/**
	 * Receives a request header (token) and a UpdateAccountRequest object. JWTInfo
	 * object is instantiated by the method JWTUtility.verifyUser(token) which
	 * contains a JWTInfo object with the values of (int) user Id and (String)
	 * username. If the object is null due to the JWTUtility.verifyUser(token)
	 * method throwing an exception, a 401 status code is thrown to the client with
	 * the message "No valid JWT".
	 * 
	 * Else, a PutResponse is returned by calling the
	 * accountService.updateAccount(updateAccountRequest parameter) method.
	 * 
	 * @Author Previous Batch
	 * @param
	 * @Return PutResponse
	 */
    @ResponseStatus(HttpStatus.OK)
    @PutMapping(path = "/update", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public PutResponse updateAccount(@RequestHeader("Authorization") String token, @RequestBody UpdateAccountRequest updateAccountRequest){
        JWTInfo parsedJWT = JWTUtility.verifyUser(token);
        if(parsedJWT != null) return accountService.updateAccount(updateAccountRequest);
        else throw new InvalidAuthorizationError(HttpStatus.UNAUTHORIZED, "No valid JWT");
    }

	/**
	 * Receives a request header (token) and a TransferRequest object. JWTInfo
	 * object is instantiated by the method JWTUtility.verifyUser(token) which
	 * contains a JWTInfo object with the values of (int) user Id and (String)
	 * username. If the object is null due to the JWTUtility.verifyUser(token)
	 * method throwing an exception, a 401 status code is thrown to the client with
	 * the message "No valid JWT".
	 * 
	 * Else, a PutResponse is returned by calling the
	 * accountService.transferToAnotherAccount(transferRequest {parameter}) method.
	 * 
	 * @Author Previous Batch
	 * @param token
	 * @param transferRequest
	 * @return
	 */
    @ResponseStatus(HttpStatus.OK)
    @PutMapping(path = "/transfer", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public PutResponse transferToAccount(@RequestHeader("Authorization") String token, @RequestBody TransferRequest transferRequest){
        JWTInfo parsedJWT = JWTUtility.verifyUser(token);
        if(parsedJWT != null) return accountService.transferToAnotherAccount(transferRequest);
        else throw new InvalidAuthorizationError(HttpStatus.UNAUTHORIZED, "No valid JWT");
    }

}
