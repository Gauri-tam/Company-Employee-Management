package com.Management.services;

import com.Management.enamurate.Role;
import com.Management.entity.User;
import com.Management.jwtrequest.UserRegisterRequest;
import com.Management.jwtresponse.UserRegisterResponse;
import com.Management.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CEO_Services {

    @Value("${security.jwt.username}")
    private String username; //ceo-user

    @Value("${security.jwt.password}")
    private String password; //ceo-password

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final UserRepository userRepository;


    public UserRegisterResponse createManager(UserRegisterRequest request, HttpServletRequest req) {
        // basic authentication we want it so just do like authentication;
        assert req != null;
        String header = req.getHeader("Authorization");
      //  Basic Y2VvLXVzZXI6Y2VvLXBhc3N3b3Jk
        if (header != null && header.startsWith("Basic ")){

            String pair = new String(Base64.decodeBase64(header.substring(6)));

            String userName=pair.split(":")[0];
            String passWord=pair.split(":")[1];

            if (userName.equals(username) && passWord.equals(password)){
                var user = User.builder()
                        .firstName(request.getFirstName())
                        .lastName(request.getLastName())
                        .email(request.getEmail())
                        .password(bCryptPasswordEncoder.encode(request.getPassword()))
                        .role(Role.MANAGER)
                        .build();
                Optional<User> newUser = userRepository.findByEmail(request.getEmail());
                if (newUser.isPresent()){
                    return UserRegisterResponse.builder()
                            .userName(request.getFirstName()+ " " +request.getLastName())
                            .message("This User Is Already Present!")
                            .build();
                }
                userRepository.save(user);
                return UserRegisterResponse.builder()
                        .userName(request.getFirstName()+" "+request.getLastName())
                        .message("Manager is Added To Database!")
                        .build();
            } else {
                return UserRegisterResponse.builder()
                        .userName(request.getFirstName()+" "+request.getLastName())
                        .message("Username or Password is Invalid!")
                        .build();
            }
        }
        else {
            return UserRegisterResponse.builder()
                    .userName(request.getFirstName()+" "+request.getLastName())
                    .message("Header Not Should Be Null!")
                    .build();
        }
    }

    // do this

    public UserRegisterResponse createTeamLead(UserRegisterRequest request, HttpServletRequest req) throws Exception {
        assert req != null;
        String authHeader = req.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Basic ")){

            String usernamePassword = new String(Base64.decodeBase64(authHeader.substring(6)));

            int separatorIndex = usernamePassword.indexOf(':');

            var userName = usernamePassword.substring(0, separatorIndex);

            var passWord = usernamePassword.substring(separatorIndex + 1);

            if(userName.equals(username) && passWord.equals(password) ) {
                var user = User.builder()
                        .firstName(request.getFirstName())
                        .lastName(request.getLastName())
                        .email(request.getEmail())
                        .password(bCryptPasswordEncoder.encode(request.getPassword()))
                        .role(Role.TEAM_LEADER)
                        .build();
                Optional<User> findUserByEmail = userRepository.findByEmail(request.getEmail());
                if (findUserByEmail.isPresent()){
                    throw new Exception("User is Present in Database");
                }
                userRepository.save(user);
                return UserRegisterResponse.builder()
                        .userName(request.getFirstName() + " " + request.getLastName())
                        .message("Team Leader Created Successfully!")
                        .build();
            } else {
                // if our  Password or UserName is not Correct in Header
                return UserRegisterResponse.builder()
                        .userName(request.getFirstName() + " " + request.getLastName())
                        .message("Access Denied! Check your credentials")
                        .build();
            }
        } else {
            // null or invalid Authorization header
            return UserRegisterResponse.builder()
                    .userName(request.getFirstName() + " " + request.getLastName())
                    .message("Access Denied! Authorization header Empty or invalid")
                    .build();
        }
    }
}
