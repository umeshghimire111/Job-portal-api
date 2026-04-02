package com.jobportal.Job.Portal.Backend.API.service.serviceImpl;


import com.jobportal.Job.Portal.Backend.API.constant.StatusConstant;
import com.jobportal.Job.Portal.Backend.API.constant.controller.RoleConstant;
import com.jobportal.Job.Portal.Backend.API.dto.UserDto;
import com.jobportal.Job.Portal.Backend.API.dto.response.ApiResponse;
import com.jobportal.Job.Portal.Backend.API.entity.Role;
import com.jobportal.Job.Portal.Backend.API.entity.Status;
import com.jobportal.Job.Portal.Backend.API.entity.User;
import com.jobportal.Job.Portal.Backend.API.exception.ApiException;
import com.jobportal.Job.Portal.Backend.API.repository.RoleRepository;
import com.jobportal.Job.Portal.Backend.API.repository.StatusRepository;
import com.jobportal.Job.Portal.Backend.API.repository.UserRepository;
import com.jobportal.Job.Portal.Backend.API.service.UserService;
import com.jobportal.Job.Portal.Backend.API.util.ResponseUtil;
import com.jobportal.Job.Portal.Backend.API.util.UuidUtil;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;


@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final StatusRepository statusRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;


    @Override
    public ApiResponse<?> register(UserDto userDto) {
        if (userRepository.existsByEmail(userDto.getEmail())) {
            throw new ApiException("Email is already registered", HttpStatus.CONFLICT);
        }

        Status defaultStatus = statusRepository.findByName(StatusConstant.ACTIVE.getName());
        if (defaultStatus == null) {
            return ResponseUtil.getFailureResponse("Default status not found. Please add it to the database.");
        }
        Role defaultRole = roleRepository.findByName(RoleConstant.USER.getName());
        if (defaultRole == null) {
            return ResponseUtil.getFailureResponse("Default role not found. Please add it to the database.");
        }

        User user = new User();
        user.setFullName(userDto.getFullName());
        user.setEmail(userDto.getEmail());
        user.setPhoneNumber(userDto.getPhoneNumber());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setStatus(defaultStatus);
        user.setRole(defaultRole);
        user.setRegisteredDate(LocalDateTime.now());
        user.setWrongPasswordAttemptCount(0);
        user.setUniqueId(UuidUtil.generateUuid());

        userRepository.save(user);

        return ResponseUtil.getSuccessfulApiResponse("User registered successfully");
    }
}
