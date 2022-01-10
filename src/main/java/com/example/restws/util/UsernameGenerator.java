package com.example.restws.util;

import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.Random;

@Component
public class UsernameGenerator {
    private final Random RANDOM = new SecureRandom();
    private final String ALPHABET = "0123456789abcdefghijklmnopqrstuvwxyABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public String generateUserId(int length) {
        return "USER-" + generateRandomString(length);
    }

    private String generateRandomString(int length) {
        StringBuilder randomString = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            randomString.append(ALPHABET.charAt(RANDOM.nextInt(ALPHABET.length())));
        }
        return randomString.toString();
    }
}
