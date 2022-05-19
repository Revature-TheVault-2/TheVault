package com.revature.thevault.presentation.controller;

import com.revature.thevault.presentation.model.request.DepositRequest;
import com.revature.thevault.presentation.model.response.builder.DeleteResponse;
import com.revature.thevault.presentation.model.response.builder.GetResponse;
import com.revature.thevault.presentation.model.response.builder.PostResponse;
import com.revature.thevault.service.classes.DepositService;
import com.revature.thevault.service.exceptions.InvalidAuthorizationError;
import com.revature.thevault.utility.JWTInfo;
import com.revature.thevault.utility.JWTUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;


/**
 * @author chris & fred
 * Handles deposit endpoints, authorization token required for all methods.
 * dependencies: depositService
 * TODO: move returns to a new line for better readability.
 */
@CrossOrigin("*")
@RestController("depositController")
@RequestMapping("/deposit")
public class DepositController {

		@Autowired
		private DepositService depositService;

		/**
		 * Creates new deposit by calling service layer method.
		 * @param String token 
		 * @param DepositRequest depositRequest
		 * @return PostResponse 
		 */
		@ResponseStatus(HttpStatus.CREATED)
		@PostMapping("/create")
		public PostResponse createDeposit(@RequestHeader("Authorization") String token, @RequestBody DepositRequest depositRequest) {
			JWTInfo parsedJWT = JWTUtility.verifyUser(token);
			if(parsedJWT != null) return depositService.createDeposit(depositRequest);
        else throw new InvalidAuthorizationError(HttpStatus.UNAUTHORIZED, "No valid JWT");
		}

		/**
		 * Verifies deposit authorization token and finds deposit by id number.
		 * @param String token
		 * @param int depositId
		 * @return GetResponse 
		 */
		@GetMapping("/detail")
		public GetResponse getByDepositId(@RequestHeader("Authorization") String token, @RequestParam int depositId){
			JWTInfo parsedJWT = JWTUtility.verifyUser(token);
			if(parsedJWT != null) return depositService.findByDepositId(depositId);
        else throw new InvalidAuthorizationError(HttpStatus.UNAUTHORIZED, "No valid JWT");
		}

		/** 
		 * Verifies user by token and returns all deposits by user id.
		 * @param String token
		 * @param Integer id
		 * @return GetResponse
		 * TODO: possibly change variable name for id, it's too vague.
		 */
		@ResponseStatus(HttpStatus.OK)
		@GetMapping("/all/{id}")
		public GetResponse findAllById(@RequestHeader("Authorization") String token, @PathVariable Integer id) {
			JWTInfo parsedJWT = JWTUtility.verifyUser(token);
			if(parsedJWT != null) return depositService.getAllUserDeposits(id);
        else throw new InvalidAuthorizationError(HttpStatus.UNAUTHORIZED, "No valid JWT");
		}

		/**
		 * Verifies user by token and then returns all deposits by account id and deposit type id.
		 * @param String token
		 * @param String depositType
		 * @param Integer accountId
		 * @return GetResponse
		 */
		@ResponseStatus(HttpStatus.OK)
		@GetMapping(path = "/type/{accountId}", produces = MediaType.APPLICATION_JSON_VALUE)
		public GetResponse findByAccountIdAndDepositTypeId(@RequestHeader("Authorization") String token, @RequestParam String depositType, @PathVariable Integer accountId) {
			JWTInfo parsedJWT = JWTUtility.verifyUser(token);
			if(parsedJWT != null) return this.depositService.getAlLUserDepositsOfType(accountId, depositType);
        else throw new InvalidAuthorizationError(HttpStatus.UNAUTHORIZED, "No valid JWT");
		}

		/**
		 * Deletes all deposits by account id.
		 * @param String token
		 * @param Integer accountId
		 * @return DeleteResponse
		 */
		@DeleteMapping("/clear/{accountId}")
		public DeleteResponse deleteAllDeposits(@RequestHeader("Authorization") String token, @PathVariable Integer accountId){
			JWTInfo parsedJWT = JWTUtility.verifyUser(token);
			if(parsedJWT != null) return this.depositService.deleteAllDeposits(accountId);
        else throw new InvalidAuthorizationError(HttpStatus.UNAUTHORIZED, "No valid JWT");
		}
}


