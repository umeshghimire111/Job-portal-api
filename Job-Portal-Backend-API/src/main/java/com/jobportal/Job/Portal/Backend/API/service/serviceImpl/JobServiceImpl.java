package com.jobportal.Job.Portal.Backend.API.service.serviceImpl;

import com.jobportal.Job.Portal.Backend.API.dto.JobRequest;
import com.jobportal.Job.Portal.Backend.API.dto.param.JobMapper;
import com.jobportal.Job.Portal.Backend.API.dto.param.JobResponse;
import com.jobportal.Job.Portal.Backend.API.dto.param.PageableResponse;
import com.jobportal.Job.Portal.Backend.API.dto.param.SearchParam;
import com.jobportal.Job.Portal.Backend.API.dto.response.ApiResponse;
import com.jobportal.Job.Portal.Backend.API.entity.Job;
import com.jobportal.Job.Portal.Backend.API.repository.JobRepository;
import com.jobportal.Job.Portal.Backend.API.service.JobService;
import com.jobportal.Job.Portal.Backend.API.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class JobServiceImpl implements JobService {

    private final JobRepository jobRepository;
    private final JobMapper jobMapper;

    @Override
    public ApiResponse<?> createJob(JobRequest request) {
        try {
            Job job = new Job();
            job.setJobName(request.getJobName());
            job.setTitle(request.getJobName());
            job.setDescription(request.getDescription());
            job.setLocation(request.getLocation());
            job.setSalary(BigDecimal.valueOf(request.getSalary()));

            jobRepository.save(job);

            log.info("Job created successfully: {}", job.getJobName());
            return ResponseUtil.getSuccessfulApiResponse("Job created successfully");

        } catch (Exception e) {
            log.error("Error creating job: {}", e.getMessage());
            return ResponseUtil.getFailureResponse("Failed to create job");
        }
    }

    @Override
    public ApiResponse<?> getJobs(SearchParam searchParam) {
        try {
            int offset = searchParam.getFirstRow();
            int limit = searchParam.getPageSize();

            List<Job> allJobs = jobRepository.findAll();
            long totalCount = allJobs.size();

            int toIndex = Math.min(offset + limit, allJobs.size());
            List<Job> pagedJobs = (offset > totalCount) ? Collections.emptyList() : allJobs.subList(offset, toIndex);

            List<JobResponse> jobResponses = jobMapper.toJobResponseList(pagedJobs);

            PageableResponse<JobResponse> response = new PageableResponse<>();
            response.setData(jobResponses);
            response.setTotalCount(totalCount);

            return ResponseUtil.getSuccessfulApiResponse(response, "Job list fetched successfully");

        } catch (Exception e) {
            log.error("Error fetching jobs: {}", e.getMessage());
            return ResponseUtil.getFailureResponse("Failed to fetch jobs");
        }
    }

    @Override
    public ApiResponse<?> getJobById(Long id) {
        try {
            Job job = jobRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Job not found with id: " + id));

            log.info("Job fetched successfully: {}", job.getJobName());
            return ResponseUtil.getSuccessfulApiResponse(job, "Job fetched successfully");

        } catch (Exception e) {
            log.error("Error fetching job by id: {}", e.getMessage());
            return ResponseUtil.getFailureResponse(e.getMessage());
        }
    }
}