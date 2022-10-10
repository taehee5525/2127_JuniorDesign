package com.moneytransfer.backendserver;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Random;

public class Util {
    /**
     * Converts an array of bytes to a String.
     * @param temp byte array to be converted
     * @return String converted from byte array
     */
    public static String byteArrToString(byte[] temp) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < temp.length; i++) {
            sb.append(String.format("%02x", temp[i]));
        }
        return sb.toString();
    }

    /**
     * Encoding the String from unknown response formatting
     * (we will use this until we figure out what cause the error).
     * The error occurs when a tester code requests a query to this server.
     * It has a string with an unknown format. But it can be solved by
     * this method.
     * We will only use this method while using the tester code.
     * @param input String req that we got from the tester
     * @return encoded String.
     * @throws UnsupportedEncodingException
     */
    public static String errorDecoder(String input) throws UnsupportedEncodingException {
        String temp = URLDecoder.decode(input, "ASCII").replaceAll("/", "");
        return temp.substring(0, temp.length() - 1);
    }

    /**
     * Create and return random String (alphabet/number)
     * @param len length of str
     * @return random str
     */
    public static String makeRandomString(int len) {
        Random random = new Random();
        return random.ints(48, 123)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(len)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }
}
