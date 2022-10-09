package com.moneytransfer.backendserver.repositories;

import com.moneytransfer.backendserver.objects.User;
import java.util.HashMap;
import java.util.Map;

public class TempUserRepository implements UserRepository {

    private static Map<String, User> db = new HashMap<>();
    private static Map<String, String> tokenMap = new HashMap<>();

    @Override
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
}
