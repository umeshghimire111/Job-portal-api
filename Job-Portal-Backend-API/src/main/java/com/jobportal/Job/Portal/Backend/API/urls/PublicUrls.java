package com.jobportal.Job.Portal.Backend.API.urls;

public class PublicUrls {
    public static final String[] PUBLIC_URLS = {

            "/",
            "/index.html",
            "/favicon.ico",


            "/swagger-resources/**",
            "/swagger-ui/**",
            "/swagger-ui.html",
            "/v2/api-docs",
            "/v3/api-docs",
            "/v3/api-docs/**",
            "/configuration/ui",
            "/configuration/security",
            "/webjars/**",

            "/api/v1/auth/register",
            "/api/v1/auth/login",
            "/api/v1/auth/refreshToken",


            "/api/v1/forgetPassword",
            "/api/v1/forgetPassword/**",
            "/api/v1/sendOtp",
            "/api/v1/resetPassword",


            "/api/v1/jobs/list",
            "/api/v1/jobs/search",
            "/api/v1/jobs/{id}",
            "/api/v1/jobs/count/**"
    };
}