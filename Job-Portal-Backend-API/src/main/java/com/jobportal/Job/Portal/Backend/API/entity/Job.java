package com.jobportal.Job.Portal.Backend.API.entity;

import com.jobportal.Job.Portal.Backend.API.core.entity.AbstractEntity;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "jobs", indexes = {
        @Index(name = "idx_job_title", columnList = "title"),
        @Index(name = "idx_job_location", columnList = "location")
})
public class Job extends AbstractEntity {

    @Column(name = "job_name", nullable = false, unique = true, length = 100)
    private String jobName;

    @Column(name = "title", nullable = false, length = 150)
    private String title;

    @Column(name = "description", nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(name = "location", nullable = false, length = 100)
    private String location;

    @Column(name = "salary", nullable = false, precision = 10, scale = 2)
    private BigDecimal salary;

    // Uncomment when User entity is ready
    // @ManyToOne(fetch = FetchType.LAZY, optional = false)
    // @JoinColumn(name = "recruiter_id", nullable = false)
    // private User recruiter;
}