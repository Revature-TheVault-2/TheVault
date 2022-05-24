package com.revature.thevault.service.classes;

import com.revature.thevault.presentation.controller.ExportPDFController;
import com.revature.thevault.presentation.model.response.builder.GetResponse;
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
    public GetResponse getTransactionHistoryByMonth(Integer accountId, int month, int year) throws FileNotFoundException, MalformedURLException {
        GetResponse deposits = depositService.getAllUserDepositsByMonth(accountId, month, year);
        GetResponse withdrawals = withdrawService.getAllUserWithdrawlsByMonth(accountId, month, year);
        List<TransactionObject> transactionObjects = new ArrayList<>();
        deposits.getGotObject().forEach(deposit -> transactionObjects.add(convertDepositToTransactionObject((DepositResponseObject) deposit)));
        withdrawals.getGotObject().forEach(withdrawal -> transactionObjects.add(convertWithdrawToTransactionObject((WithdrawResponseObject) withdrawal)));
        Comparator<TransactionObject> byDate = Comparator.comparing(TransactionObject::getDate);
        transactionObjects.sort(byDate);
        ExportPDFController.createPDF(transactionObjects); // PDF File Created Here
        return GetResponse.builder()
                .success(true)
                .gotObject(transactionObjects)
                .build();
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
}
