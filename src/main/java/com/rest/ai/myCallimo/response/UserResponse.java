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
    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private String role;
    private boolean passwordChanged;
    private String telephone;
    private String avatar;
}
