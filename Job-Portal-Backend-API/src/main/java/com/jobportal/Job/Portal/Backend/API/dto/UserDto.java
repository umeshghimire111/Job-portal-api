package com.jobportal.Job.Portal.Backend.API.dto;

import com.jobportal.Job.Portal.Backend.API.dto.response.ModelBase;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto extends ModelBase {
    @NotBlank
    private String fullName;

    @Email(message="required email")
    private String email;

    @NotBlank
    private String password;

    @NotBlank
    private String phoneNumber;



}