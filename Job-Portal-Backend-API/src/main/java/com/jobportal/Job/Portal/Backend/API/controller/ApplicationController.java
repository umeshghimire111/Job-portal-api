package com.jobportal.Job.Portal.Backend.API.controller;

import com.jobportal.Job.Portal.Backend.API.constant.controller.ApiConstant;
import com.jobportal.Job.Portal.Backend.API.dto.ApplicationRequest;
import com.jobportal.Job.Portal.Backend.API.dto.response.ApiResponse;
import com.jobportal.Job.Portal.Backend.API.service.ApplicationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(ApiConstant.API + ApiConstant.SLASH + ApiConstant.APPLICATIONS)
@RequiredArgsConstructor
@Slf4j
public class ApplicationController {

    private final ApplicationService applicationService;

    @PostMapping(value = ApiConstant.APPLY, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<?> apply(
            @RequestPart("jobName") String jobName,
            @RequestPart("email") String email,
            @RequestPart("resume") MultipartFile resume) {

        log.info("Controller Started");
        ApplicationRequest request = new ApplicationRequest();
        request.setJobName(jobName);
        request.setEmail(email);
        request.setResume(resume);

        return applicationService.apply(request);
    }
}