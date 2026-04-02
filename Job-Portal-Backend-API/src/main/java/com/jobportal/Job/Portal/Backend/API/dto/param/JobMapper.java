package com.jobportal.Job.Portal.Backend.API.dto.param;

import com.jobportal.Job.Portal.Backend.API.entity.Job;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JobMapper {

    public JobResponse toJobResponse(Job job) {
        if (job == null) return null;

        return JobResponse.builder()
                .id(job.getId())
                .jobName(job.getJobName())
                .location(job.getLocation())
                .salary(job.getSalary() != null ? job.getSalary().doubleValue() : null)
                .createdAt(job.getCreatedAt())
                .build();
    }

    public List<JobResponse> toJobResponseList(List<Job> jobs) {
        return jobs.stream()
                .map(this::toJobResponse)
                .collect(Collectors.toList());
    }
}