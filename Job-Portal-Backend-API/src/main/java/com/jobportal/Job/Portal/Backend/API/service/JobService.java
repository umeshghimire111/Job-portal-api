package com.jobportal.Job.Portal.Backend.API.service;

import com.jobportal.Job.Portal.Backend.API.dto.JobRequest;
import com.jobportal.Job.Portal.Backend.API.dto.param.SearchParam;
import com.jobportal.Job.Portal.Backend.API.dto.response.ApiResponse;


public interface JobService {
    ApiResponse<?> createJob( JobRequest request);
    ApiResponse<?> getJobs(SearchParam searchParam);
    ApiResponse<?> getJobById(Long id);
}
