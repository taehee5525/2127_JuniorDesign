package com.moneytransfer.backendserver.repositories;
import com.moneytransfer.backendserver.objects.User;

public interface UserRepository {
    boolean save(User user);
    String login(String email, String password);
    boolean logout(String token);
    User lookupUser(String phoneNumber);
}
