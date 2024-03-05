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
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class JwtAuthenticationServices {

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final AuthenticationManager authenticationManager;

    private final EmailService emailService;

    private final TokenRepository tokenRepository;

    private final JwtServices jwtServices;

    public UserRegisterResponse register(UserRegisterRequest request, HttpServletRequest req) throws Exception {
        final String header = req.getHeader(HttpHeaders.AUTHORIZATION);
            if ( header == null ){
                var user = User.builder()
                        .firstName(request.getFirstName())
                        .lastName(request.getLastName())
                        .email(emailService.sentCeoMail(request.getEmail()))
                        .password(bCryptPasswordEncoder.encode(request.getPassword()))
                        .role(Role.CEO)
                        .build();
                Optional<User> newUser = userRepository.findByEmail(request.getEmail());
                if (newUser.isPresent()){
//                throw new Exception("This User Email Is Already Present In DataBase!");
                    return UserRegisterResponse.builder()
                            .userName(request.getFirstName()+" "+request.getLastName())
                            .message("This User Email Is Already Present In DataBase")
                            .build();
                }
                userRepository.save(user);
                return UserRegisterResponse.builder()
                        .userName(request.getFirstName()+" "+request.getLastName())
                        .message("Central Executive Officer is added to DataBase")
                        .build();
            }else {
                return UserRegisterResponse.builder()
                        .userName(request.getFirstName()+" "+request.getLastName())
                        .message("Authorization must be Empty!")
                        .build();

            }
    }

    public UserAuthenticateResponse authenticate(UserAuthenticateRequest request) throws Exception {
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    request.getUsername(),
                    request.getPassword()));
        }catch ( Exception e){
           throw new Exception("Bad Credential");
        }
        var user = userRepository.findByEmail(emailService.sentEmailToUser( request.getUsername())).orElseThrow();
        var access = jwtServices.generateAccessToken(user);
        var refresh = jwtServices.generateRefreshToken(user);
        revokeAllToken(user); // always revoke first than store it
        storeUserToken(user, access);
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

    private void revokeAllToken(User user) {
        var userToken = tokenRepository.findTokenByUserId(user.getUserId());
        if (userToken.isEmpty()){return; }
        userToken.forEach(t->{
            t.setExpired(true);
            t.setRevoked(true);
            tokenRepository.saveAll(userToken);
        });
    }

    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String userName;
        if (authHeader == null || !authHeader.startsWith("Bearer ")){
            return;
        }

        refreshToken = authHeader.substring(7);
        userName = jwtServices.extractUserName(refreshToken);
        if (userName != null) {
            var user = userRepository.findByEmail(emailService.sentEmailToUser(userName)).orElseThrow();
            var accessToken = jwtServices.generateAccessToken(user);
            revokeAllToken(user);
            storeUserToken(user, accessToken);
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
