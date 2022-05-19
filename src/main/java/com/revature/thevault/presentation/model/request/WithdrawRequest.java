package com.revature.thevault.presentation.model.request;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class WithdrawRequest {
    int accountId;
    String requestType;
    //request status is default pending
    String reference;
    float amount;
    public int getAccountId() {
        return 0;
    }
    public String getRequestType() {
        return null;
    }
    public Object getReference() {
        return null;
    }
    public Object getAmount() {
        return null;
    }
}
