package com.jobportal.Job.Portal.Backend.API.dto;

import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class ApplicationRequest {

    private String jobName;

    @Email(message = "Invalid email format")
    private String email;

    private MultipartFile resume;
}
