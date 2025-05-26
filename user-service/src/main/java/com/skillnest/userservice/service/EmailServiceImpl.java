package com.skillnest.userservice.service;

import com.skillnest.userservice.exception.EmailNotSentException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    private static final int OTP_EXPIRY_MINUTES = 2;

    @Async
    @Override
    public void sendEmail(String toEmail, String otp) {
        String subject = "Your OTP Code";
        String htmlContent = buildOtpHtmlTemplate(otp);
        sendHtmlEmail(toEmail, subject, htmlContent);
    }

    @Async
    @Override
    public void sendResetPasswordEmail(String toEmail, String otp) {
        String subject = "Reset Your Password";
        String htmlContent = buildResetPasswordHtmlTemplate(otp);
        sendHtmlEmail(toEmail, subject, htmlContent);
    }


    private void sendHtmlEmail(String to, String subject, String content) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

            helper.setFrom(fromEmail);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);

            mailSender.send(mimeMessage);
            log.info("Email sent to {}", to);

        } catch (MessagingException | MailException e) {
            log.error("error {}",e.getMessage());
            throw new EmailNotSentException("Failed to send email. Please try again later.");
        }
    }
    private String buildOtpHtmlTemplate(String otp) {
        return """
                <!DOCTYPE html>
                <html>
                <head>
                    <meta charset="UTF-8">
                    <title>Your OTP Code</title>
                </head>
                <body style="font-family: Arial, sans-serif;">
                    <div style="max-width: 600px; margin: auto; padding: 20px; background-color: #f4f4f4; border-radius: 10px;">
                        <h2 style="color: #333;">Your One-Time Password (OTP)</h2>
                        <p>Please use the OTP below to complete your action:</p>
                        <p style="font-size: 24px; font-weight: bold; color: #007BFF;">%s</p>
                        <p>This OTP is valid for <strong>%d minutes</strong>.</p>
                        <p>If you did not request this, you can safely ignore this email.</p>
                        <br/>
                        <p>Regards,<br/>SkillNest Team</p>
                    </div>
                </body>
                </html>
                """.formatted(otp, OTP_EXPIRY_MINUTES);
    }

    private String buildResetPasswordHtmlTemplate(String otp) {
        return """
                <!DOCTYPE html>
                <html>
                <head>
                    <meta charset="UTF-8">
                    <title>Reset Your Password</title>
                </head>
                <body style="font-family: Arial, sans-serif;">
                    <div style="max-width: 600px; margin: auto; padding: 20px; background-color: #fefefe; border-radius: 10px;">
                        <h2 style="color: #333;">Reset Password Request</h2>
                        <p>Use the OTP below to reset your password:</p>
                        <p style="font-size: 24px; font-weight: bold; color: #DC3545;">%s</p>
                        <p>This code will expire in <strong>%d minutes</strong>.</p>
                        <p>If you didn't request this, ignore this email.</p>
                        <br/>
                        <p>Regards,<br/>SkillNest Security</p>
                    </div>
                </body>
                </html>
                """.formatted(otp, OTP_EXPIRY_MINUTES);
    }
}
