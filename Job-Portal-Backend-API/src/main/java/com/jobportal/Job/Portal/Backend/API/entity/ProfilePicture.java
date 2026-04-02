package com.jobportal.Job.Portal.Backend.API.entity;

import com.jobportal.Job.Portal.Backend.API.core.entity.AbstractEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "upload_profiles", indexes = {
        @Index(name = "idx_up_email", columnList = "email")
})
public class ProfilePicture extends AbstractEntity {

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "email", nullable = false, unique = true, length = 100)
    private String email;

    @Column(name = "profile_picture", length = 500)
    private String profilePicture;
}