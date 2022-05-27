package com.revature.thevault.service.interfaces;

import com.revature.thevault.presentation.model.request.LoginRequest;
import com.revature.thevault.presentation.model.request.NewLoginCredentialsRequest;
import com.revature.thevault.presentation.model.request.ResetPasswordRequest;
import com.revature.thevault.presentation.model.response.LoginResponse;
import com.revature.thevault.presentation.model.response.builder.PostResponse;
import com.revature.thevault.presentation.model.response.builder.PutResponse;
import com.revature.thevault.repository.entity.LoginCredentialEntity;
import com.revature.thevault.service.exceptions.InvalidInputException;

public interface LoginServiceInterface {
    LoginResponse checkLogin(LoginRequest loginRequest);

    PostResponse getLoginCredentialFromLogin(String username);

    PostResponse createNewLogin(NewLoginCredentialsRequest newLoginRequest) throws InvalidInputException;

    PutResponse resetPassword(ResetPasswordRequest resetPasswordRequest);

    LoginCredentialEntity findUserByUserId(int userId);
}
