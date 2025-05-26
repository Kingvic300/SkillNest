package com.skillnest.userservice.util;

import java.security.SecureRandom;

public class OTPGenerator {
    private static final SecureRandom rnd = new SecureRandom();
    public static String generateOtp() {
        int otp = 100000 + rnd.nextInt(900000);
        return String.valueOf(otp);
    }
}
