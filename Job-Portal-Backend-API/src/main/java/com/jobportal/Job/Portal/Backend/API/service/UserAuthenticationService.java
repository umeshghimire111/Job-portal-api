package com.jobportal.Job.Portal.Backend.API.service;

import com.jobportal.Job.Portal.Backend.API.dto.response.ApiResponse;
import com.jobportal.Job.Portal.Backend.API.dto.AuthenticateUserRequest;
import com.jobportal.Job.Portal.Backend.API.dto.UserAuthenticationResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface UserAuthenticationService {
    ApiResponse<UserAuthenticationResponse> authenticate(AuthenticateUserRequest authenticateUserRequest, HttpServletResponse response);
    ApiResponse<?> refreshToken(HttpServletRequest request, HttpServletResponse response);
    ApiResponse<?> logout(HttpServletRequest request, HttpServletResponse response);


}
