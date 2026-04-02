package com.jobportal.Job.Portal.Backend.API.core.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jobportal.Job.Portal.Backend.API.dto.response.ApiResponse;
import com.jobportal.Job.Portal.Backend.API.entity.UserToken;
import com.jobportal.Job.Portal.Backend.API.repository.UserTokenRepository;
import com.jobportal.Job.Portal.Backend.API.util.ResponseUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;
import java.io.IOException;

@AllArgsConstructor
@Component
public class CustomLogoutHandler implements LogoutHandler {
    private static final Logger LOG = LoggerFactory.getLogger(CustomLogoutHandler.class);


    private final ObjectMapper objectMapper;
    private final UserTokenRepository userTokenRepository;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        response.setContentType("application/json");
        ApiResponse<?> apiResponse;
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            apiResponse = ResponseUtil.getFailureResponse("Invalid token");
            writeResponse(response, apiResponse);
            return;
        }

        String token = authHeader.substring(7);
        UserToken storedToken = userTokenRepository.findByAccessTokenAndLoggedOutFalse(token).orElse(null);

        if (storedToken != null) {
            storedToken.setLoggedOut(true);
            userTokenRepository.save(storedToken);
            LOG.info("Logged out successfully");
            apiResponse = ResponseUtil.getSuccessfulApiResponse("Logged out successfully");
            writeResponse(response, apiResponse);
            return;
        }
        apiResponse = ResponseUtil.getFailureResponse("Logout Unsuccessful");
        writeResponse(response, apiResponse);
    }

    private void writeResponse( HttpServletResponse response, ApiResponse<?> apiResponse) {
        response.setContentType("application/json");
        try {
            response.getWriter().write(objectMapper.writeValueAsString(apiResponse));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

