package com.rest.ai.myCallimo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnnonceurDto {
    private Integer id;
    private String annonceur_id;
    @NotBlank(message = "Ce champ est obligatoite !!")
    private String name;
    @NotBlank(message = "Ce champ est obligatoite !!")
    private String telephone;
    @NotBlank(message = "Ce champ est obligatoite !!")
    @Email(message = "invalid email ")
    private String email;
    private String address;
}
