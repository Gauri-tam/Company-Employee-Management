package com.Management.config;

import com.Management.component.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import static com.Management.enamurate.Permission.*;
import static com.Management.enamurate.Role.*;
import static org.springframework.http.HttpMethod.*;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final AuthenticationProvider authenticationProvider;

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    private final LogoutHandler logoutHandler;

    private static final String[] URLS = {
            "/api/v1/auth/**", // it will user registration/authentication
            "/api/v1/ceo/register/**"  // creating on
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(req->req
                        .requestMatchers("/api/v1/comp/**").permitAll() // it will set all (dept/emp/task)
                        .requestMatchers(URLS).permitAll() // it will  registration/authentication
                        .requestMatchers( "/api/v1/ceo/**").hasRole(CEO.name())
                        .requestMatchers(GET, "/api/v1/ceo/**").hasAuthority(CEO_READ.name())
                        .requestMatchers(POST, "/api/v1/ceo/**").hasAuthority(CEO_CREATE.name())
                        .requestMatchers(PUT, "/api/v1/ceo/**").hasAuthority(CEO_UPDATE.name())
                        .requestMatchers(DELETE, "/api/v1/ceo/**").hasAuthority(CEO_DELETE.name())

                        .requestMatchers("/api/v1/manager/**").hasAnyRole(CEO.name(), MANAGER.name())
                        .requestMatchers(GET, "/api/v1/manager/**").hasAnyAuthority(CEO_READ.name(), MANAGER_READ.name())
                        .requestMatchers(POST, "/api/v1/manager/**").hasAnyAuthority(CEO_CREATE.name(), MANAGER_CREATE.name())
                        .requestMatchers(PUT, "/api/v1/manager/**").hasAnyAuthority(CEO_UPDATE.name(), MANAGER_UPDATE.name())

                        .requestMatchers("/api/v1/tl/**").hasAnyRole(CEO.name(), MANAGER.name(), TEAM_LEADER.name())
                        .requestMatchers(GET, "/api/v1/tl/**").hasAnyAuthority(CEO_READ.name(), MANAGER_READ.name())
                .anyRequest().authenticated())
                .sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .logout(logout-> logout.logoutUrl("/api/v1/auth/logout")
                        .addLogoutHandler(logoutHandler)
                        .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext()));
        return http.build();
    }
}
