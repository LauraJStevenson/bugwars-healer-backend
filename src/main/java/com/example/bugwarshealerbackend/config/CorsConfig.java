package com.example.bugwarshealerbackend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        System.out.println("CORS configuration applied!");  // Add this line
        registry.addMapping("/api/*")
                .allowedOrigins("http://localhost:8080/")
                //4, "https://stage-bugwars-healer.onrender.com")
                        .allowedMethods("GET", "POST", "PUT", "DELETE")
                        .allowedHeaders("")
                        .allowCredentials(true)
                        .exposedHeaders("Access-Control-Allow-Origin");
    }
}