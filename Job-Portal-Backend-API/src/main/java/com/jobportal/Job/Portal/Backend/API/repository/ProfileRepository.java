package com.jobportal.Job.Portal.Backend.API.repository;


import com.jobportal.Job.Portal.Backend.API.entity.ProfilePicture;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProfileRepository extends JpaRepository<ProfilePicture, Integer> {
    Optional<ProfilePicture> findByEmail(String email);
}
