package com.jobportal.Job.Portal.Backend.API.service;

import com.jobportal.Job.Portal.Backend.API.dto.UserDto;
import com.jobportal.Job.Portal.Backend.API.dto.response.ApiResponse;

public interface UserService {
    ApiResponse<?> register(UserDto userDto);

}
