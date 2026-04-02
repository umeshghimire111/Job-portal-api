package com.jobportal.Job.Portal.Backend.API.core.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenAPI(){
        return new OpenAPI()
                .info(new Info().title("Job Portal Backend API "))
                .addSecurityItem(new SecurityRequirement().addList("job portal"))
                .components(new Components().addSecuritySchemes("Job portal ", new SecurityScheme()
                        .name("job portal").type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")));
    }

    }

