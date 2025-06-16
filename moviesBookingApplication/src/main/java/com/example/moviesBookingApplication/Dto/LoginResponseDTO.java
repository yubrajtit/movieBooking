package com.example.moviesBookingApplication.Dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponseDTO {

    private String jwtToken;
    private String username;
    private String roles;
}
