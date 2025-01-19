package com.chat_sphere.app.token_generation;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtRequestFilter jwtRequestFilter) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> {
                            auth.anyRequest().permitAll(); // Allow access to the token generation endpoint
                            //auth.anyRequest().authenticated(); // All other endpoints require authentication
                        })
                .cors(cors -> cors.configurationSource(request -> {
                    var config = new org.springframework.web.cors.CorsConfiguration();
                    config.addAllowedOriginPattern("*"); // Allow all origins or specify your client domain
                    config.addAllowedMethod("*");       // Allow all HTTP methods
                    config.addAllowedHeader("*");       // Allow all headers
                    return config;
                }))
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class) // Add custom filter
                .build();
    }
}

