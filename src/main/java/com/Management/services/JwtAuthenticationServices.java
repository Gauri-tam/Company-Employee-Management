package com.Management.services;

import com.Management.enamurate.Role;
import com.Management.enamurate.TokenType;
import com.Management.entity.Token;
import com.Management.entity.User;
import com.Management.jwtrequest.UserAuthenticateRequest;
import com.Management.jwtrequest.UserRegisterRequest;
import com.Management.jwtresponse.UserAuthenticateResponse;
import com.Management.jwtresponse.UserRegisterResponse;
import com.Management.repository.TokenRepository;
import com.Management.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class JwtAuthenticationServices {

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final AuthenticationManager authenticationManager;

    private final TokenRepository tokenRepository;

    private final JwtServices jwtServices;

    public UserRegisterResponse register(UserRegisterRequest request) {
        var user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(bCryptPasswordEncoder.encode(request.getPassword()))
                .role(Role.TL)
                .build();
        userRepository.save(user);
        return UserRegisterResponse.builder()
                .userName(request.getFirstName()+" "+request.getLastName())
                .message("Team Lead is Added to the DataBase!")
                .build();
    }

    public UserAuthenticateResponse authenticate(UserAuthenticateRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getUsername(),
                request.getPassword()));
        var user = userRepository.findByEmail(request.getUsername()).orElseThrow();
        var access = jwtServices.generateAccessToken(user);
        var refresh = jwtServices.generateAccessToken(user);
        storeUserToken(user, access);
        revokeALlToken(user);
        return UserAuthenticateResponse.builder()
                .accessToken(access)
                .refreshToken(refresh)
                .build();
    }

    private void storeUserToken(User user, String access) {
        var userToken = Token.builder()
                .token(access)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .user(user)
                .build();
        tokenRepository.save(userToken);
    }

    private void revokeALlToken(User user) {
        var userToken = tokenRepository.findTokenByUserId(user.getUserId());
        if (userToken.isEmpty()){return; }
        userToken.forEach(t->{
            t.setExpired(false);
            t.setRevoked(false);
        });
        tokenRepository.saveAll(userToken);
    }

    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken ;
        final String userName ;
        if (authHeader == null || !authHeader.startsWith("Bearer ")){
            return;
        }

        refreshToken = authHeader.substring(7);
        userName = jwtServices.extractUserName(refreshToken);
        if (userName != null) {
            var user = userRepository.findByEmail(userName).orElseThrow();
            var accessToken = jwtServices.generateAccessToken(user);
            storeUserToken(user, accessToken);
            revokeALlToken(user);
            if (jwtServices.isTokenValid(refreshToken, user)){
                var getRefreshToken = UserAuthenticateResponse.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build();
                new ObjectMapper().writeValue(response.getOutputStream(), getRefreshToken);
            }
        }

    }
}
