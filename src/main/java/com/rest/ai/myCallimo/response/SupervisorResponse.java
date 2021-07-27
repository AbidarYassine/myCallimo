package com.rest.ai.myCallimo.response;

import com.rest.ai.myCallimo.dto.CallerDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SupervisorResponse extends UserResponse {
    private List<CallerResponse> callers;
}
