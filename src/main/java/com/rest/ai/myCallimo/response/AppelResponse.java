package com.rest.ai.myCallimo.response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppelResponse {

    private Integer id;
    private String duree;
    private String typeAppel;
    private String fileName;
    private String fileType;
    private Instant created_at;
    private Instant updated_at;
}
