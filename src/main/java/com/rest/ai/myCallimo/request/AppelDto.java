package com.rest.ai.myCallimo.request;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AppelDto {
    @NotBlank
    private String duree;
    @NotBlank
    private String typeAppel;
}
