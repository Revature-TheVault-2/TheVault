package com.revature.thevault.service.classes;

import com.revature.thevault.presentation.controller.ExportPDFController;
import com.revature.thevault.presentation.model.response.builder.GetResponse;
import com.revature.thevault.repository.entity.DepositEntity;
import com.revature.thevault.repository.entity.WithdrawEntity;
import com.revature.thevault.service.dto.AccountResponseObject;
import com.revature.thevault.service.dto.DepositResponseObject;
import com.revature.thevault.service.dto.TransactionObject;
import com.revature.thevault.service.dto.WithdrawResponseObject;
import com.revature.thevault.service.interfaces.TransactionServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;

/**
 * 
 * Transaction Service. This service implementes TransactionServiceInterface
 * It contains getTransactionHistory, convertDepositToTransactionObject, and convertDepositToTransactionObject
 *
 *	
 */
@Service("transactionService")
public class TransactionService implements TransactionServiceInterface {

    @Autowired
    private DepositService depositService;

    @Autowired
    private WithdrawService withdrawService;
    
    @Autowired
    private ExportPDFService exportService;
    
    @Autowired
	private AccountService accServ;

    /**
     * Retrieves a transaction history with the account id as parameter.
     * To create a transaction history all the account deposits are called through deposit service,
     * and all the account withdrawals are called through withdraw service.
     * The list is sorted in descending order by date.
     * 
     * This account info is converted and returned as a ResponseObject to the requesting controllers
     * 
     * @param account id
     * @return GetResponse object
     * 
     */
    @Override
    public GetResponse getTransactionHistory(Integer accountId) {
        GetResponse deposits = depositService.getAllUserDeposits(accountId);
        GetResponse withdrawals = withdrawService.getAllUserWithdrawals(accountId);
        List<TransactionObject> transactionObjects = new ArrayList<>();
        deposits.getGotObject().forEach(deposit -> transactionObjects.add(convertDepositToTransactionObject((DepositResponseObject) deposit)));
        withdrawals.getGotObject().forEach(withdrawal -> transactionObjects.add(convertWithdrawToTransactionObject((WithdrawResponseObject) withdrawal)));
        Comparator<TransactionObject> byDate = Comparator.comparing(TransactionObject::getDate);
        transactionObjects.sort(byDate.reversed());
        return GetResponse.builder()
                .success(true)
                .gotObject(transactionObjects)
                .build();
    }
    
    /**
     * @param accountId
     * @param int month
     * @param int year
     * @author fred
     * @throws MalformedURLException 
     * @throws FileNotFoundException 
     */
    public GetResponse getTransactionHistoryByMonth(Integer accountId, int month, int year, int profileId) throws FileNotFoundException, MalformedURLException {
        GetResponse deposits = depositService.getAllUserDepositsByMonth(accountId, month, year);
        GetResponse withdrawals = withdrawService.getAllUserWithdrawlsByMonth(accountId, month, year);
        List<TransactionObject> transactionObjects = new ArrayList<>();
        deposits.getGotObject().forEach(deposit -> transactionObjects.add(convertDepositToTransactionObject((DepositResponseObject) deposit)));
        withdrawals.getGotObject().forEach(withdrawal -> transactionObjects.add(convertWithdrawToTransactionObject((WithdrawResponseObject) withdrawal)));
        Comparator<TransactionObject> byDate = Comparator.comparing(TransactionObject::getDate);
        transactionObjects.sort(byDate);

        if(!transactionObjects.isEmpty()) {
			float ourPastBalance = calculateMonthBalance(accountId, month, year);
        	exportService.createPDF(transactionObjects, month, year, profileId, ourPastBalance); // PDF File Created Here
        }

        return GetResponse.builder()
                .success(true)
                .gotObject(transactionObjects)
                .build();
    }
    
    /**
     * Gets transactions between the given dates. More direct than getByMonth.
     * @param accountId
     * @param Date start
     * @param Date end
     * @author fred
     * @throws MalformedURLException 
     * @throws FileNotFoundException 
     */
    public List<TransactionObject> getTransactionHistoryBetween(Integer accountId, Date start, Date end) {
        List<DepositEntity> deposits = depositService.getUserDepositsByAccountIdAndDateBetween(accountId, start, end);
        List<WithdrawEntity> withdrawals = withdrawService.findByAccountIdAndDateBetween(accountId, start, end);
        List<TransactionObject> transactionObjects = new ArrayList<>();
        deposits.forEach(deposit -> transactionObjects.add(convertDepositToTransactionObject((DepositEntity) deposit)));
        withdrawals.forEach(withdrawal -> transactionObjects.add(convertWithdrawToTransactionObject((WithdrawEntity) withdrawal)));

        return transactionObjects;
    }
    

    /**
     * 
     * Converts withdrawals into transaction objects
     * 
     * @param withdrawals
     * @return TransactionObject
     */
    private TransactionObject convertWithdrawToTransactionObject(WithdrawResponseObject withdrawals) {
        return new TransactionObject(
                withdrawals.getWithdrawId(),
                "Withdraw",
                withdrawals.getRequestType(),
                withdrawals.getReference(),
                withdrawals.getDateWithdraw(),
                withdrawals.getAmount()
        );
    }

    /**
     * 
     * Converts deposits into transaction objects
     * 
     * @param deposit
     * @return TransactionObject
     */
    private TransactionObject convertDepositToTransactionObject(DepositResponseObject deposit) {
        return new TransactionObject(
                deposit.getDepositId(),
                "Deposit",
                deposit.getDepositType(),
                deposit.getReference(),
                deposit.getDateDeposit(),
                deposit.getAmount()
        );
    }
    
    /**
     * 
     * Converts withdrawal ENTITIES into transaction objects
     * 
     * @param withdrawalEntity
     * @return TransactionObject
     * @author Fred
     */
    private TransactionObject convertWithdrawToTransactionObject(WithdrawEntity withdrawal) {
        return new TransactionObject(
                withdrawal.getPk_withdraw_id(),
                "Withdraw",
                withdrawal.getRequesttypeentity().getName(),
                withdrawal.getReference(),
                withdrawal.getDateWithdraw().toLocalDate(),
                withdrawal.getAmount()
        );
    }

    /**
     * 
     * Converts deposit ENTITIES into transaction objects
     * 
     * @param depositEntity
     * @return TransactionObject
     * @author Fred
     */
    private TransactionObject convertDepositToTransactionObject(DepositEntity deposit) {
        return new TransactionObject(
                deposit.getPk_deposit_id(),
                "Deposit",
                deposit.getDeposittypeentity().getName(),
                deposit.getReference(),
                deposit.getDateDeposit().toLocalDate(),
                deposit.getAmount()
        );
    }
    
    /**
	 * Takes the given starting month and year, and "rewinds" current balance to that date.
	 * @param accountId
	 * @param month
	 * @param year
	 * @author Frederick (whose name is NOT a typo)
	 * @return
	 */
	private float calculateMonthBalance(int accountId, int month, int year) {
		Calendar cal = Calendar.getInstance();
		cal.set(year, month, 1);
    	cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DATE));
    	Date endDate = new Date(cal.getTimeInMillis());
    	cal.set(year, month-1, 1);
    	cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DATE));
    	Date startDate = new Date(cal.getTimeInMillis());
		
		// Get the CURRENT balance
    	GetResponse accGetResp = accServ.getAccount(accountId);
    	List<AccountResponseObject> accResp = accGetResp.getGotObject();
    	float currentBal = accResp.get(0).getAvailableBalance();
    	float pastBal = currentBal;
		
		// Get the transactions from the given month to the CURRENT date
    	List<TransactionObject> tranList = getTransactionHistoryBetween(accountId, startDate, endDate);
    	
    	// Calculate what the past balance was
    	for(TransactionObject t : tranList) {
    		if(t.getTransactionType().equals("Deposit")) {
    			// Note that deposit SUBTRACTS. We are UNDOING from today to the past.
    			pastBal = pastBal - t.getAmount();
    		} else if(t.getTransactionType().equals("Withdraw")) {
    			// Note that withdraw ADDS. We are UNDOING from today to the past.
    			pastBal = pastBal + t.getAmount();
    		} else {
    			// this is not a deposit nor withdraw!?
    			System.out.println("Bro, what is this transaction type!?");
    		}
    	}
    	System.out.println("---------CALCULATE PAST BALANCE TEST PRINTOUT--------");
    	System.out.println("CalculateMonthBalanceService: This is our current balance! " + currentBal);
    	System.out.println("CalculateMonthBalanceService: This is our past balance! " + pastBal);
    	System.out.println("From the dates " + startDate + " to " + endDate);
		return pastBal;
	}
}
