package com.Management.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

import static com.Management.enamurate.Permission.*;
import static com.Management.enamurate.Role.*;
import static org.springframework.http.HttpMethod.*;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(req->req
                        .requestMatchers("/api/v1/comp/**").permitAll() // it will set all (dept/emp/task)
                        .requestMatchers("/api/v1/auth/**").permitAll() // it will user registration/authentication
                        .requestMatchers("/api/v1/ceo/**").hasRole(CEO.name())
                        .requestMatchers(GET,"/api/v1/ceo/**").hasAuthority(CEO_READ.name())
                        .requestMatchers(POST,"/api/v1/ceo/**").hasAuthority(CEO_CREATE.name())
                        .requestMatchers(PUT,"/api/v1/ceo/**").hasAuthority(CEO_UPDATE.name())
                        .requestMatchers(DELETE,"/api/v1/ceo/**").hasAuthority(CEO_DELETE.name())
                        .requestMatchers("/api/v1/manager/**").hasAnyRole(CEO.name(), MANAGER.name())
                        .requestMatchers(GET,"/api/v1/manager/**").hasAnyAuthority(CEO_READ.name(), MANAGER_READ.name())
                        .requestMatchers(POST,"/api/v1/manager/**").hasAnyAuthority(CEO_CREATE.name(),MANAGER_CREATE.name())
                        .requestMatchers(PUT,"/api/v1/manager/**").hasAnyAuthority(CEO_UPDATE.name(), MANAGER_UPDATE.name())
                        .requestMatchers("/api/v1/tl/**").hasAnyRole(CEO.name(), MANAGER.name(), TL.name())
                        .requestMatchers(GET, "/api/v1/tl/**").hasAnyAuthority(CEO_READ.name(), MANAGER_READ.name(), TL_READ.name())
                .anyRequest().authenticated());
        return http.build();
    }
}
