package com.jobportal.Job.Portal.Backend.API.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum StatusConstant {
    ACTIVE("ACTIVE", "ACTIVE"),
    DELETED("DELETED", "DELETED"),
    PENDING("PENDING", "PENDING"),
    BLOCKED("BLOCKED","BLOCKED"),
    CONFIRMED("CONFIRMED", "CONFIRMED"),
    CANCELLED("CANCELLED", "CANCELLED"),
    COMPLETED("COMPLETED", "COMPLETED");

    private final String name;
    private final String description;
}