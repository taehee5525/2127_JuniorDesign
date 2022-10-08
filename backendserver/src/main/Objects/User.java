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



    private void makeHashSalt() {
        Random random = new Random();
        salt = random.ints(48, 123)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(16)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }


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
