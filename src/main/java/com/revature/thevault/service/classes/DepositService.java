package com.revature.thevault.service.classes;

import com.revature.thevault.presentation.model.request.DepositRequest;
import com.revature.thevault.presentation.model.response.builder.DeleteResponse;
import com.revature.thevault.presentation.model.response.builder.GetResponse;
import com.revature.thevault.presentation.model.response.builder.PostResponse;
import com.revature.thevault.repository.dao.DepositRepository;
import com.revature.thevault.repository.entity.*;
import com.revature.thevault.service.dto.DepositResponseObject;
import com.revature.thevault.service.exceptions.InvalidAccountIdException;
import com.revature.thevault.service.exceptions.InvalidDepositIdException;
import com.revature.thevault.service.exceptions.InvalidRequestException;
import com.revature.thevault.service.interfaces.DepositServiceInterface;

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

/**
 * @author chris & fred & david Service layer for deposit repository
 *         Dependencies: DepositRepository, DepositTypeService,
 */
@Service("depositService")
public class DepositService implements DepositServiceInterface {

	@Autowired
	private DepositRepository depositRepository;

	@Autowired
	private DepositTypeService depositTypeService;

	/**
	 * Saves new deposit entity into the deposit repository and then converts to a
	 * PostResponse return.
	 * 
	 * @param DepositRequest depositRequest
	 * @return PostResponse containing single deposit entity in a list (singleton).
	 *         TODO: possibly refactor DepositEntity constructor variables for
	 *         readability TODO: create a deposit factory for the .builder()
	 */
	@Override
	public PostResponse createDeposit(DepositRequest depositRequest) {
		try {
			//outer if (notificationAmount() != 0){
			//if(depositRequest.getAmount() > "current user session".getNoficationAmount()){
				// emailservice.NotifcationEmail(deposistRequest.getAmount())}}
			
			return PostResponse.builder().success(true)
					.createdObject(Collections
							.singletonList(convertDepositEntityToResponse(depositRepository.save(new DepositEntity(0,
									new AccountEntity(depositRequest.getAccountId(), new LoginCredentialEntity(),
											new AccountTypeEntity(), 0, 0),
									depositTypeService.findDepositTypeEntityByName(depositRequest.getDepositType()),
									depositRequest.getReference(), Date.valueOf(LocalDate.now()),
									depositRequest.getAmount())))))
					.build();
		} catch (Exception e) {
			throw new InvalidRequestException(HttpStatus.BAD_REQUEST, "Bad request");
		}
	}

	/**
	 * Gets all deposit for the given user.
	 * 
	 * @param int accountId
	 * @return GetResponse containing List of deposit response objects.
	 */
	@Override
	public GetResponse getAllUserDeposits(int accountId) {
		try {
			List<DepositEntity> depositEntities = getUserDepositsByAccountId(accountId);
			return GetResponse.builder().success(true).gotObject(convertDepositEntitiesToResponseList(depositEntities))
					.build();
		} catch (InvalidAccountIdException e) {
			throw e;
		} catch (EntityNotFoundException e) {
			throw new InvalidRequestException(HttpStatus.BAD_REQUEST, "Deposits not Found for Account: " + accountId);
		} catch (Exception e) {
			throw new InvalidRequestException(HttpStatus.BAD_REQUEST, "Invalid Request");
		}
	}
	
	/**
	 * Get user deposits for a given month and year.
	 * @param accountId
	 * @param month
	 * @param year
	 * @return GetResponse containing a list of depositResponses
	 */
	public GetResponse getAllUserDepositsByMonth(int accountId, int month, int year) {
		Calendar cal = Calendar.getInstance();
    	cal.set(year, month-1, 1);
    	cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DATE));
    	Date startDate = new Date(cal.getTimeInMillis());
    	cal.add(Calendar.MONTH, 1);
    	cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DATE));
    	Date endDate = new Date(cal.getTimeInMillis());
		try {
			List<DepositEntity> depositEntities = getUserDepositsByAccountIdAndDateBetween(accountId, startDate, endDate);
			return GetResponse.builder().success(true).gotObject(convertDepositEntitiesToResponseList(depositEntities))
					.build();
		} catch (InvalidAccountIdException e) {
			throw e;
		}
	}

	/**
	 * Gets all of a user's deposits of a specific type.
	 * 
	 * @param int    accountId
	 * @param String depositType
	 * @return GetResponse containing List of deposit response objects.
	 */
	@Override
	public GetResponse getAlLUserDepositsOfType(int accountId, String depositType) {
		try {
			List<DepositEntity> depositEntities = getUserDepositsByAccountIdAndType(accountId,
					depositTypeService.findDepositTypeEntityByName(depositType));
			return GetResponse.builder().success(true).gotObject(convertDepositEntitiesToResponseList(depositEntities))
					.build();
		} catch (Exception e) {
			throw new InvalidRequestException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	/**
	 * Finds a deposit by its id number.
	 * 
	 * @param int depositId
	 * @return GetResponse containing singleton list for deposit entity.
	 */
	@Override
	public GetResponse findByDepositId(int depositId) {
		try {
			Optional<DepositEntity> depositEntityOptional = depositRepository.findById(depositId);
			if (depositEntityOptional.isPresent())
				return GetResponse.builder().success(true)
						.gotObject(
								Collections.singletonList(convertDepositEntityToResponse(depositEntityOptional.get())))
						.build();
			else
				throw new InvalidDepositIdException(HttpStatus.BAD_REQUEST, "Deposit not found: " + depositId);
		} catch (Exception e) {
			throw new InvalidRequestException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}

	/**
	 * Deletes all deposits by accountId.
	 * 
	 * @param Integer accountId
	 * @return DeleteResponse
	 */
	@Override
	public DeleteResponse deleteAllDeposits(Integer accountId) {
		try {
			depositRepository.deleteByAccountentity(
					new AccountEntity(accountId, new LoginCredentialEntity(), new AccountTypeEntity(), 0, 0));
			return DeleteResponse.builder().success(true).deletedObject(Collections.EMPTY_LIST).build();
		} catch (Exception e) {
			throw new InvalidRequestException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}
	
	/**
	 * Gets all deposits by user's account id
	 * @param int accountId
	 * @return List<DeposityEntity> 
	 */
	private List<DepositEntity> getUserDepositsByAccountId(int accountId) {
		return depositRepository.findByAccountentity(
				new AccountEntity(accountId, new LoginCredentialEntity(), new AccountTypeEntity(), 0, 0));
	}

	/**
	 * Gets all deposits by user's account id and account type.
	 * 
	 * @param int               accountId
	 * @param DepositTypeEntity depositTypeEntity
	 * @return List<DeposityEntity>
	 */
	private List<DepositEntity> getUserDepositsByAccountIdAndType(int accountId, DepositTypeEntity depositTypeEntity) {
		return depositRepository.findByAccountentityAndDeposittypeentity(
				new AccountEntity(accountId, new LoginCredentialEntity(), new AccountTypeEntity(), 0, 0),
				depositTypeEntity);
	}

	/**
<<<<<<< HEAD
	 * Gets all deposits by user's account id.
=======
	 * Gets all deposits by user's account id and between given dates.
>>>>>>> 38f11cde1cbe751113059a7ef0020f8835f59afb
	 * 
	 * @param int accountId
	 * @param startDate
	 * @param endDate
	 * @return List<DepositEntity>
	 * @author Frederick
	 */
	public List<DepositEntity> getUserDepositsByAccountIdAndDateBetween(int accountId, Date startDate, Date endDate) {
		return depositRepository.findByAccountIdAndDatesBetween(accountId, startDate, endDate);
	}

	/**
	 * Helper method to convert DepositEntity object to DepositResponseObject.
	 * 
	 * @param DepositEntity depositEntity
	 * @return DepositResponseObject
	 */
	private DepositResponseObject convertDepositEntityToResponse(DepositEntity depositEntity) {
		return new DepositResponseObject(depositEntity.getPk_deposit_id(),
				depositEntity.getAccountentity().getPk_account_id(), depositEntity.getDeposittypeentity().getName(),
				depositEntity.getReference(), depositEntity.getDateDeposit().toLocalDate(), depositEntity.getAmount());
	}

	/**
	 * Helper method to convert a list of DepositEntity objects to a list of
	 * DepositResponseObject.
	 * 
	 * @param List<DepositEntity> depositEntities
	 * @return List<DepositResponseObject>
	 */
	private List<DepositResponseObject> convertDepositEntitiesToResponseList(List<DepositEntity> depositEntities) {
		List<DepositResponseObject> responseObjects = new ArrayList<>(depositEntities.size());
		depositEntities.forEach(acc -> responseObjects.add(convertDepositEntityToResponse(acc)));
		return responseObjects;
	}
}
