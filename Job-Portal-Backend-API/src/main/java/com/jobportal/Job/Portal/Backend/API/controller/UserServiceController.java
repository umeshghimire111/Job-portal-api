package com.jobportal.Job.Portal.Backend.API.controller;

import com.jobportal.Job.Portal.Backend.API.constant.controller.ApiConstant;
import com.jobportal.Job.Portal.Backend.API.dto.UserDto;
import com.jobportal.Job.Portal.Backend.API.dto.response.ApiResponse;
import com.jobportal.Job.Portal.Backend.API.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(ApiConstant.API)
@AllArgsConstructor
@RestController
public class UserServiceController {
    private final UserService userService;

    @PostMapping(ApiConstant.REGISTER)
    public ApiResponse<?> register(@RequestBody @Valid UserDto userDto) {

        return userService.register(userDto);


    }
}