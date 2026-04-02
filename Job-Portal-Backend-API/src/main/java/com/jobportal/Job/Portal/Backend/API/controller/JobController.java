package com.jobportal.Job.Portal.Backend.API.controller;


import com.jobportal.Job.Portal.Backend.API.constant.controller.ApiConstant;
import com.jobportal.Job.Portal.Backend.API.dto.JobRequest;
import com.jobportal.Job.Portal.Backend.API.dto.param.SearchParam;
import com.jobportal.Job.Portal.Backend.API.dto.response.ApiResponse;
import com.jobportal.Job.Portal.Backend.API.service.JobService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(ApiConstant.API + ApiConstant.SLASH + ApiConstant.JOBS)
public class JobController {

    private final JobService jobService;

    @PostMapping(ApiConstant.CREATE)
    public ApiResponse<?> createJob(@RequestBody JobRequest request){
        return jobService.createJob(request);
    }

    @PostMapping(ApiConstant.LIST)
    public ApiResponse<?> getJobs(SearchParam searchParam){
        return jobService.getJobs(searchParam);
    }
}