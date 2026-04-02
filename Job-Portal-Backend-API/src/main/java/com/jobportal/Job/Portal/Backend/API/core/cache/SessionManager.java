package com.jobportal.Job.Portal.Backend.API.core.cache;


import com.jobportal.Job.Portal.Backend.API.entity.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import java.time.Duration;

@Component
@AllArgsConstructor
public class SessionManager {

    private StringRedisTemplate redisTemplate;

    private ObjectMapper objectMapper;

    private static final Duration SESSION_DURATION = Duration.ofHours(2);
    private static final String SESSION_PREFIX = "session:";

    public void storeUserSession(String token, User user) {
        String key = SESSION_PREFIX + token;
        try {
            String userJson = objectMapper.writeValueAsString(user);
            redisTemplate.opsForValue().set(key, userJson, SESSION_DURATION);
        } catch (Exception e) {
            throw new RuntimeException("Failed to store user session", e);
        }
    }

    public User getUserFromSession(String token) {
        String key = SESSION_PREFIX + token;
        String userJson = redisTemplate.opsForValue().get(key);

        if (userJson != null) {
            try {
                return objectMapper.readValue(userJson, User.class);
            } catch (Exception e) {
                throw new RuntimeException("Failed to parse user session", e);
            }
        }
        return null;
    }

    public void removeSession(String token) {
        String key = SESSION_PREFIX + token;
        redisTemplate.delete(key);
    }

    public boolean isSessionValid(String token) {
        String key = SESSION_PREFIX + token;
        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }
}