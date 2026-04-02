package com.jobportal.Job.Portal.Backend.API.entity;

import com.jobportal.Job.Portal.Backend.API.core.entity.AbstractEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "applications", indexes = {
        @Index(name = "idx_app_job_id", columnList = "job_id"),
        @Index(name = "idx_app_candidate_id", columnList = "candidate_id"),
        @Index(name = "idx_app_email", columnList = "email")
})
public class Application extends AbstractEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "job_id", nullable = false)
    private Job job;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "candidate_id", nullable = false)
    private User candidate;

    @Column(name = "resume_path", nullable = false, length = 500)
    private String resumePath;

    @Column(name = "email", nullable = false, length = 100)
    private String email;

    @Column(name = "status", nullable = false, length = 50)
    private String status;
}