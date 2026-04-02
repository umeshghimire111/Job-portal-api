package com.jobportal.Job.Portal.Backend.API.entity;

import com.jobportal.Job.Portal.Backend.API.core.entity.AbstractEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "user_tokens", indexes = {
        @Index(name = "idx_ut_user_id", columnList = "user_id"),
        @Index(name = "idx_ut_access_token", columnList = "access_token"),
        @Index(name = "idx_ut_refresh_token", columnList = "refresh_token")
})
public class UserToken extends AbstractEntity {

    @Column(name = "access_token", length = 500)
    private String accessToken;

    @Column(name = "refresh_token", length = 500)
    private String refreshToken;

    @Column(name = "logged_out", nullable = false)
    private boolean loggedOut = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
}