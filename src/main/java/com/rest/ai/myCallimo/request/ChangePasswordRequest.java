package com.rest.ai.myCallimo.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ChangePasswordRequest {
    @Email(message = "mot de passe invalid !!")
    @NotBlank(message = "Ce champ est obligatoire !!")
    private String email;

    @NotBlank(message = "mot de passe obligatoire !!")
    private String password;
    @NotBlank(message = "mot de passe obligatoire !!")
    @Size(min = 8, max = 16)
    private String confirmPassword;

}
