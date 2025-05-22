package com.skillnest.userservice.service;

import com.skillnest.userservice.exception.EmailNotSentException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailServiceImpl implements EmailService{
    private static final Logger LOGGER = LoggerFactory.getLogger(EmailServiceImpl.class);
    private final JavaMailSender mailSender;

    @Async
    @Override
    public void sendEmail(String to, String otp) {
        String emailContent = "<p>Hello,</p>" +
                "<p>Your OTP To Confirm your email address:</p>" +
                "<h2>" + otp + "</h2>" +
                "<p>This OTP will expire in 2 minutes.</p>";
        sendMimeEmail(to, "Confirm your email", emailContent);
    }

    @Async
    @Override
    public void sendResetPasswordEmail(String toEmail, String otp) {
        String emailContent = "<p>Hello,</p>" +
                "<p>Your OTP to reset your password:</p>" +
                "<h2>" + otp + "</h2>" +
                "<p>This OTP will expire in 2 minutes.</p>";

        sendMimeEmail(toEmail, "Reset Your Password", emailContent);
    }

    private void sendMimeEmail(String to, String subject, String content) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            log.error("pass 1");
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);
            helper.setFrom("oladimejivictor611@gmail.com");
            log.error("pass 3");
            mailSender.send(mimeMessage);
            log.error("pass");
            LOGGER.info("Email sent to {}", to);

        } catch (MessagingException | MailException e) {
            log.error("the error is ==> {}",e.getMessage());
            throw new EmailNotSentException("Failed to send email. Please try again later.");
        }
    }
}
