package com.jobportal.Job.Portal.Backend.API.dto.param;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class JobResponse {
    private Long id;
    private String jobName;
    private String location;
    private Double salary;
    private LocalDateTime createdAt;
}