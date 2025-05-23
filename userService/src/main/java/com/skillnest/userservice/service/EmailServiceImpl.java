package com.skillnest.userservice.service;

import com.sendgrid.*;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import io.github.cdimascio.dotenv.Dotenv;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@Slf4j
public class EmailServiceImpl implements EmailService {

    private final Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();

    private final String myEmail = dotenv.get("SENDGRID_FROM_EMAIL");
    private final String sendGridApiKey = dotenv.get("SENDGRID_API_KEY");

    private static final int OTP_EXPIRY_MINUTES = 2;

    @Async
    @Override
    public void sendEmail(String toEmail, String otp) {
        String htmlContent = buildOtpHtmlTemplate(otp);
        sendHtmlEmail(toEmail, "Your OTP Code", htmlContent);
    }

    @Async
    @Override
    public void sendResetPasswordEmail(String toEmail, String otp) {
        String htmlContent = buildResetPasswordHtmlTemplate(otp);
        sendHtmlEmail(toEmail, "Reset Your Password", htmlContent);
    }

    private void sendHtmlEmail(String toEmail, String subject, String htmlContent) {
        try {
            Email from = new Email(myEmail);
            Email to = new Email(toEmail);
            Content content = new Content("text/html", htmlContent);
            Mail mail = new Mail(from, subject, to, content);

            SendGrid sg = new SendGrid(sendGridApiKey);
            Request request = new Request();
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());

            Response response = sg.api(request);

            if (response.getStatusCode() < 200 || response.getStatusCode() >= 300) {
                log.error("Failed to send email. Status: {}", response.getStatusCode());
                throw new RuntimeException("Email sending failed");
            }

            log.info("Email sent to {}", toEmail);
        } catch (IOException e) {
            log.error("Email sending failed", e);
            throw new RuntimeException("Could not send email", e);
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
