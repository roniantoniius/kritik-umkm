package com.roniantonius.kritikumkm.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
			.authorizeHttpRequests(auth -> 
					auth.anyRequest().authenticated()
			)
			
			// an authentication will handle token from Keycloak to create authenticated user
			.oauth2ResourceServer(oauth2 -> 
					oauth2.jwt(jwt -> 
							jwt.jwtAuthenticationConverter(jwtAuthenticationConverter())
					)
			)
			
			// building stateless rest api requiring stateless rest api atau STATELESS session management
			.sessionManagement(sesi -> 
					sesi.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			)
			.csrf(csrf -> csrf.disable());
		
		return http.build();
	}
	
	@Bean
	public JwtAuthenticationConverter jwtAuthenticationConverter() {
		return new JwtAuthenticationConverter();
	}
}
