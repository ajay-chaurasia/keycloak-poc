package com.test.Keycloak.config;

import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.authorizeHttpRequests(auth -> auth.anyRequest().authenticated())
                .sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .oauth2ResourceServer(oauth2ResourceServer->oauth2ResourceServer.jwt(Customizer.withDefaults()))
                .httpBasic(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .build();
    }

    @PostConstruct
    private void configureSsl() {
        System.setProperty("javax.net.ssl.trustStore", System.getenv("TRUST_STORE_PATH"));
        System.setProperty("javax.net.ssl.trustStorePassword", System.getenv("TRUST_STORE_PASSWORD"));
        System.setProperty("javax.net.ssl.keyStore", System.getenv("KEY_STORE_PATH"));
        System.setProperty("javax.net.ssl.keyStorePassword", System.getenv("KEY_STORE_PASSWORD"));
    }
}
