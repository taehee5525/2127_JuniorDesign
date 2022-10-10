package com.moneytransfer.backendserver.repositories;

import com.moneytransfer.backendserver.Util;
import com.moneytransfer.backendserver.objects.User;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;


public class TempUserRepositoryX {

    private static Map<String, User> db = new HashMap<>();
    private static Map<String, String> tokenMap = new HashMap<>();

    public boolean save(User user) {
        String userEmail = user.getEmail();
        if (db.containsKey(userEmail)) {
            System.out.println("\nUser failed to join moneyTransferApp.");
            System.out.println("Reason: User Email already in use.");
            System.out.println("Total number of User: " + db.size());
            return false;
        } else {
            db.put(userEmail, user);
        }
        System.out.println("\nUser successfully joined moneyTransferApp");
        System.out.println("User Email: " + user.getEmail());
        System.out.println("User HASH-SALT: " + user.getSalt());
        System.out.println("User Salted Password: " + user.getSaltedPwHash());
        System.out.println("Total number of User: " + db.size());

        return true;
    }


    public String login(String email, String password) {

        System.out.println("\nUser Requested Login");
        System.out.println("User Email: " + email);

        if (db.containsKey(email)) {
            String temp = password + db.get(email).getSalt();
            String ret = "0";
            try {
                MessageDigest md = MessageDigest.getInstance("SHA-256");
                md.update(temp.getBytes());
                byte[] hashedVal = md.digest();
                ret = Util.byteArrToString(hashedVal);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }

            System.out.println(ret);

            if (!db.get(email).passwordChecker(ret)) {
                System.out.println("User failed to login: Incorrect password");
                return null;
            }

            Random random = new Random();
            String token = random.ints(48, 123)
                    .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                    .limit(32)
                    .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                    .toString();

            tokenMap.put(token, email);
            System.out.println("User successfully logged in");
            return token;
        } else {
            System.out.println("User failed to login: User not found");
            return null;
        }
    }


    public boolean logout(String token) {
        if (tokenMap.containsKey(token)) {
            tokenMap.remove(token);
            return true;
        } else {
            return false;
        }
    }

    public User lookupUser(String phoneNumber) {
        for (User user : db.values()) {
            if (user.getPhoneNumber().equals(phoneNumber)) {
                return user;
            }
        }
        return null;
    }
}
