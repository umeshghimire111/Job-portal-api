package com.jobportal.Job.Portal.Backend.API.repository;

import com.jobportal.Job.Portal.Backend.API.entity.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface JobRepository extends JpaRepository<Job,Long> {

  @Query("SELECT j FROM Job j WHERE LOWER(j.jobName) = LOWER(:jobName)")
  Optional<Job> findByJobNameIgnoreCase(@Param("jobName") String jobName);
}
