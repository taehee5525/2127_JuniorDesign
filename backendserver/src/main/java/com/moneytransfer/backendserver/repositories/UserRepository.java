package com.moneytransfer.backendserver.repositories;
import com.moneytransfer.backendserver.objects.User;

public interface UserRepository {
    /**
     * Registers an user to the system
     * @param user User object that holds information of the user to be signed up
     * @return true if sign up was successful, false otherwise
     */
    boolean save(User user);

    /**
     * User logs in to the system
     * @param email email address of the user attempting to log in
     * @param password password that user types in
     * @return user-specific token if login was successful, null otherwise
     */
    String login(String email, String password);

    /**
     * User logs out from the system
     * @param token token of the user attempting to sign out
     * @return true if successfully logged out, false otherwise
     */
    boolean logout(String token);

    /**
     * Looks up other user using registered phone number
     * @param phoneNumber phone number of the user to be looked up
     * @return User object if the user exists, null otherwise
     */
    User lookupUser(String phoneNumber);
}
