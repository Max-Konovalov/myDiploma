package ru.mkonovalov.jurdoc.utils;

import java.security.SecureRandom;
import java.util.Random;

public class GeneratorUtils {
    public static final char[] EN_UPPERCASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
    public static final char[] NUMBERS = "1234567890".toCharArray();
    public static final char[] EN_LOWERCASE = "abcdefghijklmnopqrstuvwxyz".toCharArray();

    public static String createRandomString(int length, char[] ... availableSymbols){
        String charsString = "";
        for (char[] characters : availableSymbols) {
            charsString = charsString.concat(new String(characters));
        }

        char[] chars = charsString.toCharArray();

        StringBuilder sb = new StringBuilder();
        Random random = new SecureRandom();
        for (int i = 0; i < length; i++) {
            char c = chars[random.nextInt(chars.length)];
            sb.append(c);
        }

        return sb.toString();
    }
}
