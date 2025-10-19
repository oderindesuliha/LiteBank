package org.peejay.joblync.services.impl;

import org.peejay.joblync.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;

    @Autowired
    public EmailServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void sendRegistrationEmail(String to, String firstName, String temporaryPassword) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Welcome to JobLync - Account Registration");
        message.setText(
                "Dear " + firstName + ",\n\n" +
                        "Welcome to JobLync! Your account has been successfully created.\n\n" +
                        "Here are your login credentials:\n" +
                        "Email: " + to + "\n" +
                        "Temporary Password: " + temporaryPassword + "\n\n" +
                        "Please log in and change your password immediately.\n\n" +
                        "Best regards,\n" +
                        "The JobLync Team"
        );
        mailSender.send(message);
    }

    @Override
    public void sendPasswordResetEmail(String to, String firstName, String resetToken) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("JobLync - Password Reset Request");
        message.setText(
                "Dear " + firstName + ",\n\n" +
                        "We received a request to reset your password for your JobLync account.\n\n" +
                        "To reset your password, please click on the following link:\n" +
                        "http://localhost:8080/reset-password?token=" + resetToken + "\n\n" +
                        "If you did not request a password reset, please ignore this email.\n\n" +
                        "This link will expire in 24 hours.\n\n" +
                        "Best regards,\n" +
                        "The JobLync Team"
        );
        mailSender.send(message);
    }

    @Override
    public void sendPasswordEmail(String to, String temporaryPassword) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("JobLync - New Temporary Password");
        message.setText(
                "Dear User,\n\n" +
                        "Your password has been reset. Here is your new temporary password:\n\n" +
                        "Temporary Password: " + temporaryPassword + "\n\n" +
                        "Please log in and change your password immediately.\n\n" +
                        "Best regards,\n" +
                        "The JobLync Team"
        );
        mailSender.send(message);
    }
}