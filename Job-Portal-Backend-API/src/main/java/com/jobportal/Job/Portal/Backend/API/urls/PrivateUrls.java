package com.jobportal.Job.Portal.Backend.API.urls;

public class PrivateUrls {
    public static final String[] PRIVATE_URLS = {

            "/api/v1/auth/logout",
            "/api/v1/auth/validate",
            "/api/v1/auth/change-password",


            "/api/v1/applications/**",


            "/api/v1/jobs/create",
            "/api/v1/jobs/update",
            "/api/v1/jobs/update/**",
            "/api/v1/jobs/delete",
            "/api/v1/jobs/delete/**",


            "/api/v1/users/**",
            "/api/v1/users/profile",
            "/api/v1/users/update",
            "/api/v1/users/update/**",
            "/api/v1/users/upload",
            "/api/v1/users/upload/**",


            "/api/v1/upload/**",
            "/api/v1/upload",


            "/api/v1/admin/**",
            "/api/v1/admin/dashboard",
            "/api/v1/admin/users/**",
            "/api/v1/admin/jobs/**",
            "/api/v1/admin/applications/**"
    };
}