package com.rest.ai.myCallimo.response;


import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class JwtResponse {
    private String token;
    private String type = "Bearer";
    UserResponse user;
}
