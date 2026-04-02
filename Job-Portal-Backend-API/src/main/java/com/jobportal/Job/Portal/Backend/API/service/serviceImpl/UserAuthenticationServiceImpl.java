package com.jobportal.Job.Portal.Backend.API.service.serviceImpl;

import com.jobportal.Job.Portal.Backend.API.constant.StatusConstant;
import com.jobportal.Job.Portal.Backend.API.core.model.UserPrincipal;
import com.jobportal.Job.Portal.Backend.API.core.security.JwtService;
import com.jobportal.Job.Portal.Backend.API.core.util.UserTokenUtil;
import com.jobportal.Job.Portal.Backend.API.dto.response.ApiResponse;
import com.jobportal.Job.Portal.Backend.API.dto.AuthenticateUserRequest;
import com.jobportal.Job.Portal.Backend.API.dto.UserAuthenticationResponse;
import com.jobportal.Job.Portal.Backend.API.entity.User;
import com.jobportal.Job.Portal.Backend.API.entity.UserToken;
import com.jobportal.Job.Portal.Backend.API.exception.ApiException;
import com.jobportal.Job.Portal.Backend.API.repository.StatusRepository;
import com.jobportal.Job.Portal.Backend.API.repository.UserRepository;
import com.jobportal.Job.Portal.Backend.API.repository.UserTokenRepository;
import com.jobportal.Job.Portal.Backend.API.service.UserAuthenticationService;
import com.jobportal.Job.Portal.Backend.API.util.ResponseUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import lombok.*;

@Service
@AllArgsConstructor
public class UserAuthenticationServiceImpl implements UserAuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final StatusRepository statusRepository;
    private final UserTokenRepository userTokenRepository;

    @Override
    @Transactional
    public ApiResponse<UserAuthenticationResponse> authenticate(AuthenticateUserRequest authenticateUserRequest, HttpServletResponse response) {
        User user = userRepository.findByEmail(authenticateUserRequest.getEmail());
        if (user == null || user.getStatus().equals(statusRepository.findByName(StatusConstant.DELETED.getName()))) {
            throw new ApiException("The account doesn't exist.", HttpStatus.NOT_FOUND);
        }
        if (user.getStatus().equals(statusRepository.findByName(StatusConstant.BLOCKED.getName()))) {
            throw new ApiException("The user is currently blocked. Please contact support.", HttpStatus.UNAUTHORIZED);
        }
        if (user.getStatus().equals(statusRepository.findByName(StatusConstant.ACTIVE.getName()))) {
            try {
                Authentication authentication = authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                authenticateUserRequest.getEmail(), authenticateUserRequest.getPassword()));
                authentication.isAuthenticated();
                if (authentication.isAuthenticated()) {
                    UserToken userToken = UserTokenUtil.saveToken(user,
                            jwtService.generateAccessToken(user),
                            userTokenRepository,
                            jwtService.generateRefreshToken(user));
                    jwtService.setHttpOnlyCookie(response,"accessToken",userToken.getAccessToken(),60*60*24);
                    jwtService.setHttpOnlyCookie(response,"refreshToken",userToken.getRefreshToken(),60*60*24*7);
                    userRepository.updateLastLoggedInTime(authenticateUserRequest.getEmail(), LocalDateTime.now());
                    userRepository.updateWrongPasswordAttemptCount(authenticateUserRequest.getEmail(),   0);

                    UserAuthenticationResponse authResponse =new UserAuthenticationResponse();
                    authResponse.setAccessToken(userToken.getAccessToken());
                    authResponse.setRefreshToken(userToken.getRefreshToken());

                    return ResponseUtil.getSuccessfulApiResponse(authResponse,"You have successfully logged in.");
                }
            } catch (BadCredentialsException e) {
                userRepository.updateWrongPasswordAttemptCount(user.getEmail(), user.getWrongPasswordAttemptCount() + 1);
                throw new ApiException("The password you entered is incorrect.", HttpStatus.UNAUTHORIZED);
            }
        }
        throw new ApiException("This account is awaiting verification. Please verify to continue.", HttpStatus.FORBIDDEN);
    }

    @Override
    public ApiResponse<?> refreshToken(HttpServletRequest request, HttpServletResponse response) {
        String refreshToken = null;
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if ("refreshToken".equals(cookie.getName())) {
                    refreshToken = cookie.getValue();
                    break;
                }
            }
        }
        if (refreshToken == null) {
            throw new ApiException("The refresh token is missing. Please log in again to continue.", HttpStatus.BAD_REQUEST);
        }
        User user = userRepository.findByEmail(jwtService.extractEmail(refreshToken));
        if (user == null) {
            throw new ApiException("The refresh token is invalid. Please log in again to continue.", HttpStatus.UNAUTHORIZED);
        }
        UserPrincipal userPrincipal = new UserPrincipal(user);
        boolean isRefreshTokenValid = jwtService.validateRefreshToken(refreshToken, userPrincipal);
        if (isRefreshTokenValid) {
            UserToken token = UserTokenUtil.saveToken(user,
                    jwtService.generateAccessToken(user),
                    userTokenRepository,
                    jwtService.generateRefreshToken(user));
            jwtService.setHttpOnlyCookie(response,"accessToken",token.getAccessToken(),60*60*24);
            jwtService.setHttpOnlyCookie(response,"refreshToken",token.getRefreshToken(),60*60*24*7);
            UserTokenUtil.invalidateToken(refreshToken, userTokenRepository::findByRefreshToken, userTokenRepository);
            return ResponseUtil.getSuccessfulApiResponse("Tokens refreshed successfully.");
        }
        throw new ApiException("The refresh token is invalid. Please log in again to continue.", HttpStatus.UNAUTHORIZED);
    }
    @Override
    public ApiResponse<?> logout(HttpServletRequest request, HttpServletResponse response) {
        String accessToken = null;
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if ("accessToken".equals(cookie.getName())) {
                    accessToken = cookie.getValue();
                    break;
                }
            }
        }
        UserToken storedToken = userTokenRepository.findByAccessTokenAndLoggedOutFalse(accessToken).orElse(null);
        if (storedToken != null) {
            storedToken.setLoggedOut(true);
            userTokenRepository.save(storedToken);
            jwtService.clearCookie("accessToken", response);
            jwtService.clearCookie("refreshToken", response);
            SecurityContextHolder.clearContext();
            return ResponseUtil.getSuccessfulApiResponse("You have been successfully logged out.");
        }
        throw new ApiException("Logout failed. An error occurred.", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}