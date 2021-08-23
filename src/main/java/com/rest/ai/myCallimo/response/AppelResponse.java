package com.rest.ai.myCallimo.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppelResponse {

    private Integer id;
    private Date date;
    private String duree;
    private String typeAppel;
    private CallerResponse caller;
}
