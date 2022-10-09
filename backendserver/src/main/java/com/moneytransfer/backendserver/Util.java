package com.moneytransfer.backendserver;

public class Util {
    /**
     * Converts an array of bytes to a String.
     * @param temp byte array to be converted
     * @return String coverted from byte array
     */
    public static String byteArrToString(byte[] temp) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < temp.length; i++) {
            sb.append(String.format("%02x", temp[i]));
        }
        return sb.toString();
    }

    public static String errorDecoder(String input) {
        return null;
    }
}
