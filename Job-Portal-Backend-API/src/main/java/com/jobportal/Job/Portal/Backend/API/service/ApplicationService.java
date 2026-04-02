package com.jobportal.Job.Portal.Backend.API.service;

import com.jobportal.Job.Portal.Backend.API.dto.ApplicationRequest;
import com.jobportal.Job.Portal.Backend.API.dto.response.ApiResponse;


public interface ApplicationService {
    ApiResponse<?> apply(ApplicationRequest applicationRequest);

}
