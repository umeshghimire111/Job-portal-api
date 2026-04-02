package com.jobportal.Job.Portal.Backend.API.entity;

import com.jobportal.Job.Portal.Backend.API.core.entity.AbstractEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "users", indexes = {
        @Index(name = "idx_user_email", columnList = "email"),
        @Index(name = "idx_user_unique_id", columnList = "unique_id")
})
public class User extends AbstractEntity {

    @Column(name = "full_name", nullable = false, length = 100)
    private String fullName;

    @Column(name = "email", nullable = false, unique = true, length = 100)
    private String email;

    @Column(name = "phone_number", length = 20)
    private String phoneNumber;

    @Column(name = "password", length = 255)
    private String password;

    @Column(name = "unique_id", nullable = false, unique = true, length = 50)
    private String uniqueId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "status_id", referencedColumnName = "id", nullable = false)
    private Status status;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "role_id", referencedColumnName = "id", nullable = false)
    private Role role;

    @Column(name = "registered_date", nullable = false)
    private LocalDateTime registeredDate;

    @Column(name = "password_changed_date")
    private LocalDateTime passwordChangeDate;

    @Column(name = "last_logged_in_time")
    private LocalDateTime lastLoggedInTime;

    @Column(name = "wrong_password_attempt_count", columnDefinition = "INT DEFAULT 0")
    private Integer wrongPasswordAttemptCount;

    @Column(name = "profile_picture_link", length = 500)
    private String profilePictureLink;
}