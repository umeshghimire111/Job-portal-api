package com.jobportal.Job.Portal.Backend.API.controller;

import com.jobportal.Job.Portal.Backend.API.constant.controller.ApiConstant;
import com.jobportal.Job.Portal.Backend.API.dto.AuthenticateUserRequest;
import com.jobportal.Job.Portal.Backend.API.dto.UserAuthenticationResponse;
import com.jobportal.Job.Portal.Backend.API.dto.response.ApiResponse;
import com.jobportal.Job.Portal.Backend.API.service.UserAuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RequestMapping( ApiConstant.API)
@RestController
@AllArgsConstructor
public class UserAuthenticationController {
    private final UserAuthenticationService userAuthenticationService;

    @PostMapping(ApiConstant.LOGIN)
    public ApiResponse<UserAuthenticationResponse> authenticate(@RequestBody @Valid AuthenticateUserRequest authenticateUserRequest, HttpServletResponse response) {
        return userAuthenticationService.authenticate(authenticateUserRequest, response);
    }

    @PostMapping(ApiConstant.REFRESH_TOKEN+ApiConstant.SLASH+ApiConstant.CREATE)
    public ApiResponse<?> refreshToken(HttpServletRequest request, HttpServletResponse response) {
        return userAuthenticationService.refreshToken(request, response);
    }

    @PostMapping(ApiConstant.AUTH + ApiConstant.SLASH + ApiConstant.LOGOUT)
    public ApiResponse<?> logout(HttpServletRequest request, HttpServletResponse response) {
        return userAuthenticationService.logout(request, response);
    }

}