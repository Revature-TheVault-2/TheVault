package com.revature.thevault.service.classes;

import com.itextpdf.layout.element.List;
import com.revature.thevault.presentation.model.request.WithdrawRequest;
import com.revature.thevault.presentation.model.response.builder.DeleteResponse;
import com.revature.thevault.presentation.model.response.builder.GetResponse;
import com.revature.thevault.presentation.model.response.builder.PostResponse;
import com.revature.thevault.repository.dao.AccountProfileRepository;
import com.revature.thevault.repository.dao.AccountRepository;
import com.revature.thevault.repository.dao.WithdrawRepository;
import com.revature.thevault.repository.entity.*;
import com.revature.thevault.service.dto.WithdrawResponseObject;
import com.revature.thevault.service.exceptions.InvalidAmountException;
import com.revature.thevault.service.exceptions.InvalidRequestException;
import com.revature.thevault.service.exceptions.InvalidWithdrawIdRequest;

import org.junit.Ignore;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;

@Ignore
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)

class WithdrawServiceTest {

    @Autowired
    private WithdrawService withdrawService;

    @MockBean
    private WithdrawRepository withdrawRepository;

    @MockBean
    private RequestTypeService requestTypeService;

    @MockBean
    private RequestStatusService requestStatusService;
    
    @MockBean
	private AccountRepository accountRepository;
    
    @MockBean
    private AccountProfileRepository accountProfileRepository;
    
//    @MockBean
//    private EmailService emailService;

    private int accountId;
    private String reference;
    private float amount;

    private WithdrawEntity storedWithdrawEntity, storedWithdrawEntity2, storedWithdrawEntity3;
    private AccountEntity accountEntity;
    private WithdrawResponseObject withdrawResponseObject, withdrawResponseObject2, withdrawResponseObject3;

    private RequestTypeEntity requestType;
    private RequestStatusEntity requestStatusEntity;
    private Date dateStored, dateStored2, dateStored3;
    private String email;

    private LoginCredentialEntity loginCredentialEntity;
    private AccountTypeEntity accountTypeEntity;
    private AccountProfileEntity accountProfileEntity;

    @BeforeAll
    void setup(){
        MockitoAnnotations.openMocks(this);
        accountId = 1;
        reference = "reference";
        amount = 12.22F;
        loginCredentialEntity = new LoginCredentialEntity(1, "username", "password");
        accountTypeEntity = new AccountTypeEntity(1, "Checking");

        requestType = new RequestTypeEntity(1, "Retail");
        requestStatusEntity = new RequestStatusEntity(1, "Pending");
        
        accountProfileEntity = new AccountProfileEntity(
        		accountId, loginCredentialEntity, "firstName", "lastName", 
        		"someEmail@email.com", "12312341234", "Personal Drive", amount);
    }

    @BeforeEach
    void setupBeforeEach(){
        dateStored = Date.valueOf(LocalDate.now());
        dateStored2 = Date.valueOf("2022-04-30");
        dateStored3 = Date.valueOf("2022-05-31");
        accountEntity = new AccountEntity(
                accountId,
                loginCredentialEntity,
                accountTypeEntity,
                100F,
                100F
        );
        storedWithdrawEntity = new WithdrawEntity(
                1,
                accountEntity,
                requestType,
                requestStatusEntity,
                reference,
                dateStored,
                amount,
                email
        );
        storedWithdrawEntity2 = new WithdrawEntity(
                2,
                accountEntity,
                requestType,
                requestStatusEntity,
                reference,
                dateStored2,
                amount, email
        );
        storedWithdrawEntity3 = new WithdrawEntity(
                3,
                accountEntity,
                requestType,
                requestStatusEntity,
                reference,
                dateStored3,
                amount, email
        );
        withdrawResponseObject = new WithdrawResponseObject(
                storedWithdrawEntity.getPk_withdraw_id(),
                storedWithdrawEntity.getAccountentity().getPk_account_id(),
                storedWithdrawEntity.getRequesttypeentity().getName(),
                storedWithdrawEntity.getRequeststatusentity().getName(),
                storedWithdrawEntity.getReference(),
                storedWithdrawEntity.getDateWithdraw().toLocalDate(),
                storedWithdrawEntity.getAmount()
        );
        withdrawResponseObject2 = new WithdrawResponseObject(
                storedWithdrawEntity2.getPk_withdraw_id(),
                storedWithdrawEntity2.getAccountentity().getPk_account_id(),
                storedWithdrawEntity2.getRequesttypeentity().getName(),
                storedWithdrawEntity2.getRequeststatusentity().getName(),
                storedWithdrawEntity2.getReference(),
                storedWithdrawEntity2.getDateWithdraw().toLocalDate(),
                storedWithdrawEntity2.getAmount()
        );
        withdrawResponseObject3 = new WithdrawResponseObject(
                storedWithdrawEntity3.getPk_withdraw_id(),
                storedWithdrawEntity3.getAccountentity().getPk_account_id(),
                storedWithdrawEntity3.getRequesttypeentity().getName(),
                storedWithdrawEntity3.getRequeststatusentity().getName(),
                storedWithdrawEntity3.getReference(),
                storedWithdrawEntity3.getDateWithdraw().toLocalDate(),
                storedWithdrawEntity3.getAmount()
        );
        Mockito.when(requestTypeService.getRequestTypeByName("Retail")).thenReturn(requestType);
        Mockito.when(requestStatusService.getRequestStatusByName("Pending")).thenReturn(requestStatusEntity);
        Mockito.when(accountProfileRepository.findByLogincredential(loginCredentialEntity)).thenReturn(accountProfileEntity);
        Mockito.when(accountRepository.findById(anyInt())).thenReturn(Optional.of(accountEntity));
    }

    @Test
    void createWithdrawal() {
        WithdrawRequest createWithdrawRequest = new WithdrawRequest(
                accountId,
                storedWithdrawEntity.getRequesttypeentity().getName(),
                reference,
                amount,
                email
        );

        PostResponse createdWithdrawResponse = PostResponse.builder()
                .success(true)
                        .createdObject(Collections.singletonList(withdrawResponseObject))
                .build();

        WithdrawEntity saveWithdraw = new WithdrawEntity(
                0,
                new AccountEntity(createWithdrawRequest.getAccountId(), new LoginCredentialEntity(), new AccountTypeEntity(), 0, 0),
                requestType,
                requestStatusEntity,
                createWithdrawRequest.getReference(),
                dateStored,
                createWithdrawRequest.getAmount(),
                createWithdrawRequest.getEmail()
        );
        Mockito.when(withdrawRepository.save(saveWithdraw)).thenReturn(storedWithdrawEntity);
        assertEquals(createdWithdrawResponse, withdrawService.createWithdrawal(createWithdrawRequest));
    }

    @ParameterizedTest
    @ValueSource(floats = {0F, -1F, -2123F})
    void createWithdrawInvalidAmountException(float number){
        WithdrawRequest invalidRequest = new WithdrawRequest(
                accountId,
                requestType.getName(),
                reference,
                number,
                email
        );
        assertThrows(InvalidAmountException.class, () -> withdrawService.createWithdrawal(invalidRequest));
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"asdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasf", "a"})
    void createWithdrawInvalidRequestType(String string){
        WithdrawRequest invalidRequest = new WithdrawRequest(
                accountId,
                string,
                reference,
                1F,
                email
        );
        assertThrows(InvalidRequestException.class, () -> withdrawService.createWithdrawal(invalidRequest));
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"asdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasdfasf", "a"})
    void createWithdrawInvalidReference(String string){
        WithdrawRequest invalidRequest = new WithdrawRequest(
                accountId,
                requestType.getName(),
                string,
                1F,
                email
        );
        assertThrows(InvalidRequestException.class, () -> withdrawService.createWithdrawal(invalidRequest));
    }

    @Test
    void FindByWithdrawIdInvalidWithdrawIdException(){
        Mockito.when(withdrawRepository.findById(-1)).thenReturn(Optional.empty());
        assertThrows(InvalidWithdrawIdRequest.class, () -> withdrawService.findByWithdrawId(-1));
    }

    @Test
    void getAllUserWithdrawals() {
        GetResponse getWithdrawsResponse = GetResponse.builder()
                        .success(true)
                                .gotObject(Collections.singletonList(withdrawResponseObject))
                                        .build();
        Mockito.when(withdrawRepository.findByAccountentity(
                new AccountEntity(
                        accountId,
                        new LoginCredentialEntity(),
                        new AccountTypeEntity(),
                        0,
                        0))).thenReturn(Collections.singletonList(storedWithdrawEntity));
        assertEquals(getWithdrawsResponse, withdrawService.getAllUserWithdrawals(accountId));
    }
    
    @Test
    void getAllUserWithdrawlsByMonth() {
    	GetResponse getWithdrawsResponse = GetResponse.builder()
                .success(true)
                        .gotObject(Arrays.asList(withdrawResponseObject2, withdrawResponseObject3))
                                .build();
				Mockito.when(withdrawRepository.findByAccountIdAndDatesBetween(anyInt(), anyString(), anyString()))
				.thenReturn(Arrays.asList(storedWithdrawEntity2, storedWithdrawEntity3));
				assertEquals(getWithdrawsResponse, withdrawService.getAllUserWithdrawlsByMonth(accountId, 4, 2022));
    }

    @Test
    void getAlLUserWithdrawalsOfType() {
        GetResponse getWithdrawsResponse = GetResponse.builder()
                .success(true)
                .gotObject(Collections.singletonList(withdrawResponseObject))
                .build();

        Mockito.when(withdrawRepository.findByAccountentityAndRequesttypeentity(
                new AccountEntity(
                        accountId,
                        new LoginCredentialEntity(),
                        new AccountTypeEntity(),
                        0,
                        0),
                requestType))
                .thenReturn(Collections.singletonList(storedWithdrawEntity));

        assertEquals(getWithdrawsResponse, withdrawService.getAlLUserWithdrawalsOfType(accountId, requestType.getName()));
    }

    @Test
    void findByWithdrawId() {
        GetResponse getWithdrawsResponse = GetResponse.builder()
                .success(true)
                .gotObject(Collections.singletonList(withdrawResponseObject))
                .build();
        Mockito.when(withdrawRepository.findById(storedWithdrawEntity.getPk_withdraw_id())).thenReturn(Optional.of(storedWithdrawEntity));
        assertEquals(getWithdrawsResponse, withdrawService.findByWithdrawId(storedWithdrawEntity.getPk_withdraw_id()));
    }

    @Test
    void deleteAllWithdraws() {
        DeleteResponse deleteAllWithdrawResponse = DeleteResponse.builder()
                        .success(true)
                                .deletedObject(Collections.EMPTY_LIST)
                                        .build();
        Mockito.doNothing().when(withdrawRepository).deleteByAccountentity(
                new AccountEntity(
                    accountId,
                    new LoginCredentialEntity(),
                    new AccountTypeEntity(),
                    0,
                    0
                ));
        assertEquals(deleteAllWithdrawResponse, withdrawService.deleteAllWithdraws(accountId));
    }
}