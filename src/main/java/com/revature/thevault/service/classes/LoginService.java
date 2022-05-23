package com.revature.thevault.service.classes;

import com.revature.thevault.presentation.model.request.LoginRequest;
import com.revature.thevault.presentation.model.request.NewLoginCredentialsRequest;
import com.revature.thevault.presentation.model.request.ResetPasswordRequest;
import com.revature.thevault.presentation.model.response.LoginResponse;
import com.revature.thevault.presentation.model.response.builder.PostResponse;
import com.revature.thevault.presentation.model.response.builder.PutResponse;
import com.revature.thevault.repository.dao.LoginRepository;
import com.revature.thevault.repository.entity.LoginCredentialEntity;
import com.revature.thevault.service.dto.LoginResponseObject;
import com.revature.thevault.service.exceptions.InvalidInputException;
import com.revature.thevault.service.exceptions.InvalidRequestException;
import com.revature.thevault.service.interfaces.LoginServiceInterface;
import com.revature.thevault.utility.JWTUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service("loginService")
public class LoginService implements LoginServiceInterface {

	@Autowired
	private LoginRepository loginRepository;


	/**
	 * Service layer method that checks the login credentials
	 * @param loginRequest
	 * @return LoginResponse
	 */
	@Override
	public LoginResponse checkLogin(LoginRequest loginRequest) {
		LoginCredentialEntity loginCredentialEntity = loginRepository.findByUsername(loginRequest.getUsername());
		if (loginCredentialEntity.getPassword().contentEquals(loginRequest.getPassword())) {
			return new LoginResponse(true);
		} else {
			return new LoginResponse(false);
		}
	}

	/**
	 * Service layer that logs users in by getting the login credentials 
	 * @param loginRequest
	 * @return PostResponse
	 */
	@Override
	public PostResponse getLoginCredentialFromLogin(String username) {
		try {
			return PostResponse.builder().success(true)
					.createdObject(Collections.singletonList(convertEntityToResponse(loginRepository
							.findByUsername(username))))
					.build();
		} catch (Exception e) {
			throw new InvalidInputException("User was not found");

		}
	}
/**
 * Service layer that that creates a new login or account
 * @param newLoginRequest
 * @return PostResponse
 */
	@Override
	public PostResponse createNewLogin(NewLoginCredentialsRequest newLoginRequest) {
		try {
			return PostResponse.builder().success(true)
					.createdObject(Collections
							.singletonList(convertEntityToResponse(loginRepository.save(new LoginCredentialEntity(0,
									newLoginRequest.getUsername(), newLoginRequest.getPassword())))))
					.build();
		} catch (Exception e) {
			throw new InvalidInputException("Please check the information");
		}
	}
	/**
	 * Service layer that resets the password of the account
	 * @param resetPasswordRequest
	 * @return putResponse
	 */

	@Override
	public PutResponse resetPassword(ResetPasswordRequest resetPasswordRequest) {
		String[] randomPhrases = { "S16oRl1u", "67eQ3EfT", "vzDo", "tFECo", "yfQKYW5", "kfPYi", "mxYCQ6Y8", "C4z9s55",
				"v19n", "qih3", "vJbetBI", "1UYFn", "sxs1daex", "0W1aB5q9", "218qjn83", "rP48L", "8LF4rZ", "Q2N9p",
				"3wE", "LfIQd", "i3z", "XRP", "Tjpk7R", "E3Q9BnF", "Yb9C" };
		StringBuilder passwordResetter = new StringBuilder("");

		for (int i = 0; i < 2; i++) {
			double doubleRandomNumber = Math.random() * 25;
			int randomNumber = (int) doubleRandomNumber;
			passwordResetter.append(randomPhrases[randomNumber]);
		}
		try {
			LoginCredentialEntity loginCredentialEntity = loginRepository
					.findByUsername(resetPasswordRequest.getUsername());
			loginCredentialEntity.setPassword(passwordResetter.toString());
			return PutResponse.builder().success(true)
					.updatedObject(Collections.singletonList(loginRepository.save(loginCredentialEntity))).build();
		} catch (NullPointerException e) {
			throw new InvalidRequestException(HttpStatus.BAD_REQUEST, "invalid request");
		}
	}
	/**
	 * Service layer that finds the user by ID
	 * @param userId
	 * @return loginRepository
	 */
	@Override
	public LoginCredentialEntity findUserByUserId(int userId) {
		return loginRepository.findById(userId).orElse(null);
	}

	/**
	 * Service layer that validates user credentials to login
	 * //looks for username and password
	 * @param loginRequest
	 * @return
	 */
	public PostResponse validateLogin(LoginRequest loginRequest) {
		try {
			LoginCredentialEntity loginCredentialEntity = loginRepository
					.findByUsernameAndPassword(loginRequest.getUsername(), loginRequest.getPassword());
			return PostResponse.builder().success(true)
					.createdObject(Collections.singletonList(convertEntityToResponse(loginCredentialEntity))).build();
		} catch (Exception e) {
			throw new InvalidRequestException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}
	/**
	 * helper method to convert entity to response
	 * @param loginCredentialEntity
	 * @return LoginResponseObject
	 */


    private LoginResponseObject convertEntityToResponse(LoginCredentialEntity loginCredentialEntity) {
        return new LoginResponseObject(
                loginCredentialEntity.getPkuserid(),
                loginCredentialEntity.getUsername(),
                loginCredentialEntity.getPassword()
        );
    }

}
