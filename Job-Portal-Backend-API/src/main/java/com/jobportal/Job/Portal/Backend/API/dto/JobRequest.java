package com.jobportal.Job.Portal.Backend.API.dto;

import lombok.Data;

@Data
public class JobRequest {

private  String jobName;

    private String title;

    private String description;

    private String location;

    private Double salary;

}