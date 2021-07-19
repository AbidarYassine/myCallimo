package com.rest.ai.myCallimo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminDto extends UserDto {
    private String role = "ADMIN";
    private List<SupervisorDto> supervisors;
}
