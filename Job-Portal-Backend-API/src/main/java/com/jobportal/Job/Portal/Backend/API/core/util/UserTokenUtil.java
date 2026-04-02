package com.jobportal.Job.Portal.Backend.API.core.util;

import com.jobportal.Job.Portal.Backend.API.entity.User;
import com.jobportal.Job.Portal.Backend.API.entity.UserToken;
import com.jobportal.Job.Portal.Backend.API.repository.UserTokenRepository;
import lombok.NonNull;
import java.util.Optional;
import java.util.function.Function;

public class UserTokenUtil {
    public static UserToken saveToken(User user, String accessToken, UserTokenRepository tokenRepo, String refreshToken) {
        UserToken token = new UserToken();
        token.setUser(user);
        token.setAccessToken(accessToken);
        token.setRefreshToken(refreshToken);
        token.setLoggedOut(false);
        return tokenRepo.save(token);
    }


    /*
     Example usage:
     invalidateToken(refreshToken, adminTokenRepo::findByRefreshToken, adminTokenRepo);
     invalidateToken(accessToken, adminTokenRepo::findByAccessToken, adminTokenRepo);
     */

    public static void invalidateToken(String tokenValue, @NonNull Function<String, Optional<UserToken>> tokenFinder, UserTokenRepository tokenRepository) {
        tokenFinder.apply(tokenValue).ifPresent(token -> {
            token.setLoggedOut(true);
            tokenRepository.save(token);
        });
    }
}
