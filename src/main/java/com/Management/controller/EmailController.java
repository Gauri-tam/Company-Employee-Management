package com.Management.controller;

import com.Management.entity.EmailDetails;
import com.Management.services.EmailService;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/email")
@Tag(name = "Email Controller", description = "it will use for only testing purpose")
@Hidden
@RequiredArgsConstructor
public class EmailController {

    // http://localhost:8080/api/v1/email/sendMail

    private final EmailService emailService;

    // just for testing
    @PostMapping("/send")
    public ResponseEntity<String>
    sendEmail(@RequestBody EmailDetails details) throws MessagingException {
        String status
                = emailService.sentSimpleMail(details);
        return new ResponseEntity<>(status, HttpStatus.CREATED);
    }
}
