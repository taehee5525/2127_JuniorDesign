package com.moneytransfer.backendserver;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class Util {

    public final static long TOKEN_EXPIRE_LIMIT = 300000; // 300000ms == 300s = 5mins
    public static final String FSP_NAME = "techpayfsp";
    public static final Long RESPONSE_WAIT_LIMIT = 10000L;
    public static final Long FSP_RESPONSE_WAIT_LIMIT = 5000L;
    public final static String CURRENCY = "USD";
    public final static ZonedDateTime now = ZonedDateTime.now();
    public final static String date = now.format(DateTimeFormatter.RFC_1123_DATE_TIME);
    public static final Map<String, String> urlMap = new HashMap<String, String>() {
        {
            put("Central_Ledger", "http://central-ledger.local");
            put("Account_Lookup_Service", "http://account-lookup-service.local");
            put("endPoint", "http://localhost:8080/");
            put("Quoting_Service", "http://quoting-service.local");
            put("Transaction_Request_Service", "http://transaction-request-service.local");
        }
    };

    /**
     * For temp usage only, this will be updated after tested.
     */
    public static final Map<String, String> headerMap = new HashMap<String, String>() {
        {
            put("participantsAccept", "application/vnd.interoperability.participants+json;version=1");
            put("participantsContentType", "application/vnd.interoperability.participants+json;version=1.0");
            put("tempHeaderDate", date);
            put("partiesAccept", "application/vnd.interoperability.parties+json;version=1");
            put("partiesContentType", "application/vnd.interoperability.parties+json;version=1.0");
            put("quotesAccept", "application/vnd.interoperability.quotes+json;version=1");
            put("quotesContentType", "application/vnd.interoperability.quotes+json;version=1.0");
            put("transactionAccept", "application/vnd.interoperability.transactionRequest+json;version=1");
            put("transactionContentType", "application/vnd.interoperability.transactionRequest+json;version=1.0");
        }
    };

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

    /**
     * Appends salt to password and hashes the modified password
     * @param input user-input password
     * @return hashed value of password + salt
     */
    public static String makeSaltedVal(String input, String salt) {
        String temp = input + salt;
        String ret = "0";
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(temp.getBytes());
            byte[] hashedVal = md.digest();
            ret = Util.byteArrToString(hashedVal);
        } catch (NoSuchAlgorithmException e) {
        } finally {
            return ret;
        }
    }

}
