package com.rest.ai.myCallimo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;


@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserDto implements Serializable {
    // class for shared info between couch (presentation,service,data layer)
    private static final long serialVersionUID = -8613411897657339469L;
    private Integer id;
    @NotBlank(message = "prénom est obligatoire !!")
    private String firstName;
    @NotBlank(message = "nom est obligatoire !!")
    private String lastName;
//    private List<CallerDto> callers;
    @NotBlank()
    @Email(message = "mot de passe invalid !!")
    private String email;
    @NotBlank()
    @Size(min = 8, max = 16)
    private String password;
    private Boolean emailVerficationStatus = false;
    private String role;
    private boolean passwordChanged;
    @NotBlank()
    private String telephone;
    private String avatar = "https://cdn.pixabay.com/photo/2020/07/14/13/07/icon-5404125_960_720.png";

}
