package com.jobportal.Job.Portal.Backend.API.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UploadProfilePictureDto {

    @NotNull(message="user name")
    private String name;

    @Email(message="required email ")
    private String email;
}