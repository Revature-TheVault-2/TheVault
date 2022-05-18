package com.revature.thevault.presentation.model.request;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
// referenced by  service.classes.depositservice.java
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DepositRequest {
    
	String depositType;
    int accountId;
    String reference;
    float amount;
}
