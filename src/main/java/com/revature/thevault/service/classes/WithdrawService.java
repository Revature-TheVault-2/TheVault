package com.revature.thevault.service.classes;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.revature.thevault.presentation.model.request.WithdrawRequest;
import com.revature.thevault.presentation.model.response.builder.DeleteResponse;
import com.revature.thevault.presentation.model.response.builder.GetResponse;
import com.revature.thevault.presentation.model.response.builder.PostResponse;
import com.revature.thevault.repository.dao.WithdrawRepository;
import com.revature.thevault.repository.entity.AccountEntity;
import com.revature.thevault.repository.entity.AccountTypeEntity;
import com.revature.thevault.repository.entity.LoginCredentialEntity;
import com.revature.thevault.repository.entity.WithdrawEntity;
import com.revature.thevault.service.dto.WithdrawResponseObject;
import com.revature.thevault.service.exceptions.InvalidAccountIdException;
import com.revature.thevault.service.exceptions.InvalidRequestException;
import com.revature.thevault.service.exceptions.InvalidWithdrawIdRequest;
import com.revature.thevault.service.interfaces.WithdrawServiceInterface;

@Service("withdrawService")
public class WithdrawService implements WithdrawServiceInterface {

    @Autowired
    private WithdrawRepository withdrawRepository;

    @Autowired
    private RequestTypeService requestTypeService;

    @Autowired
    private RequestStatusService requestStatusService;

    /**
     * Takes an inputted withdraw object and saves it to the database, then returns it as a post request
     * 
     * @param withdrawRequest Object
     * @return a POST request that contains a singleton list of the created withdraw
     */
    @Override
    public PostResponse createWithdrawal(WithdrawRequest withdrawRequest) {
    	// float balancePostWithdrawal = sessionUser.getAvaiable_balance() - withdrawRequest.getAmount(); 
    	
    //Outer if(notificationAmount != 0){
    	//if(-withdrawRequest.getAmount() > "current user session".getNoficationAmount()){
		// emailservice.NotifcationEmail(withdrawRequest.getAmount()) }}
    	
    	// if(balancePostWithdrawal < 0){
    	// emailservice.overdraftEmail(balancePostWithdrawal); }
        return PostResponse.builder()
                .success(true)
                .createdObject( Collections.singletonList(
                   convertEntityToResponse(    
                     withdrawRepository.save(new WithdrawEntity (0,
                        	new AccountEntity(withdrawRequest.getAccountId(),new LoginCredentialEntity(),new AccountTypeEntity(),0, 0),  
                           requestTypeService.getRequestTypeByName(withdrawRequest.getRequestType()),                
                           requestStatusService.getRequestStatusByName("Pending"),                             
                           withdrawRequest.getReference(),                        
                           Date.valueOf(LocalDate.now()),                           
                           withdrawRequest.getAmount())                                                                
                                                
                                        )
                                )
                    )
                )
                .build();
    }

    /**
     * Gets a list of all withdraws associated with an account by the ID of the account
     * 
     * @param accountId
     * @return A custom get response that contains the list
     */
    @Override
    public GetResponse getAllUserWithdrawals(int accountId) {
        List<WithdrawEntity> withdrawEntities = findByAccountId(accountId);
        return GetResponse.builder()
                .success(true)
                .gotObject(convertEntityListToResponses(withdrawEntities))
                .build();
    }
    
    /**
	 * Get user withdraws for a given month and year.
	 * @param accountId
	 * @param month
	 * @param year
	 * @return GetResponse containing a list of withdrawResponses
     */
    public GetResponse getAllUserWithdrawlsByMonth(int accountId, int month, int year) {
    	Calendar cal = Calendar.getInstance();
    	cal.set(year, month-1, 1);
    	cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DATE));
    	Date startDate = new Date(cal.getTimeInMillis());
    	cal.add(Calendar.MONTH, 1);
    	cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DATE));
    	Date endDate = new Date(cal.getTimeInMillis());
    	try {
    	List<WithdrawEntity> withdrawEntities = findByAccountIdAndDateBetween(accountId, startDate, endDate);
        return GetResponse.builder()
                .success(true)
                .gotObject(convertEntityListToResponses(withdrawEntities))
                .build();
    } catch (InvalidAccountIdException e) {
		throw e;
	} catch (EntityNotFoundException e) {
		throw new InvalidRequestException(HttpStatus.BAD_REQUEST, "Deposits not Found for Account: " + accountId + " in range: " + startDate + " to " + endDate);
	} catch (Exception e) {
		throw new InvalidRequestException(HttpStatus.BAD_REQUEST, "Invalid Request");
	}
    }


    
    /**
     * Creates a list of withdraws based on account Id
     * 
     * @param accountId
     * @return A list of withdraws from the dao layer based on Id
     */
    private List<WithdrawEntity> findByAccountId(int accountId) {
        return withdrawRepository.findByAccountentity(
                new AccountEntity(
                        accountId,
                        new LoginCredentialEntity(),
                        new AccountTypeEntity(),
                        0,
                        0
                )
        );
    }
    
    /**
     * Creates a list of withdraws based on account Id
     * and between the given dates
     * 
     * @param int accountId
	 * @param startDate
	 * @param endDate
	 * @return List<DepositEntity>
	 * @author Frederick
     */
    private List<WithdrawEntity> findByAccountIdAndDateBetween(int accountId, Date startDate, Date endDate) {
        return withdrawRepository.findByAccountIdAndDatesBetween(accountId, startDate, endDate);
    }

    /**
     * Generates a get response with the list generated in the findByAccountIdAndRequestType method
     * 
     * @param accountId
     * @param requestName is requestType
     * @return A get response with the found list of withdraws
     */
    @Override
    public GetResponse getAlLUserWithdrawalsOfType(int accountId, String requestName) {
        return GetResponse.builder()
                .success(true)
                .gotObject(
                        convertEntityListToResponses(
                                findByAccountIdAndRequestType(accountId, requestName)
                        )
                )
                .build();
    }

    /**
     * Creates an optional object of a withdraw by finding based on the ID, then if it exists makes a singleton list of that withdraw
     * 
     * @param withdrawId
     * @return a singleton list of the searched for withdraw
     * @throws InvalidWithdrawIdRequest (if the id doesn't exist)
     */
    @Override
    public GetResponse findByWithdrawId(int withdrawId) {
    	
        Optional<WithdrawEntity> withdrawEntityOptional = withdrawRepository.findById(withdrawId);
        // Optional means that it can be null if it doesn't find anything
        
        if(withdrawEntityOptional.isPresent()) { // Checks that the object actually exists
            return GetResponse.builder()
                    .success(true)
                    .gotObject(Collections.singletonList(
                            convertEntityToResponse(withdrawEntityOptional.get()) // Converts the normal object into a "response" object of the same type and values
                    ))
                    .build();
        } else {
            throw new InvalidWithdrawIdRequest(HttpStatus.BAD_REQUEST, "Withdraw not found, withdraw Id: " + withdrawId);
        }
    }

    /**
     * Deletes all withdraws of an account by accountId
     * 
     * @param An Integer accountId, we believe due to path variable
     * @return A Delete response with an empty list
     */
    @Override
    public DeleteResponse deleteAllWithdraws(Integer accountId) {
        withdrawRepository.deleteByAccountentity(new AccountEntity(accountId, new LoginCredentialEntity(), new AccountTypeEntity(), 0, 0));
        return DeleteResponse.builder()
                .success(true)
                .deletedObject(Collections.EMPTY_LIST)
                .build();
    }

    /**
     * Creates a list of withdraws that are filtered by account id and request type
     * 
     * @param accountId
     * @param requestName
     * @return list of withdraws based on the id and request type
     */
    private List<WithdrawEntity> findByAccountIdAndRequestType(int accountId, String requestName) {
        return withdrawRepository.findByAccountentityAndRequesttypeentity( // Still not using a session
        			// The account id for some reason													and the type of request
           new AccountEntity(accountId,new LoginCredentialEntity(),new AccountTypeEntity(),0,0), requestTypeService.getRequestTypeByName(requestName) );
                        
    }
    
    /**
     * Creates a list of withdraw response objects from a list of withdraw entity objects using forEach loop to call the convertEntityToResponse method
     * 
     * @param withdrawEntities
     * @return the list of withdrawResponseObjects
     */
    private List<WithdrawResponseObject> convertEntityListToResponses(List<WithdrawEntity> withdrawEntities) {
    	
    	List<WithdrawResponseObject> withdrawResponseObjects = new ArrayList<>(withdrawEntities.size());
    	
    	withdrawEntities.forEach(withdraw -> withdrawResponseObjects.add(convertEntityToResponse(withdraw)));
    	
    	return withdrawResponseObjects;
    }
    
/**
 * Converts a withdraw entity into response object. And changes the fields that would have more than one part due to being foreign keys into specific values
 * from those foreign keys
 * 
 * @param withdrawEntity
 * @return a Response object of a withdraw
 */
    private WithdrawResponseObject convertEntityToResponse(WithdrawEntity withdrawEntity) {
        return new WithdrawResponseObject(
                withdrawEntity.getPk_withdraw_id(),
                withdrawEntity.getAccountentity().getPk_account_id(),
                withdrawEntity.getRequesttypeentity().getName(),
                withdrawEntity.getRequeststatusentity().getName(),
                withdrawEntity.getReference(),
                withdrawEntity.getDateWithdraw().toLocalDate(),
                withdrawEntity.getAmount()
		);
	}
}
