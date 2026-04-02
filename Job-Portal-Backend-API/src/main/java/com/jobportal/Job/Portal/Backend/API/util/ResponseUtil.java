package com.jobportal.Job.Portal.Backend.API.util;


import com.jobportal.Job.Portal.Backend.API.constant.ServerResponseCodeConstant;
import com.jobportal.Job.Portal.Backend.API.dto.response.ApiResponse;
import org.springframework.http.HttpStatus;
import java.time.LocalDateTime;

public class ResponseUtil {
    public static <T> ApiResponse<T> getApiExceptionResponse(HttpStatus status,String message) {
        return ApiResponse.<T>builder()
                .code(ServerResponseCodeConstant.GENERIC_ERROR)
                .message(message)
                .httpStatus(status)
                .timestamp(LocalDateTime.now())
                .build();
    }
    public static <T> ApiResponse<T> getFailureResponse(String message) {
        return ApiResponse.<T>builder()
                .code(ServerResponseCodeConstant.GENERIC_ERROR)
                .message(message)
                .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                .timestamp(LocalDateTime.now())
                .build();
    }

    public static <T> ApiResponse<T> getSuccessfulApiResponse(String message) {
        return ApiResponse.<T>builder()
                .code(ServerResponseCodeConstant.SUCCESS)
                .message(message)
                .httpStatus(HttpStatus.OK)
                .timestamp(LocalDateTime.now())
                .build();
    }

    public static <T> ApiResponse<T> getSuccessfulApiResponse(T data, String message) {
        return ApiResponse.<T>builder()
                .code(ServerResponseCodeConstant.SUCCESS)
                .message(message)
                .data(data)
                .httpStatus(HttpStatus.OK)
                .timestamp(LocalDateTime.now())
                .build();
    }

    public static <T> ApiResponse<T> getTimeoutApiResponse(String message) {
        return ApiResponse.<T>builder()
                .code(ServerResponseCodeConstant.TIMEOUT)
                .message(message)
                .httpStatus(HttpStatus.REQUEST_TIMEOUT)
                .timestamp(LocalDateTime.now())
                .build();
    }

    public static <T> ApiResponse<T> getNotFoundResponse(String message) {
        return ApiResponse.<T>builder()
                .code(ServerResponseCodeConstant.NOT_FOUND)
                .message(message)
                .httpStatus(HttpStatus.NOT_FOUND)
                .timestamp(LocalDateTime.now())
                .build();
    }

    public static <T> ApiResponse<T> getBeanValidationFailureResponse(String message) {
        return ApiResponse.<T>builder()
                .code(ServerResponseCodeConstant.VALIDATION_ERROR)
                .message(message)
                .httpStatus(HttpStatus.BAD_REQUEST)
                .timestamp(LocalDateTime.now())
                .build();
    }

    public static <T> ApiResponse<T> getUnAuthorized(String message) {
        return ApiResponse.<T>builder()
                .code(ServerResponseCodeConstant.FAILURE)
                .message(message)
                .httpStatus(HttpStatus.UNAUTHORIZED)
                .timestamp(LocalDateTime.now())
                .build();
    }
    public static <T> ApiResponse<T> getAuthenticatedApiResponse(String message) {
        return ApiResponse.<T>builder()
                .code(ServerResponseCodeConstant.SUCCESS)
                .message(message)
                .httpStatus(HttpStatus.OK)
                .timestamp(LocalDateTime.now())
                .build();
    }
    public static<T> ApiResponse<T> getConflict( String message) {
        return ApiResponse.<T>builder()
                .code(ServerResponseCodeConstant.CONFLICT)
                .message(message)
                .httpStatus(HttpStatus.OK)
                .timestamp(LocalDateTime.now())
                .build();
    }

    public static<T> ApiResponse<T> getErrorWhileRunning( String message) {
        return ApiResponse.<T>builder()
                .code(ServerResponseCodeConstant.RUNTIME_ERROR)
                .message(message)
                .httpStatus(HttpStatus.OK)
                .timestamp(LocalDateTime.now())
                .build();
    }
}
