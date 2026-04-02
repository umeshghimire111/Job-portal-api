package com.jobportal.Job.Portal.Backend.API.service;

import com.jobportal.Job.Portal.Backend.API.dto.UploadProfilePictureDto;
import com.jobportal.Job.Portal.Backend.API.dto.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface ProfileService {
    ApiResponse<?> uploadProfilePicture(MultipartFile file, UploadProfilePictureDto profileDto);
    ApiResponse<?> updateProfilePicture(MultipartFile file, UploadProfilePictureDto profileDto);
    ApiResponse<?> deleteProfilePicture(String email);
    ApiResponse<?> getAllProfilePictures();
    ApiResponse<?> getProfileByEmail(String email);
    ResponseEntity<byte[]> getDirectImage(String fileName);
    ApiResponse<?> listFilesResponse();







}
