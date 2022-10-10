package com.moneytransfer.backendserver.objects;

import com.moneytransfer.backendserver.Util;

import java.util.Random;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class User {
    private static final int SALT_SIZE = 16;

    private String email;
    private String saltedPwHash;

    private String salt;
    // hashed(input + salt) ==> stored

    private String phoneNumber;
    private double balance;

    /**
     * Creates an user with an email and a hashed password
     * @param email email account used to sign up
     * @param password user set password
     */
    public User(String email, String password) {
        this.email = email;
        makeHashSalt();
        saltedPwHash = makeSaltedVal(password);
    }

    /**
     * Generates a random string (i.e., salt)
     */
    private void makeHashSalt() {
        Random random = new Random();
        salt = random.ints(48, 123)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(16)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

    /**
     * Appends salt to password and hashes the modified password
     * @param input user-input password
     * @return hashed value of password + salt
     */
    private String makeSaltedVal(String input) {
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


    /**
     * Checks if the password is correct
     * @param input input of password user types in upon sign in
     * @return true if the password is correct, false otherwise
     */
    public boolean passwordChecker(String input) {
        return saltedPwHash.equals(makeSaltedVal(input));
    }


    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getEmail() {
        return email;
    }

    public String getSalt() { 
        return salt; 
    }

    public String getSaltedPwHash() {
        return saltedPwHash;
    }
}
