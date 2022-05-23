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
// Is referenced by com.revature.thevault.presentation.controller.AccountProfileController.java && presentation.service.classes.accountprofileservice
public class AccountProfileRequest {
    int profileId;

//    public AccountProfileRequest(int id) {
//    }

    public int getProfileId() {
        return profileId;
    }
}
