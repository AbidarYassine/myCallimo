package com.rest.ai.myCallimo.request;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AppelDto {

    @NotNull
    private Date date;
    @NotBlank
    private String duree;
    @NotBlank
    private String typeAppel;
}
