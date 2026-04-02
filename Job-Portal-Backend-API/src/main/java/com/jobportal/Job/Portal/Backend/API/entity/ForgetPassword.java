package com.jobportal.Job.Portal.Backend.API.entity;

import com.jobportal.Job.Portal.Backend.API.core.entity.AbstractEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "forget_password")
public class ForgetPassword extends AbstractEntity {

    @Column(name = "email", nullable = false, length = 100)
    private String email;

    @Column(name = "otp", nullable = false, length = 10)
    private String otp;

    @Column(name = "expiry_time", nullable = false)
    private LocalDateTime expiryTime;

    @Column(name = "is_used", nullable = false)
    private boolean used = false;
}