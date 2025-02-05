package com.revature.thevault.presentation.controller;

import com.revature.thevault.presentation.model.response.builder.GetResponse;
import com.revature.thevault.service.classes.TransactionService;
import com.revature.thevault.service.exceptions.InvalidAuthorizationError;
import com.revature.thevault.utility.JWTInfo;
import com.revature.thevault.utility.JWTUtility;

import java.io.FileNotFoundException;
import java.net.MalformedURLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * 
 * Transaction Controller has only one method, getTransactionHistory, 
 * which returns the transaction history of an account id if the authorization token is valid
 *
 */
@CrossOrigin("*")
@RestController("transactionController")
@RequestMapping("/transaction")
public class TransactionController {

	/**
	 * Creates a transaction  service 
	 */
    @Autowired
    private TransactionService transactionService;

    /**
	 * Gets the transaction history through the transaction service 
	 * @throws InvalidAuthorizationError
	 * @return Transaction history
	 * 
	 */
    @GetMapping("/history/{accountId}")
    public GetResponse getTransactionHistory(@RequestHeader("Authorization") String token, @PathVariable Integer accountId) {
        return transactionService.getTransactionHistory(accountId);
    }
    
    @GetMapping("/history/{accountId}/{year}/{month}/{profileId}")
    public GetResponse getTransactionHistoryByMonth(@RequestHeader("Authorization") String token, @PathVariable Integer accountId, @PathVariable Integer year, @PathVariable Integer month, @PathVariable Integer profileId) throws FileNotFoundException, MalformedURLException {
    	return transactionService.getTransactionHistoryByMonth(accountId, month, year, profileId);
    }

}
