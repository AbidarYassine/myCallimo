package com.rest.ai.myCallimo.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetSupervisorByCodesRequest {
    private List<String> codes;
}
