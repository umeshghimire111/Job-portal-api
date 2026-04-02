package com.jobportal.Job.Portal.Backend.API.repository;

import com.jobportal.Job.Portal.Backend.API.entity.ForgetPassword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface ForgetPasswordRepository extends JpaRepository<ForgetPassword,Long> {

    @Query("SELECT f FROM ForgetPassword f WHERE f.email = :email AND f.otp = :otp AND f.used = false AND f.expiresAt > :now")
    Optional<ForgetPassword> findValidOtp(@Param("email") String email, @Param("otp") String otp, @Param("now") LocalDateTime now);
}
