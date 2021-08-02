package com.rest.ai.myCallimo.response;


import com.rest.ai.myCallimo.dto.CityDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CallerResponse extends UserResponse {
    private CityDto city;
}
