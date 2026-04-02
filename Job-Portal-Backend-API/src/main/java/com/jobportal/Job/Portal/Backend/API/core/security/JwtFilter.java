package com.jobportal.Job.Portal.Backend.API.core.security;

import com.jobportal.Job.Portal.Backend.API.core.service.UserDetailService;
import com.jobportal.Job.Portal.Backend.API.urls.PrivateUrls;
import com.jobportal.Job.Portal.Backend.API.urls.PublicUrls;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.Arrays;

@AllArgsConstructor
@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final ApplicationContext applicationContext;
    private final AntPathMatcher pathMatcher = new AntPathMatcher();

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        String requestURI = request.getRequestURI();


        if (isPublicUrl(requestURI)) {
            filterChain.doFilter(request, response);
            return;
        }


        if (isPrivateUrl(requestURI)) {
            String token = extractToken(request);
            String email = null;

            if (token != null) {
                email = jwtService.extractEmail(token);
            }

            if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = applicationContext.getBean(UserDetailService.class).loadUserByUsername(email);

                if (jwtService.validateAccessToken(token, userDetails)) {
                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
        }

        filterChain.doFilter(request, response);
    }

    private String extractToken(HttpServletRequest request) {

        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }


        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if ("accessToken".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }

        return null;
    }

    private boolean isPrivateUrl(String requestUrl) {
        return Arrays.stream(PrivateUrls.PRIVATE_URLS)
                .anyMatch(pattern -> pathMatcher.match(pattern, requestUrl));
    }

    private boolean isPublicUrl(String requestUrl) {
        return Arrays.stream(PublicUrls.PUBLIC_URLS)
                .anyMatch(pattern -> pathMatcher.match(pattern, requestUrl));
    }
}