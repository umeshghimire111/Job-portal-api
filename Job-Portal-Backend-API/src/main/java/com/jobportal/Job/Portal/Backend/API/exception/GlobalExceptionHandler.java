package com.jobportal.Job.Portal.Backend.API.exception;



import com.jobportal.Job.Portal.Backend.API.dto.response.ApiResponse;
import com.jobportal.Job.Portal.Backend.API.util.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ApiException.class)
    public ApiResponse<?> handleApiException(ApiException e) {
        log.warn("API Exception: {}", e.getMessage());
        return ResponseUtil.getFailureResponse(e.getMessage());
    }

    @ExceptionHandler(DataAccessException.class)
    public ApiResponse<?> handleDatabaseException(DataAccessException e) {
        log.error("Database error: {}", e.getMessage());

        if (e.getMessage().contains("doesn't exist")) {
            return ResponseUtil.getFailureResponse("Database table not found. Please contact administrator.");
        }

        return ResponseUtil.getFailureResponse("Database error occurred");
    }

//    @ExceptionHandler(RedisConnectionFailureException.class)
//    public ApiResponse<?> handleRedisException(RedisConnectionFailureException e) {
//        log.error("Redis connection failed: {}", e.getMessage());
//        return ResponseUtil.getFailureResponse("Cache service unavailable. Please try again later.");
//    }

    @ExceptionHandler(Exception.class)
    public ApiResponse<?> handleGenericException(Exception e) {
        log.error("Unexpected error: {}", e.getMessage(), e);
        return ResponseUtil.getFailureResponse("An unexpected error occurred");
    }
}