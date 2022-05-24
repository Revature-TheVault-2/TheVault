package com.revature.thevault.service.dto;


import lombok.*;
import lombok.experimental.FieldDefaults;

@EqualsAndHashCode
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LoginCredentialResponse {
    int userId;
}
