package com.revature.thevault.service.classes;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
class PwResetTokenServiceTest {
	
	
	PwResetTokenService tPwResetTokenService;
	
	PwResetTokenService mockResetService = mock(PwResetTokenService.class);

	@BeforeEach
	void setUpBeforeClass() throws Exception {
		tPwResetTokenService = new PwResetTokenService();
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void generateResetTokenTest() {
		
		String tokenString1 =  tPwResetTokenService.generateResetToken(2);
		String tokenString2 =  mockResetService.generateResetToken(2);
		when(mockResetService.generateResetToken(2)).thenReturn(tokenString2);
		
		assertNotNull(mockResetService, "the token must be random but not null");
		assertNotSame(tokenString2, tokenString1, "the tokens are NOT the same even for same id");
		assertNotEquals(tokenString1, tokenString2, "the tokens are different even for same id");
		
		verify(mockResetService, times(1)).generateResetToken(2);
		
		
	}

}