package com.jobportal.Job.Portal.Backend.API.service;

import com.jobportal.Job.Portal.Backend.API.dto.ForgetPasswordDto;
import com.jobportal.Job.Portal.Backend.API.dto.ResetPasswordRequest;
import com.jobportal.Job.Portal.Backend.API.dto.response.ApiResponse;

public interface ForgetPasswordService {

    ApiResponse<?> sendOtp(ForgetPasswordDto dto);
    ApiResponse<?> resetPassword(ResetPasswordRequest request);
}
