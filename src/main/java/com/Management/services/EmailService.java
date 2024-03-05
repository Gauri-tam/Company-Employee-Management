package com.Management.services;

import com.Management.entity.EmailDetails;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Properties;


@Service
@RequiredArgsConstructor
public class EmailService {

    @Value("${spring.mail.username}")
    private String sender ;

    @Value("${spring.mail.password}")
    private String password;

    @Value("${spring.mail.host}")
    private String host;

    @Value("${spring.mail.port}")
    private String port;

    @Value("${spring.mail.properties.mail.smtp.auth}")
    private String auth;

    @Value("${spring.mail.properties.mail.smtp.starttls.enable}")
    private String ttl;

    public String sentSimpleMail(EmailDetails details) throws MessagingException {

        Properties properties = System.getProperties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", port);
        properties.put("mail.smtp.auth", auth);
        properties.put("mail.smtp.starttls.enable", ttl);

        Session session = Session.getDefaultInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(sender, password);
            }
        });

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(sender);
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(details.getRecipient()));
            message.setSubject(details.getSubject());
            message.setText(details.getMsgBody());

            String path="D:\\Workspace\\POCs\\images\\wee.png";
            MimeMultipart mimeMultipart = new MimeMultipart();
            MimeBodyPart textMime = new MimeBodyPart();
            MimeBodyPart fileMime = new MimeBodyPart();
            try {
                textMime.setText(details.getMsgBody());
                File file=new File(path);
                fileMime.attachFile(file);
                mimeMultipart.addBodyPart(textMime);
                mimeMultipart.addBodyPart(fileMime);
            } catch (Exception e) {
                e.printStackTrace();
            }
            message.setContent(mimeMultipart);
            Transport.send(message);

            return "Mail Send!";

        }catch (Exception e) {
            e.printStackTrace();
            return "Getting Error!";
        }
    }

    // to authenticate the user

    public String sentEmailToUser(String email) {

        final String subject = "User is login SuccessFully!!";
        final String msg = "You are Authenticate To use this Api...\n" +
                "Now You Have an Access Token for 10 Minute...\n" +
                "And You Have an refresh Token for 24 Hours...\n"
                +"Thank you!";
        if (!email.isEmpty()){
            sentEmail(email, subject, msg);
            System.out.println("Email Sent!...");
        }
        return email;
    }

    // to Connecting to the User (Ceo) Entity;

    public String sentCeoMail(String email) {

        final String subject = "User Registered!!";
        final String msg = "You are Register as CEO...";

        if (!email.isEmpty()){
            sentEmail(email, subject, msg);
        }
        return email;
    }

 // to Connecting to the User (Manager) Entity;

    public String sentManagerMail(String email) {

        final String subject = "User Registered!!";
        final String msg = "You are Register as Manager...";

        if (!email.isEmpty()){
            sentEmail(email, subject, msg);
        }
        return email;
    }

    // to Connecting to the User (Tame Leader) Entity;
    public String sentTeamLeaderMail(String email) {

        final String subject = "User is Registered!!";
        final String msg = "You are Register as Team Leader...";

        if (!email.isEmpty()){
            sentEmail(email, subject, msg);
        }
        return email;
    }

    // for logout from user
    public String sentLogoutMail(String email) {

        final String subject = "User is logout SuccessFully!!";
        final String msg = "You Are no longer User Now..\n"+
                "Thank You!";

        if (!email.isEmpty()){
            sentEmail(email, subject, msg);
        }
        return email;
    }

    //Multiple use
    private String sentEmail(String email, String subject, String msg){
        Properties properties = System.getProperties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", port);
        properties.put("mail.smtp.auth", auth);
        properties.put("mail.smtp.starttls.enable", ttl);

        Session session = Session.getDefaultInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(sender, password);
            }
        });
        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(sender);
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
            message.setSubject(subject);
            message.setText(msg);

            String path="D:\\Workspace\\POCs\\images\\wee.png";
            MimeMultipart mimeMultipart = new MimeMultipart();
            MimeBodyPart textMime = new MimeBodyPart();
            MimeBodyPart fileMime = new MimeBodyPart();
            try {
                textMime.setText(msg);
                File file=new File(path);
                fileMime.attachFile(file);
                mimeMultipart.addBodyPart(textMime);
                mimeMultipart.addBodyPart(fileMime);
            } catch (Exception e) {
                e.printStackTrace();
            }
            message.setContent(mimeMultipart);
            Transport.send(message);

            return email;

        }catch (Exception e) {
            e.printStackTrace();
            return "Getting Error!";
        }
    }
}
