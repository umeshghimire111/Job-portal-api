package com.jobportal.Job.Portal.Backend.API.core.cache;


import com.jobportal.Job.Portal.Backend.API.entity.Job;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;
import java.time.Duration;
import java.util.List;

@Repository
@AllArgsConstructor
public class JobSearchRepository {


    private StringRedisTemplate redisTemplate;

    private ObjectMapper objectMapper;

    public void cacheSearchResults(String query, List<Job> results) {
        String key = "search:" + query.toLowerCase().replace(" ", ":");
        try {
            String jsonResults = objectMapper.writeValueAsString(results);
            redisTemplate.opsForValue().set(key, jsonResults, Duration.ofMinutes(15));
        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    public List<Job> getCachedSearchResults(String query) {
        String key = "search:" + query.toLowerCase().replace(" ", ":");
        String jsonResults = redisTemplate.opsForValue().get(key);

        if (jsonResults != null) {
            try {
                return objectMapper.readValue(jsonResults,
                        objectMapper.getTypeFactory().constructCollectionType(List.class, Job.class));
            } catch (Exception e) {
                // Handle deserialization error
                e.printStackTrace();
            }
        }
        return null;
    }
}