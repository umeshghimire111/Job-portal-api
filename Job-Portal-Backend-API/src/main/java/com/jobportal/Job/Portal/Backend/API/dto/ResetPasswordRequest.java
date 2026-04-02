package com.jobportal.Job.Portal.Backend.API.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class ResetPasswordRequest {
    @NotBlank(message="Email is required")
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$", message = "Invalid email format")
    private String email;

    @NotNull
    private String otp;

    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*])[a-zA-Z0-9!@#$%^&*]{4,12}$", message = "password must be min 4 and max 12 length containing at least 1 uppercase, 1 lowercase, 1 special character and 1 digit ")
    private String newPassword;
}
