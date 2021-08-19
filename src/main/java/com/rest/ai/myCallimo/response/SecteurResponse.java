package com.rest.ai.myCallimo.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SecteurResponse {
    private Integer id;
    private String libelle;
    private String code;
    private boolean afected;
    private List<CityResponse> cities;

}
