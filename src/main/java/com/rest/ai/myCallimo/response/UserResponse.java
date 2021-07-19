package com.rest.ai.myCallimo.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserResponse {
    private String firstName;
    private String lastName;
    private String email;
    private String role;
}
