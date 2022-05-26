package com.revature.thevault.service.classes;

import com.revature.thevault.presentation.model.request.CreateAccountRequest;
import com.revature.thevault.presentation.model.request.TransferRequest;
import com.revature.thevault.presentation.model.request.UpdateAccountRequest;
import com.revature.thevault.presentation.model.response.builder.DeleteResponse;
import com.revature.thevault.presentation.model.response.builder.GetResponse;
import com.revature.thevault.presentation.model.response.builder.PostResponse;
import com.revature.thevault.presentation.model.response.builder.PutResponse;
import com.revature.thevault.repository.dao.AccountRepository;
import com.revature.thevault.repository.entity.AccountEntity;
import com.revature.thevault.repository.entity.LoginCredentialEntity;
import com.revature.thevault.service.dto.AccountResponseObject;
import com.revature.thevault.service.exceptions.*;
import com.revature.thevault.service.interfaces.AccountServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.*;

@Service("accountService")
public class AccountService implements AccountServiceInterface{

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccountTypeService accountTypeService;

	/**
	 * Builds a PostResponse object containing a singleton list which holds a single
	 * AccountEntity object produced by saving a new AccountEntity to the DataBase.
	 * 
	 * @Author Previous Batch
	 * @param
	 * @Return PostResponse obj
	 */
    @Override
    public PostResponse createAccount(CreateAccountRequest createAccountRequest) {
        return PostResponse.builder()
                .success(true)
                .createdObject(Collections.singletonList(
                        convertAccountEntityToResponse(
                                accountRepository.save(
                                        new AccountEntity(
                                                0,
                                                new LoginCredentialEntity(createAccountRequest.getUserId(), "", ""),
                                                accountTypeService.findAccountTypeEntityByName(createAccountRequest.getAccountType()),
                                                0,
                                                0)
                                )
                        )
                )
                ).build(); 
    }

    /**
     * Receives an (int) account id.
     * 
     * Builds a GetResponse object containing a singleton
     * list which holds a single AccountResponseObject() that 
     * was populated by feeding the int parameter into the 
     * getAccountById method which returns an account entity.
     * 
     * @Author Previous Batch
     * @param
     * @return GetResponse obj
     */
    @Override
    public GetResponse getAccount(int accountId) {
            return GetResponse.builder()
                    .success(true)
                    .gotObject(Collections.singletonList(
                            convertAccountEntityToResponse(
                                    getAccountById(accountId))))
                    .build();
    }

	/**
	 * In the Try block: An AccountEntity object in instantiated by calling
	 * getAccountById(accountId parameter) method. The
	 * accountRepository.delete(accountEntity object) method deletes the AccountEntity
	 * from the database. Returns a DeleteResponse object that is built containing a
	 * singleton list object that holds the variables from the deleted AccountEntity
	 * object.
	 * 
	 * @Author Previous Batch
	 * @param
	 * @Return DeleteResponse object
	 */
    @Override
    public DeleteResponse deleteAccount(int accountId) {
        try {
            AccountEntity accountEntity = getAccountById(accountId);
            accountRepository.delete(accountEntity);
            return DeleteResponse.builder()
                    .success(true)
                    .deletedObject(Collections.singletonList(
                            convertAccountEntityToResponse(accountEntity)))
                    .build();
        } catch (EntityNotFoundException e) {
            throw new InvalidAccountIdException(HttpStatus.BAD_REQUEST, "Invalid Account Id for delete request: " + accountId);
        }
    }

    
    /**
     * Receives an int parameter (userId.
     * Instantiates a list by calling the method getUserAccountsByUserId(userId)
     * 
     * Builds a GetResponse object containing a List(AccountResponseObject) object and returns it.
     * 
     * @Author Previous Batch
     * @param
     * @return GetResponse object
     */
    @Override
    public GetResponse getAccounts(int userId) {
        List<AccountEntity> accountEntities = getUserAccountsByUserId(userId);
        return GetResponse.builder()
                .success(true)
                .gotObject(
                        convertAccountEntitiesToResponseList(accountEntities))
                .build();
    }

	/**
	 * An AccountEntity object(accountEntityReal) is instantiated by calling the
	 * getAccountById({parameter}.getAccountId()) method. Uses setters on the new
	 * AccountEntity object to replace the variable values with those of the
	 * {parameter}. Instantiate another AccountEntity object by calling
	 * updateAccountEntity(accountEntityReal) method. Return a PutResponse object
	 * that is built containing a singleton list object which holds a new
	 * instantiated AccountResponseObject with the same values as the updated
	 * AccountEntity.
	 * 
	 * @Author Previous Batch
	 * @param 
	 * @Return PutResponse
	 */
    @Override
    public PutResponse updateAccount(UpdateAccountRequest updateAccountRequest) {
        AccountEntity accountEntityReal = getAccountById(updateAccountRequest.getAccountId());
        accountEntityReal.setAccountTypeEntity(accountTypeService.findAccountTypeEntityByName(updateAccountRequest.getAccountType()));
        accountEntityReal.setAvailable_balance(updateAccountRequest.getAvailableBalance());
        accountEntityReal.setPending_balance(updateAccountRequest.getPendingBalance());
        AccountEntity updatedAccountEntity = updateAccountEntity(accountEntityReal);
        return PutResponse.builder()
                .success(true)
                .updatedObject(Collections.singletonList(
                        convertAccountEntityToResponse(updatedAccountEntity)))
                .build();
    }

	/**
	 * An AccountEntity object(accountEntityReal) is instantiated by calling the
	 * getAccountById({parameter}.getOwnerAccountId()) method.
	 * 
	 * instantiates another AccountEntity by
	 * getAccountById({parameter}.getReceiverAccountId()) if balance is
	 * insufficient, it'll throw InvalidAmountException.
	 * 
	 * Else, 2 AccountEntity objects are instantiated. The first(aka the sending
	 * AccountEntity) is instantiated by calling the updateAccountEntity() method
	 * which calls the updateAmount() method which contains the sending
	 * AccountEntity object, the transfer amount, and a true boolean(since they are
	 * the sender.
	 * 
	 * The second(aka the receiving AccountEntity) is instantiated by calling the
	 * updateAccountEntity() method which calls the updateAmount() method which
	 * contains the receiving AccountEntity, the incoming balance value, and a false
	 * boolean(since they are the receiving party.
	 * 
	 * Returns a PutResponse that is built containing both the updated AccountEntity objects.
	 * 
	 * @Author Previous Batch
	 * @param
	 * @Return PutResponse
	 */
    @Override
    public PutResponse transferToAnotherAccount(TransferRequest transferRequest) {
        AccountEntity ownerAccountEntity = getAccountById(transferRequest.getOwnerAccountId());
        AccountEntity receiverAccountEntity = getAccountById(transferRequest.getReceiverAccountId());
        if (ownerAccountEntity.getAvailable_balance() <= transferRequest.getAmount())
            throw new InvalidAmountException(HttpStatus.NOT_ACCEPTABLE, "Owner Account: $" + ownerAccountEntity.getAvailable_balance() + " Invalid Requested Amount: $" + transferRequest.getAmount());

        AccountEntity updatedOwner = updateAccountEntity(
                updateAmount(ownerAccountEntity, -transferRequest.getAmount(), true));
        AccountEntity updatedReceiver = updateAccountEntity(
                updateAmount(receiverAccountEntity, transferRequest.getAmount(), false));

        return PutResponse.builder()
                .success(true)
                .updatedObject(convertAccountEntitiesToResponseList(Arrays.asList(updatedOwner, updatedReceiver)))
                .build();
    }

    /**
     * Update the balance of an account entity, it is always assumed that the pending balance is updated but a boolean must be true to update both
     * If money is to be removed then a negative amount must be passed in
     * @param accountEntity The account entity passed in to be updated
     * @param amount The amount needed to be updating by, pass in a negative if money is to be removed
     * @param both The boolean check to update both pending and available balance.
     * @return The account entity with its balances updated as requested
     */
     private AccountEntity updateAmount(AccountEntity accountEntity, float amount, boolean both){
        accountEntity.setPending_balance(accountEntity.getPending_balance() + amount);
        if(both) accountEntity.setAvailable_balance(accountEntity.getAvailable_balance() + amount);
        return accountRepository.save(accountEntity);
    }

	/**
	 * Optional(AccountEntity) object is instantiated by calling the method
	 * accountRepository.findById(accountId parameter). If isPresent() method
	 * returns true (Optional object is not null) then get() method returns the
	 * AccountEntity object. Else, an exception is thrown.
	 * 
	 * @Author Previous Batch
	 * @param accountId
	 * @return
	 */
    private AccountEntity getAccountById(int accountId){
        Optional<AccountEntity> accountEntityOptional = accountRepository.findById(accountId);
        if (accountEntityOptional.isPresent()) return accountEntityOptional.get();
        else throw new EntityNotFoundException();
    }

	/**
	 * Generates a List(Account entity) using
	 * accountRepository.findByLogincredentials() with [new
	 * LoginCredentialEntity(userId, "", "")] as a parameter. If the list was not
	 * populated, then an InvalidUserIdException is thrown. If the list is not null,
	 * then the list is returned.
	 * 
	 * @param userId
	 * @return
	 */
    private List<AccountEntity> getUserAccountsByUserId(int userId){
        List<AccountEntity> accountEntities = accountRepository.findByLogincredentials(new LoginCredentialEntity(userId, "", ""));
        if(accountEntities != null) return accountEntities;
        else throw new InvalidUserIdException(HttpStatus.BAD_REQUEST, "Invalid User Id Provided: " + userId);
    }

	/**
	 * Returns an AccountEntity object after saving it to the DataBase.
	 * 
	 * @Author Previous batch
	 * @param accountEntity
	 * @return AccountEntity
	 */
    private AccountEntity updateAccountEntity(AccountEntity accountEntity){
        return accountRepository.save(accountEntity);
    }

	/**
	 * Instantiates a new List of the same length as the parameter list. Each entry
	 * in the parameter list is run through the method
	 * convertAccountEntityToResponse() and stored in the new list which is then
	 * returned.
	 * 
	 * @Author Previous Batch
	 * @param accountEntities
	 * @return
	 */
    private List<AccountResponseObject> convertAccountEntitiesToResponseList(List<AccountEntity> accountEntities) {
        List<AccountResponseObject> responseObjects = new ArrayList<>(accountEntities.size());
        accountEntities.forEach(acc -> responseObjects.add(convertAccountEntityToResponse(acc)));
        return responseObjects;
    }

	/**
	 * Returns a new AccountResponseObject() containing all Variables held by the
	 * given AccountEntity object.
	 * 
	 * @Author Previous Batch
	 * @param accountEntity
	 * @return
	 */
    private AccountResponseObject convertAccountEntityToResponse(AccountEntity accountEntity) {
        return new AccountResponseObject(
                accountEntity.getPk_account_id(),
                accountEntity.getLogincredentials().getPkUserId(),
                accountEntity.getAccountTypeEntity().getName(),
                accountEntity.getAvailable_balance(),
                accountEntity.getPending_balance()
        );
    }
}
