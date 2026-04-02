package com.jobportal.Job.Portal.Backend.API.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserAuthenticationResponse {
    private String accessToken;
    private String refreshToken;
}