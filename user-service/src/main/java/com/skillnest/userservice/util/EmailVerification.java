package com.skillnest.userservice.util;


import com.skillnest.userservice.exception.InvalidEmailException;

public class EmailVerification {

    public static String emailVerification(String email) {
        String regex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        if (email.matches(regex)) {
            return email;
        } else {
            throw new InvalidEmailException("Wrong email address");
        }
    }
}