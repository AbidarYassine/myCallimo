package com.rest.ai.myCallimo.request;

import com.rest.ai.myCallimo.validation.ValidationListInteger;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetByIdsRequest implements Serializable {

    @ValidationListInteger
    private List<Integer> ids;
}
