package com.skillnest.userservice.mapper;

import com.skillnest.userservice.data.model.OTP;
import com.skillnest.userservice.dtos.request.OTPRequest;
import com.skillnest.userservice.dtos.response.OTPResponse;
import com.skillnest.userservice.util.OTPGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

public class OTPMapper {

    public static OTP mapToOTP(String email) {
        OTP otp = new OTP();
        otp.setId(UUID.randomUUID().toString());
        otp.setOtp(OTPGenerator.generateOtp());
        otp.setEmail(email);
        otp.setCreatedAt(LocalDateTime.now());
        otp.setExpiresAt(LocalDateTime.now().plusMinutes(2));
        return otp;
    }
    public static OTPResponse mapToOTPResponse(String message, String email) {
        OTPResponse otpResponse = new OTPResponse();
        otpResponse.setMessage(message);
        otpResponse.setEmail(email);
        return otpResponse;
    }
}
