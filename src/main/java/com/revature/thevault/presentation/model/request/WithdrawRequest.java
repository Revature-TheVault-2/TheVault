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
<<<<<<< HEAD
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
=======
    String email; //used for later development 

>>>>>>> 64f8b9ae0f6865205fdcbd0304034fcf6c382dd2
}
