package com.jobportal.Job.Portal.Backend.API.processor;


import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.core.env.MapPropertySource;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

public class CustomEnvPropertiesPostProcessor implements EnvironmentPostProcessor {
    private static final String PROPERTY_SOURCE_NAME = "customEnvPropertiesPostProcessor";

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        String url = environment.getProperty("spring.datasource.url");
        String username = environment.getProperty("spring.datasource.username");
        String password = environment.getProperty("spring.datasource.password");
        String driver = environment.getProperty("spring.datasource.driver-class-name");

        if (url == null || username == null || driver == null) {

            System.out.println("Datasource not configured, skipping CustomEnvPropertiesPostProcessor.");
            return;
        }

        Map<String, Object> envProperties = new HashMap<>();
        try {
            DataSource dataSource = DataSourceBuilder.create()
                    .url(url)
                    .username(username)
                    .password(password)
                    .driverClassName(driver)
                    .build();

            try (Connection conn = dataSource.getConnection();
                 PreparedStatement stmt = conn.prepareStatement("SELECT `key`, `value` FROM application_config");
                 ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {
                    String key = rs.getString("key");
                    String value = rs.getString("value");
                    envProperties.put(key, value);
                }
            }

            environment.getPropertySources().addFirst(new MapPropertySource(PROPERTY_SOURCE_NAME, envProperties));

        } catch (Exception e) {

            System.err.println("Failed to load environment properties from database: " + e.getMessage());
        }
    }
}
