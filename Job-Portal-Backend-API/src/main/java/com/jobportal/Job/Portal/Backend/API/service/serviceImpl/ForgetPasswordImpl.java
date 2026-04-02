package com.jobportal.Job.Portal.Backend.API.service.serviceImpl;

import com.jobportal.Job.Portal.Backend.API.dto.ForgetPasswordDto;
import com.jobportal.Job.Portal.Backend.API.dto.ResetPasswordRequest;
import com.jobportal.Job.Portal.Backend.API.dto.response.ApiResponse;
import com.jobportal.Job.Portal.Backend.API.entity.ForgetPassword;
import com.jobportal.Job.Portal.Backend.API.entity.User;
import com.jobportal.Job.Portal.Backend.API.repository.ForgetPasswordRepository;
import com.jobportal.Job.Portal.Backend.API.repository.UserRepository;
import com.jobportal.Job.Portal.Backend.API.service.ForgetPasswordService;
import com.jobportal.Job.Portal.Backend.API.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import static com.jobportal.Job.Portal.Backend.API.util.OtpUtil.generateOtp;

@Service
@RequiredArgsConstructor
public class ForgetPasswordImpl implements ForgetPasswordService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailServiceImpl mailService;
    private final ForgetPasswordRepository forgetPasswordRepository; // Now being used

    @Override
    public ApiResponse<?> sendOtp(ForgetPasswordDto dto) {
        String normalizedEmail = dto.getEmail();

        User user = userRepository.findByEmail(normalizedEmail);
        if (user == null) {
            return ResponseUtil.getNotFoundResponse("User not registered with this email");
        }

        String otp = generateOtp();


        ForgetPassword forgetPassword = new ForgetPassword();
        forgetPassword.setEmail(normalizedEmail);
        forgetPassword.setOtp(otp);
        forgetPassword.setCreatedAt(LocalDateTime.now());
        forgetPassword.setExpiryTime(LocalDateTime.now().plusMinutes(10));
        forgetPassword.setUsed(false);
        forgetPasswordRepository.save(forgetPassword);

        try {
            mailService.sendOtpMail(normalizedEmail, user.getFullName(), otp, "null");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseUtil.getFailureResponse("Failed to send OTP email: ");
        }

        return ResponseUtil.getSuccessfulApiResponse("OTP sent successfully");
    }
    @Override
    public ApiResponse<?> resetPassword(ResetPasswordRequest request) {
        String normalizedEmail = request.getEmail();
        String otp = request.getOtp();
        String newPassword = request.getNewPassword();


        ForgetPassword forgetPassword = forgetPasswordRepository.findValidOtp(
                normalizedEmail, otp, LocalDateTime.now()
        ).orElse(null);

        if (forgetPassword == null) {
            return ResponseUtil.getNotFoundResponse("Invalid or expired OTP");
        }

        forgetPassword.setUsed(true);
        forgetPasswordRepository.save(forgetPassword);

        User user = userRepository.findByEmail(normalizedEmail);
        if (user == null) {
            return ResponseUtil.getNotFoundResponse("User not found");
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);

        return ResponseUtil.getSuccessfulApiResponse("Password reset successfully");
    }
}