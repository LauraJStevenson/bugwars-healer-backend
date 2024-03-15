package com.example.bugwarshealerbackend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        System.out.println("CORS configuration applied!");
        registry.addMapping("/api/**")
                .allowedOrigins("http://localhost:5173", "https://bugwars-healer-frontend-stage.onrender.com",
                        "https://bugwars-healer-frontend.onrender.com")

                        .allowedMethods("GET", "POST", "PUT", "DELETE")
                        .allowedHeaders("*")
                        .allowCredentials(true)
                        .exposedHeaders("Allow-Origin", "Allow-Credentials");
    }
}