package com.rest.ai.myCallimo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserDto implements Serializable {
    // class for shared info between couch (presentation,service,data layer)
    private static final long serialVersionUID = -8613411897657339469L;
    private Integer id;
    @NotBlank()
    private String firstName;
    @NotBlank()
    private String lastName;
    private List<CallerDto> callers;
    @NotBlank()
    private String email;
    @NotBlank()
    private String password;
    private Boolean emailVerficationStatus = false;
    private String role;
    private boolean passwordChanged;
    @NotBlank()
    private String telephone;
    private String avatar = "https://cdn.pixabay.com/photo/2020/07/14/13/07/icon-5404125_960_720.png";

}
