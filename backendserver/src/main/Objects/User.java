import java.util.Random;

public class User {
    private static final int SALT_SIZE = 16;

    private String email;
    private String saltedPwHash;

    private String salt;
    // hashed(input + salt) ==> stored

    private String phoneNumber;
    private double balance;


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

    public String getSalt() { return salt; }

    public String getSaltedPwHash() {
        return saltedPwHash;
    }
}
