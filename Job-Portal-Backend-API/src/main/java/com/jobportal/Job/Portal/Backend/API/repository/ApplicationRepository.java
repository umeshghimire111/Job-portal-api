package com.jobportal.Job.Portal.Backend.API.repository;

import com.jobportal.Job.Portal.Backend.API.entity.Application;
import com.jobportal.Job.Portal.Backend.API.entity.Job;
import com.jobportal.Job.Portal.Backend.API.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ApplicationRepository extends JpaRepository<Application, Integer> {

    boolean existsByJobAndCandidate(Job job, User candidate);
}
