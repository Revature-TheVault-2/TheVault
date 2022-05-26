package com.revature.thevault.presentation.controller;

import com.revature.thevault.presentation.model.request.AccountProfileRequest;
import com.revature.thevault.presentation.model.request.ProfileCreateRequest;
import com.revature.thevault.presentation.model.request.UpdateProfileRequest;
import com.revature.thevault.presentation.model.response.AccountProfileResponse;
import com.revature.thevault.presentation.model.response.builder.DeleteResponse;
import com.revature.thevault.presentation.model.response.builder.GetResponse;
import com.revature.thevault.presentation.model.response.builder.PostResponse;
import com.revature.thevault.presentation.model.response.builder.PutResponse;
import com.revature.thevault.repository.entity.AccountProfileEntity;
import com.revature.thevault.repository.entity.LoginCredentialEntity;
import com.revature.thevault.service.classes.AccountProfileService;
import com.revature.thevault.service.exceptions.InvalidAuthorizationError;
import com.revature.thevault.utility.JWTInfo;
import com.revature.thevault.utility.JWTUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;


@RestController("accountProfileController")
@RequestMapping("/profile")
public class AccountProfileController {

    @Autowired
    private AccountProfileService accountProfileService;

    /**
     * Invokes the paired service layer and then calls its createProfile method by passing the ProfileCreateRequest
     * @author previous team
     * @param profileCreateRequest - this is the ?base model? (AccountProfileEntity) minus LoginCredentailEntity Obj
     * @return PostResponse Object - which carries the request object and a success boolean
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/create")
    public PostResponse createProfile(@RequestBody ProfileCreateRequest profileCreateRequest){
        return accountProfileService.createProfile(profileCreateRequest);
    }

    /**
     * Does a session check.
     * Invokes the paired service layer and then calls its getProfile method by passing the ID along
     * 
     * @param token
     * @param userId
     * @return GetResponse - which carries the request object and a success boolean
     */
    @GetMapping("/get/{id}")
    public GetResponse getProfile(@PathVariable int id){
    	return accountProfileService.getProfile(new AccountProfileRequest(id));
    }
    /**
     * remove, this is not necessary to the application
     * Deprecated due to comment left by other team^
     * @param token
     * @param accountProfileRequest
     * @return
     */
    @Deprecated
    @DeleteMapping("/delete")
    public DeleteResponse deleteProfile(@RequestHeader("Authorization") String token, @RequestBody AccountProfileRequest accountProfileRequest){
        return accountProfileService.deleteProfile(accountProfileRequest);
    }

    /**
     * Does a session check.
     * Invokes the paired service layer and then calls its updateProfile method passign along the param
     * @param token
     * @param updateProfileRequest
     * @return PutResponse object - which carries the request object and a success boolean
     */
    @PutMapping("/update")
    public PutResponse updateProfile(@RequestHeader("Authorization") String token, @RequestBody UpdateProfileRequest updateProfileRequest){
        return accountProfileService.updateProfile(updateProfileRequest);
    }
}
