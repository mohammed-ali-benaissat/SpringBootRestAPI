package com.self.springbootprojectstarter.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.Customizer;

/**
 * 🔥 SecurityConfig = Spring Security configuration for the application
 *
 * Key teaching points:
 * 1️⃣ Spring Security intercepts HTTP requests before they reach controllers.
 * 2️⃣ SecurityFilterChain defines how requests are authenticated and authorized.
 * 3️⃣ @Profile allows you to define different rules for dev vs production.
 * 4️⃣ Stateless API = no HTTP session is stored on the server (typical for REST APIs with tokens or Basic Auth)
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // ============================================================
    // 🔹 DEV PROFILE SECURITY
    // ============================================================
    @Bean
    @Profile("dev")
    /**
     * Development profile: allows swagger for testing
     * - csrf disabled because we are building stateless REST API
     * - session management set to STATELESS (no server session)
     * - HTTP Basic authentication enabled for simplicity
     */
    public SecurityFilterChain devSecurityFilterChain(HttpSecurity http) throws Exception {
        http
                // Disable CSRF for stateless APIs (no session cookies)
                .csrf(AbstractHttpConfigurer::disable)

                // Session management: REST APIs usually do not keep server sessions
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                // Define authorization rules
                .authorizeHttpRequests(authz -> authz
                        // Allow Swagger endpoints in dev profile
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
                        // All other requests require authentication
                        .anyRequest().authenticated()
                )

                // Enable basic HTTP authentication
                .httpBasic(Customizer.withDefaults());

        // Build and return the security filter chain
        return http.build();
    }

    // ============================================================
    // 🔹 NON-DEV (PRODUCTION) PROFILE SECURITY
    // ============================================================
    @Bean
    @Profile("!dev")
    /**
     * Production profile: deny swagger access
     * - CSRF disabled (stateless)
     * - All endpoints require authentication
     * - Swagger endpoints explicitly denied
     */
    public SecurityFilterChain prodSecurityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authorizeHttpRequests(authz -> authz
                        // Deny swagger endpoints in production
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").denyAll()
                        // All other requests require authentication
                        .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }
}