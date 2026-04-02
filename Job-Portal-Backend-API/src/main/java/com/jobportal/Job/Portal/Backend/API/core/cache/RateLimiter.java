package com.jobportal.Job.Portal.Backend.API.core.cache;



import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import java.time.Duration;
import static com.jobportal.Job.Portal.Backend.API.constant.RateConstant.RATE_LIMIT_PREFIX;
import static com.jobportal.Job.Portal.Backend.API.constant.MaxRequest.MAX_REQUESTS_PER_MINUTE;

@Component
@AllArgsConstructor
public class RateLimiter {

    private StringRedisTemplate redisTemplate;



    public boolean isAllowed(String apiKey) {
        String key =  RATE_LIMIT_PREFIX+ apiKey;
        Long count = redisTemplate.opsForValue().increment(key);

        if (count == 1) {
            redisTemplate.expire(key, Duration.ofMinutes(1));
        }

        return count <= MAX_REQUESTS_PER_MINUTE;
    }

    public long getRemainingRequests(String apiKey) {
        String key =  RATE_LIMIT_PREFIX + apiKey;
        String countStr = redisTemplate.opsForValue().get(key);

        if (countStr == null) {
            return MAX_REQUESTS_PER_MINUTE;
        }

        long count = Long.parseLong(countStr);
        return Math.max(0, MAX_REQUESTS_PER_MINUTE - count);
    }

    public void resetRateLimit(String apiKey) {
        String key = RATE_LIMIT_PREFIX + apiKey;
        redisTemplate.delete(key);
    }
}