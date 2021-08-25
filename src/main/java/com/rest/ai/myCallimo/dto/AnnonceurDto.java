package com.rest.ai.myCallimo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnnonceurDto {
    private Integer id;
    private String annonceur_id;
    private String name;
    private String telephone;
    private String email;
    private String address;
}
