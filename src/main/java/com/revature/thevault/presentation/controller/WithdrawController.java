package com.revature.thevault.presentation.controller;

import com.revature.thevault.presentation.model.request.WithdrawRequest;
import com.revature.thevault.presentation.model.response.builder.DeleteResponse;
import com.revature.thevault.presentation.model.response.builder.GetResponse;
import com.revature.thevault.presentation.model.response.builder.PostResponse;
import com.revature.thevault.service.classes.WithdrawService;
import com.revature.thevault.service.exceptions.InvalidAuthorizationError;
import com.revature.thevault.utility.JWTInfo;
import com.revature.thevault.utility.JWTUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController("withdrawController")
@RequestMapping("/withdraw")
public class WithdrawController {

	@Autowired
	private WithdrawService withdrawService;

	/**
	 * First verifies if the user is logged in with JWT then returns a list of the withdraws associated with the account ID
	 * 
	 * @param token
	 * @param accountId
	 * @return the list of withdraws as a getResponse
	 * @throws an invalid authorization error if you are not logged in
	 */
	@GetMapping("/all/{accountId}")
	public GetResponse getUsersWithdraws(@RequestHeader("Authorization") String token, @PathVariable Integer accountId) {

		JWTInfo parsedJWT = JWTUtility.verifyUser(token);

		if (parsedJWT != null) {
			return withdrawService.getAllUserWithdrawals(accountId);
		} else {
			throw new InvalidAuthorizationError(HttpStatus.UNAUTHORIZED, "No valid JWT");
		}
	}

	
	/**
	 * First verifies if the user is logged in with JWT then creates a new withdraw request in the database
	 * 
	 * @param token
	 * @param withdrawRequest
	 * @return a POST response with a singleton list of the created withdraw
	 * @throws InvalidAuthorizationError if not logged in
	 * 
	 */
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping("/generate")
	public PostResponse createWithdrawal(@RequestHeader("Authorization") String token, @RequestBody WithdrawRequest withdrawRequest) {
		
		JWTInfo parsedJWT = JWTUtility.verifyUser(token);
		
		if (parsedJWT != null) {
			return withdrawService.createWithdrawal(withdrawRequest);
		}else {
			throw new InvalidAuthorizationError(HttpStatus.UNAUTHORIZED, "No valid JWT");
		}
	}

	/**
	 * First verifies if the user is logged in with JWT then finds the details of a specified withdraw based on ID
	 * 
	 * @param token
	 * @param withdrawId
	 * @return a GetRequest containing a singleton list of the specified withdraw
	 * @throws InvalidAuthorizationError if not logged in
	 */
	@GetMapping("/detail")
	public GetResponse findByWithdrawId(@RequestHeader("Authorization") String token, @RequestParam int withdrawId) {
		
		JWTInfo parsedJWT = JWTUtility.verifyUser(token);
		
		if (parsedJWT != null) {
			return withdrawService.findByWithdrawId(withdrawId);
		}else {
			throw new InvalidAuthorizationError(HttpStatus.UNAUTHORIZED, "No valid JWT");
		}
	}

	/**
	 * First verifies if the user is logged in with JWT then Filters the withdraws by type and id
	 * 
	 * 
	 * @param token
	 * @param requestType
	 * @param accountId
	 * @return Get Response with a filtered list
	 * @throws InvalidAuthorizationError if not logged in
	 */
	@GetMapping("/type/{requestType}")
	public GetResponse getWithdrawalByType(@RequestHeader("Authorization") String token, @PathVariable String requestType, @RequestParam int accountId) {
		
		JWTInfo parsedJWT = JWTUtility.verifyUser(token);
		
		if (parsedJWT != null) {
			return withdrawService.getAlLUserWithdrawalsOfType(accountId, requestType);
		}else {
			throw new InvalidAuthorizationError(HttpStatus.UNAUTHORIZED, "No valid JWT");
		}
	}

	
	/**
	 * First verifies if the user is logged in with JWT then deletes all known withdraws by the accountId
	 * 
	 * @param token
	 * @param accountId
	 * @return a Delete Response that contains an empty list
	 * @throws InvalidAuthorizationError if not logged in
	 */
	@DeleteMapping("/clear/{accountId}")
	public DeleteResponse deleteAllWithdraws(@RequestHeader("Authorization") String token, @PathVariable Integer accountId) {
		
		JWTInfo parsedJWT = JWTUtility.verifyUser(token);
		
		if (parsedJWT != null) {
			return withdrawService.deleteAllWithdraws(accountId);
		}else {
			throw new InvalidAuthorizationError(HttpStatus.UNAUTHORIZED, "No valid JWT");
		}
	}
}
