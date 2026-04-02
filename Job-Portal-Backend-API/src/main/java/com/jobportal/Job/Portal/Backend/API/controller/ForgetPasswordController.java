package com.jobportal.Job.Portal.Backend.API.controller;


import com.jobportal.Job.Portal.Backend.API.constant.controller.ApiConstant;
import com.jobportal.Job.Portal.Backend.API.dto.ForgetPasswordDto;
import com.jobportal.Job.Portal.Backend.API.dto.ResetPasswordRequest;
import com.jobportal.Job.Portal.Backend.API.dto.response.ApiResponse;
import com.jobportal.Job.Portal.Backend.API.service.ForgetPasswordService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ApiConstant.API + ApiConstant.SLASH+ApiConstant.FORGET_PASSWORD)
@RequiredArgsConstructor
public class ForgetPasswordController {

    private final ForgetPasswordService forgetPasswordService;

    @PostMapping(ApiConstant.SEND_OTP)
    public ApiResponse<?> sendOtp(@RequestBody @Valid ForgetPasswordDto email) {
        return forgetPasswordService.sendOtp(email);
    }

    @PostMapping(ApiConstant.RESET_PASSWORD)
    public ApiResponse<?> resetPassword(@RequestBody @Valid ResetPasswordRequest request) {

        return forgetPasswordService.resetPassword(request);
    }
}