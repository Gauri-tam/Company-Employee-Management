package com.Management.controller;

import com.Management.jwtrequest.UserAuthenticateRequest;
import com.Management.jwtrequest.UserRegisterRequest;
import com.Management.jwtresponse.UserAuthenticateResponse;
import com.Management.jwtresponse.UserRegisterResponse;
import com.Management.services.JwtAuthenticationServices;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class JwtAuthenticationController {

    private final JwtAuthenticationServices jwtAuthenticationServices;

    @PostMapping("/register")
    public ResponseEntity<UserRegisterResponse> registration(
           @Valid @RequestBody UserRegisterRequest request, HttpServletRequest req) throws Exception {
        return ResponseEntity.ok(
        jwtAuthenticationServices.register(request, req));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<UserAuthenticateResponse> authenticate(
           @Valid @RequestBody UserAuthenticateRequest request) throws Exception {
        return ResponseEntity.ok(
                jwtAuthenticationServices.authenticate(request));
    }

    @PostMapping("/refresh")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        jwtAuthenticationServices.refreshToken(request, response);
    }
}
