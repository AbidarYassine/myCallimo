package com.rest.ai.myCallimo.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SupervisorSecteurResponse {

    private SupervisorResponse supervisor;
    /* code secteurs */
    private String code;
}
