package com.Management.controller;

import com.Management.jwtrequest.UserAuthenticateRequest;
import com.Management.jwtrequest.UserRegisterRequest;
import com.Management.jwtresponse.UserAuthenticateResponse;
import com.Management.jwtresponse.UserRegisterResponse;
import com.Management.services.JwtAuthenticationServices;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/auth")
@Tag(name ="JWT Authentication Controller", description = "it will user for only creating Ceo, Authenticate User(CEO, MANAGER, TEAM LEADER) And Refresh token")
@RequiredArgsConstructor
public class JwtAuthenticationController {

    private final JwtAuthenticationServices jwtAuthenticationServices;

    @PostMapping("/register")
    @Operation(summary = "Register the User")
    public ResponseEntity<UserRegisterResponse> registration(
           @Valid @RequestBody UserRegisterRequest request, HttpServletRequest req) throws Exception {
        return ResponseEntity.ok(
        jwtAuthenticationServices.register(request, req));
    }

    @PostMapping("/authenticate")
    @Operation(summary = "Authenticate the User")
    public ResponseEntity<UserAuthenticateResponse> authenticate(
           @Valid @RequestBody UserAuthenticateRequest request) throws Exception {
        return ResponseEntity.ok(
                jwtAuthenticationServices.authenticate(request));
    }

    @PostMapping("/refresh")
    @Operation(summary = "refresh The Authenticate Token")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        jwtAuthenticationServices.refreshToken(request, response);
    }
}
