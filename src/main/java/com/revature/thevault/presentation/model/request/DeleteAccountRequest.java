package com.revature.thevault.presentation.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
// referenced by repository.dao.loginrepository.java
public class DeleteAccountRequest {
    int accountId;
}
