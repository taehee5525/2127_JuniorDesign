package com.moneytransfer.backendserver;

public class Util {
    public static String byteArrToString(byte[] temp) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < temp.length; i++) {
            sb.append(String.format("%02x", temp[i]));
        }
        return sb.toString();
    }
}
