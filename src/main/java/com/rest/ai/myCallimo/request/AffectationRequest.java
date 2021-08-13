package com.rest.ai.myCallimo.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AffectationRequest {

    @NotNull
    private List<Integer> ids;
    @NotNull(message = "id est obligatoire")
    private Integer id;
}
