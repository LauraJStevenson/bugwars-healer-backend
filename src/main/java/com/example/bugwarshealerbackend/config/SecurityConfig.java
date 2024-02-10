package com.example.bugwarshealerbackend.config;


import com.example.bugwarshealerbackend.jpa.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;

@Configuration
public class SecurityConfig {

    UserRepository userRepository;

    public SecurityConfig (UserRepository userRepository) {
        super();
        this.userRepository =  userRepository;
    }

    // return 403 if the user is not logged in
    @Bean
    AuthenticationEntryPoint forbiddenEntryPoint() {
        return new HttpStatusEntryPoint(HttpStatus.FORBIDDEN);
    }

    // defined security role for end points
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .authorizeHttpRequests(request -> request
                        .requestMatchers(HttpMethod.POST, "/api/v1/users/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/v1/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/v1/logout").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/v1/scripts/**").permitAll()

                        .requestMatchers(new AntPathRequestMatcher("/api/**")).authenticated())


                .addFilterBefore(new AuthenticationFilter(userRepository),
                        UsernamePasswordAuthenticationFilter.class)
                // csrf => Cross-Site Request Forgery
                .csrf(AbstractHttpConfigurer::disable)
        ;
        return http.build();
    }

}
