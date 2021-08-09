package com.rest.ai.myCallimo.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CityResponse {
    private Integer id;
    private String name;
    private String code_postal;
}
