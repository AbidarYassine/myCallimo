package com.rest.ai.myCallimo.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ChangePasswordRequest {
    private String email;
    private String password;
    private String confirmPassword;

}
