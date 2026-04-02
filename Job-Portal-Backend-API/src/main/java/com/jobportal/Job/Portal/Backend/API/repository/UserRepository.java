package com.jobportal.Job.Portal.Backend.API.repository;

import com.jobportal.Job.Portal.Backend.API.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    User findByEmail(String username);

    boolean existsByEmail(String email);
    @Modifying
    @Query("UPDATE User u SET u.lastLoggedInTime = :time WHERE u.email = :email")
    void updateLastLoggedInTime(@Param("email") String email, @Param("time") LocalDateTime time);

    @Modifying
    @Query("UPDATE User u SET u.wrongPasswordAttemptCount = :count WHERE u.email = :email")
    void updateWrongPasswordAttemptCount(@Param("email") String email, @Param("count") Integer count);

}
