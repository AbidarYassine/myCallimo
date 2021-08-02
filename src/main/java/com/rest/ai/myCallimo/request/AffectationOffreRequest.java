package com.rest.ai.myCallimo.request;

import com.rest.ai.myCallimo.validation.ValidationListInteger;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AffectationOffreRequest {

    @ValidationListInteger
    private List<Integer> offres_ids;
    @NotNull(message = "superviseur id est obligatoire")
    private Integer supervisor_id;
}
