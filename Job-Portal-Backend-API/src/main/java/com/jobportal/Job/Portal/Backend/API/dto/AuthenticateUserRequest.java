package com.jobportal.Job.Portal.Backend.API.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AuthenticateUserRequest {

    @NotBlank(message="Email is required")
    private String email;

    @NotBlank(message="Password is required")
    private String password;
}

