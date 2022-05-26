package com.revature.thevault.presentation.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
// Is referenced by service.classes.accountservice.java && utility.validation.accountvalidation.java
public class CreateAccountRequest {
    int userId;
    String accountType;
}
