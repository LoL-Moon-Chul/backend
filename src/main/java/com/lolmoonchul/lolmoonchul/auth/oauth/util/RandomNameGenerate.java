package com.lolmoonchul.lolmoonchul.auth.oauth.util;

import java.util.Random;
import org.springframework.stereotype.Component;

@Component
public class RandomNameGenerate {

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int LENGTH = 8;


    public String generate() {
        StringBuilder nickname = new StringBuilder(LENGTH);
        Random random = new Random();

        nickname.setLength(0);
        for (int i = 0; i < LENGTH; i++) {
            nickname.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
        }

        return nickname.toString();
    }
}
