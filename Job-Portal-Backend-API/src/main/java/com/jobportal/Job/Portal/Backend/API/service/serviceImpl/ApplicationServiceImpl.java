package com.jobportal.Job.Portal.Backend.API.service.serviceImpl;

import com.jobportal.Job.Portal.Backend.API.constant.StatusConstant;
import com.jobportal.Job.Portal.Backend.API.dto.ApplicationRequest;
import com.jobportal.Job.Portal.Backend.API.dto.response.ApiResponse;
import com.jobportal.Job.Portal.Backend.API.entity.Application;
import com.jobportal.Job.Portal.Backend.API.entity.Job;
import com.jobportal.Job.Portal.Backend.API.entity.Status;
import com.jobportal.Job.Portal.Backend.API.entity.User;
import com.jobportal.Job.Portal.Backend.API.repository.ApplicationRepository;
import com.jobportal.Job.Portal.Backend.API.repository.JobRepository;
import com.jobportal.Job.Portal.Backend.API.repository.StatusRepository;
import com.jobportal.Job.Portal.Backend.API.repository.UserRepository;
import com.jobportal.Job.Portal.Backend.API.service.ApplicationService;
import com.jobportal.Job.Portal.Backend.API.service.UploadFileService;
import com.jobportal.Job.Portal.Backend.API.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;

@Service
@Slf4j
@RequiredArgsConstructor
public class ApplicationServiceImpl implements ApplicationService {

    private final JobRepository jobRepository;
    private final ApplicationRepository applicationRepository;
    private final UserRepository userRepository;
    private final StatusRepository statusRepository;
    private final UploadFileService uploadFileService;

    private static final String BASE_UPLOAD_PATH =
            System.getenv("UPLOAD_BASE_PATH") != null
                    ? System.getenv("UPLOAD_BASE_PATH")
                    : System.getProperty("user.home") + File.separator + "Downloads" + File.separator;

    private static final String RESUME_DIR = "resumes" + File.separator;

    @Override
    public ApiResponse<?> apply(ApplicationRequest applicationRequest) {
        try {
            validateRequest(applicationRequest);

            Job job = findJob(applicationRequest.getJobName());
            if (job == null) {
                return ResponseUtil.getFailureResponse("Job not found");
            }

            User candidate = findOrCreateCandidate(applicationRequest.getEmail());
            if (candidate == null) {
                return ResponseUtil.getFailureResponse("Unable to process candidate");
            }

            if (!isCandidateActive(candidate)) {
                return ResponseUtil.getFailureResponse("Your account is not active. Cannot apply to jobs.");
            }

            if (hasAlreadyApplied(job, candidate)) {
                return ResponseUtil.getFailureResponse("You have already applied to this job");
            }

            String resumePath = uploadResume(applicationRequest.getResume());
            if (resumePath == null) {
                return ResponseUtil.getFailureResponse("Failed to upload resume");
            }

            saveApplication(job, candidate, resumePath, applicationRequest.getEmail());

            log.info("Application submitted successfully for job: {} by email: {}", job.getJobName(), applicationRequest.getEmail());
            return ResponseUtil.getAuthenticatedApiResponse("Application submitted successfully");

        } catch (Exception e) {
            log.error("Unexpected error applying to job", e);
            return ResponseUtil.getBeanValidationFailureResponse("Application failed: " + e.getMessage());
        }
    }

    private void validateRequest(ApplicationRequest request) {
        if (request.getJobName() == null || request.getJobName().trim().isEmpty()) {
            throw new IllegalArgumentException("Job name is required");
        }
        if (request.getEmail() == null || request.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("Email is required");
        }
        if (request.getResume() == null || request.getResume().isEmpty()) {
            throw new IllegalArgumentException("Resume file is required");
        }
    }

    private Job findJob(String jobName) {
        return jobRepository.findByJobNameIgnoreCase(jobName.trim()).orElse(null);
    }

    private User findOrCreateCandidate(String email) {
        User candidate = userRepository.findByEmail(email);
        if (candidate == null) {
            Status activeStatus = statusRepository.findByName(StatusConstant.ACTIVE.getName());
            if (activeStatus == null) {
                log.error("Default status ACTIVE not found in database");
                return null;
            }
            candidate = new User();
            candidate.setEmail(email);
            candidate.setFullName("Candidate");
            candidate.setStatus(activeStatus);
            candidate = userRepository.save(candidate);
            log.info("New candidate created with email: {}", email);
        }
        return candidate;
    }

    private boolean isCandidateActive(User candidate) {
        return StatusConstant.ACTIVE.getName().equals(candidate.getStatus().getName());
    }

    private boolean hasAlreadyApplied(Job job, User candidate) {
        return applicationRepository.existsByJobAndCandidate(job, candidate);
    }

    private String uploadResume(MultipartFile resumeFile) {
        try {
            String contentType = resumeFile.getContentType();
            if (contentType == null ||
                    (!contentType.equals("application/pdf") &&
                            !contentType.equals("application/msword") &&
                            !contentType.equals("application/vnd.openxmlformats-officedocument.wordprocessingml.document"))) {
                log.warn("Invalid file type: {}", contentType);
                return null;
            }

            return uploadFileService.uploadFile(resumeFile, BASE_UPLOAD_PATH, RESUME_DIR, false);
        } catch (Exception e) {
            log.error("Failed to upload resume", e);
            return null;
        }
    }

    private void saveApplication(Job job, User candidate, String resumePath, String email) {
        Application application = new Application();
        application.setJob(job);
        application.setCandidate(candidate);
        application.setResumePath(resumePath);
        application.setEmail(email);
        application.setStatus(StatusConstant.ACTIVE.getName());
        applicationRepository.save(application);
        log.info("Application saved with ID: {}", application.getId());
    }
}