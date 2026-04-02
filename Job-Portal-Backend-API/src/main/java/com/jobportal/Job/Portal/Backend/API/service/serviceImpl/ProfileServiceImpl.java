package com.jobportal.Job.Portal.Backend.API.service.serviceImpl;


import com.jobportal.Job.Portal.Backend.API.constant.StatusConstant;
import com.jobportal.Job.Portal.Backend.API.dto.UploadProfilePictureDto;
import com.jobportal.Job.Portal.Backend.API.dto.response.ApiResponse;
import com.jobportal.Job.Portal.Backend.API.entity.ProfilePicture;
import com.jobportal.Job.Portal.Backend.API.entity.User;
import com.jobportal.Job.Portal.Backend.API.repository.ProfileRepository;
import com.jobportal.Job.Portal.Backend.API.repository.StatusRepository;
import com.jobportal.Job.Portal.Backend.API.repository.UserRepository;
import com.jobportal.Job.Portal.Backend.API.service.ProfileService;
import com.jobportal.Job.Portal.Backend.API.service.UploadFileService;
import com.jobportal.Job.Portal.Backend.API.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {

    private final UploadFileService uploadFileService;
    private final ProfileRepository profileRepository;
    private final UserRepository userRepository;
    private final StatusRepository statusRepository;

    private static final String BASE_UPLOAD_PATH =
            System.getenv("UPLOAD_BASE_PATH") != null
                    ? System.getenv("UPLOAD_BASE_PATH")
                    : System.getProperty("user.home") + File.separator + "Downloads" + File.separator;

    private static final String PROFILE_PICTURE_DIR = "profiles" + File.separator;

    @Override
    public ApiResponse<?> uploadProfilePicture(MultipartFile file, UploadProfilePictureDto profileDto) {
        User user = userRepository.findByEmail(profileDto.getEmail());
        if (user == null || user.getStatus().equals(statusRepository.findByName(StatusConstant.DELETED.getName()))) {
            return ResponseUtil.getFailureResponse("The account doesn't exist.");
        }

        Optional<ProfilePicture> existingProfile = profileRepository.findByEmail(profileDto.getEmail());
        if (existingProfile.isPresent()) {
            return ResponseUtil.getFailureResponse("Profile picture already exists. Use update endpoint.");
        }

        try {
            String fileName = uploadFileService.uploadFile(file, BASE_UPLOAD_PATH, PROFILE_PICTURE_DIR, true);

            ProfilePicture newProfile = new ProfilePicture();
            newProfile.setName(profileDto.getName());
            newProfile.setEmail(profileDto.getEmail());
            newProfile.setProfilePicture(fileName);

            profileRepository.save(newProfile);

            return ResponseUtil.getSuccessfulApiResponse("Profile picture uploaded successfully");

        } catch (IllegalArgumentException e) {
            return ResponseUtil.getBeanValidationFailureResponse(e.getMessage());
        } catch (IOException e) {
            log.error("Upload error", e);
            return ResponseUtil.getErrorWhileRunning("Failed to upload");
        }
    }

    @Override
    public ApiResponse<?> updateProfilePicture(MultipartFile file, UploadProfilePictureDto profileDto) {
        User user = userRepository.findByEmail(profileDto.getEmail());
        if (user == null || user.getStatus().equals(statusRepository.findByName(StatusConstant.DELETED.getName()))) {
            return ResponseUtil.getFailureResponse("The account doesn't exist.");
        }

        Optional<ProfilePicture> existingProfileOpt = profileRepository.findByEmail(profileDto.getEmail());
        if (existingProfileOpt.isEmpty()) {
            return ResponseUtil.getFailureResponse("Profile picture not found. Use upload endpoint first.");
        }

        try {
            String fileName = uploadFileService.uploadFile(file, BASE_UPLOAD_PATH, PROFILE_PICTURE_DIR, true);

            ProfilePicture existingProfile = existingProfileOpt.get();

            String oldFileName = existingProfile.getProfilePicture();
            if (oldFileName != null && !oldFileName.isEmpty()) {
                Path oldPath = Paths.get(BASE_UPLOAD_PATH + PROFILE_PICTURE_DIR + oldFileName);
                Files.deleteIfExists(oldPath);
            }

            existingProfile.setName(profileDto.getName());
            existingProfile.setProfilePicture(fileName);
            profileRepository.save(existingProfile);

            return ResponseUtil.getSuccessfulApiResponse("Profile picture updated successfully");

        } catch (IllegalArgumentException e) {
            return ResponseUtil.getBeanValidationFailureResponse(e.getMessage());
        } catch (IOException e) {
            log.error("Update error", e);
            return ResponseUtil.getErrorWhileRunning("Failed to update");
        }
    }

    @Override
    public ApiResponse<?> deleteProfilePicture(String email) {
        Optional<ProfilePicture> profileOpt = profileRepository.findByEmail(email);
        if (profileOpt.isEmpty()) {
            return ResponseUtil.getNotFoundResponse("Profile picture not found with email: " + email);
        }

        ProfilePicture profile = profileOpt.get();
        String fileName = profile.getProfilePicture();

        if (fileName != null && !fileName.isEmpty()) {
            Path path = Paths.get(BASE_UPLOAD_PATH + PROFILE_PICTURE_DIR + fileName);
            try {
                Files.deleteIfExists(path);
            } catch (IOException e) {
                log.error("Error deleting file: {}", fileName, e);
            }
        }

        profileRepository.delete(profile);
        return ResponseUtil.getSuccessfulApiResponse("Profile picture deleted successfully");
    }

    @Override
    public ApiResponse<?> getAllProfilePictures() {
        List<ProfilePicture> profiles = profileRepository.findAll();
        return ResponseUtil.getSuccessfulApiResponse(profiles, "Profiles retrieved successfully");
    }

    @Override
    public ApiResponse<?> getProfileByEmail(String email) {
        return profileRepository.findByEmail(email)
                .map(profile -> ResponseUtil.getSuccessfulApiResponse(profile, "Profile found"))
                .orElseGet(() -> ResponseUtil.getNotFoundResponse("Profile not found with email: " + email));
    }

    @Override
    public ResponseEntity<byte[]> getDirectImage(String fileName) {
        if (fileName == null || fileName.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        try {
            String decodedFileName = java.net.URLDecoder.decode(fileName, java.nio.charset.StandardCharsets.UTF_8);
            Path path = Paths.get(BASE_UPLOAD_PATH + PROFILE_PICTURE_DIR + decodedFileName);

            if (!Files.exists(path)) {
                return ResponseEntity.notFound().build();
            }

            byte[] fileBytes = Files.readAllBytes(path);
            String contentType = getContentType(decodedFileName);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType(contentType));
            headers.setContentDispositionFormData("inline", decodedFileName);

            return new ResponseEntity<>(fileBytes, headers, HttpStatus.OK);

        } catch (IOException e) {
            log.error("Error reading file {}", fileName, e);
            return ResponseEntity.status(500).build();
        }
    }

    @Override
    public ApiResponse<?> listFilesResponse() {
        List<ProfilePicture> files = profileRepository.findAll();

        Map<String, List<String>> grouped = files.stream()
                .collect(Collectors.groupingBy(
                        ProfilePicture::getName,
                        Collectors.mapping(ProfilePicture::getProfilePicture, Collectors.toList())));

        List<Map<String, Object>> result = new ArrayList<>();
        for (Map.Entry<String, List<String>> entry : grouped.entrySet()) {
            Map<String, Object> map = new HashMap<>();
            map.put("originalName", entry.getKey());
            map.put("versions", entry.getValue());
            result.add(map);
        }

        return ResponseUtil.getSuccessfulApiResponse(""+result);
    }

    private String getContentType(String fileName) {
        String fileNameLower = fileName.toLowerCase();
        if (fileNameLower.endsWith(".png")) {
            return "image/png";
        } else if (fileNameLower.endsWith(".jpg") || fileNameLower.endsWith(".jpeg")) {
            return "image/jpeg";
        } else if (fileNameLower.endsWith(".gif")) {
            return "image/gif";
        } else {
            return "image/jpeg";
        }
    }
}
