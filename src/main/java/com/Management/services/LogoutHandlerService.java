package com.Management.services;

import com.Management.repository.TokenRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

import java.io.PrintWriter;
import java.io.Writer;

@Service
@RequiredArgsConstructor
public class LogoutHandlerService implements LogoutHandler {

    private final TokenRepository tokenRepository;

    @SneakyThrows
    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        String authHeader = request.getHeader("Authorization");
        String token;
        if (authHeader == null || !authHeader.startsWith("Bearer ")){
            return;
        }
        token = authHeader.substring(7);
       var userToken = tokenRepository.findUserByToken(token).orElseThrow();
        if (userToken != null){
            userToken.setRevoked(true);
            userToken.setExpired(true);
        }
        tokenRepository.save(userToken);
        PrintWriter writer = response.getWriter();
        writer.println("User Logout!");
    }
}
