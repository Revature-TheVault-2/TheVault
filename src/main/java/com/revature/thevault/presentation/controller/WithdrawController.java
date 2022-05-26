package com.revature.thevault.presentation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.revature.thevault.presentation.model.request.WithdrawRequest;
import com.revature.thevault.presentation.model.response.builder.DeleteResponse;
import com.revature.thevault.presentation.model.response.builder.GetResponse;
import com.revature.thevault.presentation.model.response.builder.PostResponse;
import com.revature.thevault.service.classes.WithdrawService;
import com.revature.thevault.service.exceptions.InvalidAuthorizationError;
import com.revature.thevault.utility.JWTInfo;
import com.revature.thevault.utility.JWTUtility;

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

			return withdrawService.getAllUserWithdrawals(accountId);
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

			return withdrawService.createWithdrawal(withdrawRequest);
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

			return withdrawService.findByWithdrawId(withdrawId);
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

			return withdrawService.getAlLUserWithdrawalsOfType(accountId, requestType);
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

			return withdrawService.deleteAllWithdraws(accountId);
		}
}
