package com.example.test2;

public class Utility {
    public static String token;

    public static String userEmailAddr;

    public static String userName;

    public static String friendEmail;

    public static String friendDelEmail;

    public static int numOfFriends;

    public static String transactionID;

    public static double userBalance;

    public static String friendReqEmail;

    public static long timestamp;

    public static boolean isExpiredToken(String token) {
        long startTime = Utility.timestamp; //time when token started
        long estimatedTime = System.currentTimeMillis() - startTime; //finds difference
        return estimatedTime > 300000; //token is expired
    }
}
