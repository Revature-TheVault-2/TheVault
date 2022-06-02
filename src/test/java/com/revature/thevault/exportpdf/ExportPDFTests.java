package com.revature.thevault.exportpdf;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.BeforeAll;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.revature.thevault.repository.dao.AccountProfileRepository;
import com.revature.thevault.repository.entity.AccountProfileEntity;
import com.revature.thevault.repository.entity.LoginCredentialEntity;
import com.revature.thevault.service.classes.ExportPDFService;
import com.revature.thevault.service.classes.RandomStringService;
import com.revature.thevault.service.classes.Email.EmailService;
import com.revature.thevault.service.dto.TransactionObject;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ExportPDFTests {
	@Autowired
	private ExportPDFService pdfService;
	@MockBean
	private RandomStringService strServ;
	@MockBean
	private EmailService emailServ;	
	@MockBean
	private AccountProfileRepository profileRepos;
	@MockBean
	private AccountProfileEntity testProfileEntity;
	@MockBean
	private LoginCredentialEntity testLoginCredential;
	
	
	private Optional<AccountProfileEntity> optionalProfile;

	String testRandString;
	
	TransactionObject fakeTransaction;
	TransactionObject fakeTransaction2;
	TransactionObject fakeTransaction3;
	ArrayList<TransactionObject> arrList;
	
	private int month;
	private int year;
	
	private int testProfileID;
	
	private float accountBalance;
	
	@BeforeAll()
		void BeforeSetUp() {
			MockitoAnnotations.openMocks(this);
			testProfileID = 12345;
			accountBalance = 100F;
			
			testLoginCredential = new LoginCredentialEntity();
			testProfileEntity = new AccountProfileEntity(testProfileID, testLoginCredential, "Hugh", "Mann", "fakeemail@iloveunicorns.com", "800-664-1234", "123 Shakedown Street, Detroit, Michigan, 90210", accountBalance);
			
			optionalProfile = Optional.of(testProfileEntity);
			testRandString = "FR07";
			
			month = 12;
			year = 1999;
			
			fakeTransaction = new TransactionObject(1, "Deposit", "Cash", "A1B", LocalDate.parse("1999-12-03"), 200F); // Why is amount a Float though? Why???
			fakeTransaction2 = new TransactionObject(2, "Withdrawal", "Payment", "Something", LocalDate.parse("1999-12-31"), 100F); // Why is amount a Float though? Why???
			fakeTransaction3 = new TransactionObject(3, "Withdrawal", "Payment", "CCC", LocalDate.parse("1999-12-05"), 300F); // Why is amount a Float though? Why???
			
			arrList = new ArrayList<TransactionObject>();
			arrList.add(fakeTransaction);
			arrList.add(fakeTransaction2);
			
		}
	
	@Test
	public void createTest() throws FileNotFoundException, MalformedURLException {
		System.out.println("I exist.");
		Mockito.when(strServ.getSaltString()).thenReturn(testRandString);
		Mockito.when(profileRepos.findById(testProfileID)).thenReturn(optionalProfile);
		
		// Assert
		assertDoesNotThrow(() -> pdfService.createPDF(arrList, month, year, testProfileID, accountBalance), "An exception has occured.");
		// Verify
		verify(emailServ, times(1)).sendReportPdfEmail("src/main/resources/pdf/" + testRandString + "myMonthStatement.pdf", "1999-12-31 to 2000-01-31", "fakeemail@iloveunicorns.com", "Hugh");
		
		File file = new File("src/main/resources/pdf/" + testRandString + "myMonthStatement.pdf");
		file.delete();
	}
	
}
