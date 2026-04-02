package com.jobportal.Job.Portal.Backend.API.controller;

import com.jobportal.Job.Portal.Backend.API.constant.controller.ApiConstant;
import com.jobportal.Job.Portal.Backend.API.dto.UploadProfilePictureDto;
import com.jobportal.Job.Portal.Backend.API.dto.response.ApiResponse;
import com.jobportal.Job.Portal.Backend.API.service.ProfileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(ApiConstant.API + ApiConstant.SLASH)
@RequiredArgsConstructor
public class ProfileViewController {

    private final ProfileService profileService;

    @PostMapping(value = ApiConstant.UPLOAD, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<?> upload(
            @RequestParam("file") MultipartFile file,
            @ModelAttribute @Valid UploadProfilePictureDto profileDto) {
        return profileService.uploadProfilePicture(file, profileDto);
    }

    @GetMapping(ApiConstant.ALL_PICTURE)
    public ApiResponse<?> getAllProfilePictures() {
        return profileService.getAllProfilePictures();
    }

    @GetMapping(ApiConstant.PROFILE_EMAIL)
    public ApiResponse<?> getProfileByEmail(@PathVariable String email) {
        return profileService.getProfileByEmail(email);
    }

    @GetMapping(ApiConstant.LIST_FILES)
    public ApiResponse<?> listFilesResponse(){
        return profileService.listFilesResponse();
    }

    @GetMapping(ApiConstant.DIRECT_IMAGE)
    public ResponseEntity<byte[]> getDirectImage(@PathVariable String fileName) {
        return profileService.getDirectImage(fileName);
    }
    @PostMapping(ApiConstant.DELETE)
    ApiResponse<?> deleteProfilePicture(String email){
        return profileService.deleteProfilePicture(email);
    }
    @PostMapping(ApiConstant.UPDATE)
    ApiResponse<?> updateProfilePicture(MultipartFile file, UploadProfilePictureDto profileDto){
        return profileService.updateProfilePicture(file, profileDto);
    }


}

