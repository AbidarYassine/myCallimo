package com.rest.ai.myCallimo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;


@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserDto implements Serializable {
    // class for shared info between couch (presentation,service,data layer)
    private static final long serialVersionUID = -8613411897657339469L;
    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Boolean emailVerficationStatus = false;
    private String role;
    private boolean passwordChanged;
    private String telephone;
    private String avatar = "https://cdn.pixabay.com/photo/2020/07/14/13/07/icon-5404125_960_720.png";

}
