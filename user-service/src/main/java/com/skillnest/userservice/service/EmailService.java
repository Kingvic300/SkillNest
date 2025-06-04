package com.skillnest.userservice.service;

import org.springframework.scheduling.annotation.Async;

public interface EmailService {

    @Async
    void sendEmail(String to, String emailContent);
    @Async
    void sendResetPasswordEmail(String toEmail, String otp);
}
