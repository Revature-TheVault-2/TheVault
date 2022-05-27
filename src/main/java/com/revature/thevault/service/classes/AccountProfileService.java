package com.revature.thevault.service.classes;

import com.revature.thevault.presentation.model.request.AccountProfileRequest;
import com.revature.thevault.presentation.model.request.ProfileCreateRequest;
import com.revature.thevault.presentation.model.request.UpdateProfileRequest;
import com.revature.thevault.presentation.model.response.AccountProfileResponse;
import com.revature.thevault.presentation.model.response.builder.DeleteResponse;
import com.revature.thevault.presentation.model.response.builder.GetResponse;
import com.revature.thevault.presentation.model.response.builder.PostResponse;
import com.revature.thevault.presentation.model.response.builder.PutResponse;
import com.revature.thevault.repository.dao.AccountProfileRepository;
import com.revature.thevault.repository.entity.AccountProfileEntity;
import com.revature.thevault.repository.entity.LoginCredentialEntity;
import com.revature.thevault.service.exceptions.InvalidProfileIdException;
import com.revature.thevault.service.exceptions.InvalidRequestException;
import com.revature.thevault.service.interfaces.AccountProfileInterface;
import com.revature.thevault.utility.enums.ResponseType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.Optional;

@Service("accountProfileService")
public class AccountProfileService implements AccountProfileInterface {

    @Autowired
    private AccountProfileRepository accountProfileRepository;

    
    private static LoginService loginService;

    /**
     * Takes the ID from the param, and then creates a LoginCredential with just an ID.
     * Then it finds the corresponding AccountProfile.
     * Then returns it in a GetResponse
     * @author Other Team
     * @param AccountProfileRequest
     * @return GetResponse
     */
    @Override
    public GetResponse getProfile(AccountProfileRequest accountProfileRequest) {
        LoginCredentialEntity loginCredential = new LoginCredentialEntity(
                accountProfileRequest.getProfileId(),
                "",
                ""
        );

        AccountProfileEntity profile = accountProfileRepository.findByLogincredential(loginCredential);

        try {
            return GetResponse.builder()
            		.success(true)
                    .gotObject(Collections.singletonList(convertEntityToResponse(profile)))
                    .build();
        } catch (Exception e) {
            throw new InvalidRequestException(HttpStatus.BAD_REQUEST, "invalid request");
        }
    }



    /**
     * Creates an AccountProfileEntity and sets all of its fields from the parameter.
     * DAO call to save the created profile entity.
     * The return from the DB is the parameter for the convertEntityToResponse
     * Then builds a PostResponse Object out of success and the convertedEntity
     * 
     * @param profileCreateRequest 
     * @return PostResponse
     */
    @Override
    public PostResponse createProfile(ProfileCreateRequest profileCreateRequest) {
        AccountProfileEntity createdProfileEntity = new AccountProfileEntity(
                0,
                loginService.findUserByUserId(profileCreateRequest.getUserId()),
                profileCreateRequest.getFirstName(),
                profileCreateRequest.getLastName(),
                profileCreateRequest.getEmail(),
                profileCreateRequest.getPhoneNumber(),
                profileCreateRequest.getAddress(),
                0
        );

        AccountProfileResponse convertedCreatedEntity = convertEntityToResponse(accountProfileRepository.save(createdProfileEntity));

        try {
            return PostResponse.builder()
                    .success(true)
                    .createdObject(Collections.singletonList(convertedCreatedEntity))
                    .build();
        } catch (Exception e) {
            throw new InvalidRequestException(HttpStatus.BAD_REQUEST, "invalid request");
        }
    }

    
    /**
     * This method takes the param and makes a new AccountProfileEntity setting all of its values to the param's.
     * Then it saves it to the DB.
     * What the DB returns is converted to an AccountProfileResponse
     * Then the response is put into a PutResponse and sent back
     * @author Other Team
     * @param updateProfileRequest
     * @return PutResponse
     */
    @Override
    public PutResponse updateProfile(UpdateProfileRequest updateProfileRequest) {
        try {
            return PutResponse.builder()
                    .success(true)
                    .updatedObject(Collections.singletonList(convertEntityToResponse(accountProfileRepository.save(new AccountProfileEntity(
                            updateProfileRequest.getProfileId(),
                            loginService.findUserByUserId(updateProfileRequest.getUserId()),
                            updateProfileRequest.getFirstName(),
                            updateProfileRequest.getLastName(),
                            updateProfileRequest.getEmail(),
                            updateProfileRequest.getPhoneNumber(),
                            updateProfileRequest.getAddress(),
                            0
                    )))))
                    .build();
        } catch (Exception e) {
            throw new InvalidRequestException(HttpStatus.BAD_REQUEST, "invalid request");
        }
    }


    @Override
    @Transactional
    @Deprecated
    public DeleteResponse deleteProfile(AccountProfileRequest accountProfileRequest) {

        try {
            Optional<AccountProfileEntity> optionalProfile = accountProfileRepository.findById(accountProfileRequest.getProfileId());
            if(optionalProfile.isPresent()) {
                accountProfileRepository.delete(optionalProfile.get());
                return DeleteResponse.builder()
                        .success(true)
                        .deletedObject(Collections.singletonList(convertEntityToResponse(optionalProfile.get())))
                        .build();
            }
            else{
                throw new InvalidProfileIdException(HttpStatus.BAD_REQUEST, "account profile not found");
            }
        }catch(InvalidProfileIdException e) {
            throw e;
        }
    }

    /**
     * HELPER METHOD
     * 
     * @param accountProfileEntity
     * @return AccountProfileResponse
     */
    private AccountProfileResponse convertEntityToResponse(AccountProfileEntity accountProfileEntity) {
        return new AccountProfileResponse(
                accountProfileEntity.getPk_profile_id(),
                accountProfileEntity.getLogincredential().getPkuserid(),
                accountProfileEntity.getFirst_name(),
                accountProfileEntity.getLast_name(),
                accountProfileEntity.getEmail(),
                accountProfileEntity.getPhone_number(),
                accountProfileEntity.getAddress()
        );
    }
}
