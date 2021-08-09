package com.rest.ai.myCallimo.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SecteurResponse {

    private Integer id;

    private String libelle;
    private String code;
//    private List<CityDto> cities;
}
