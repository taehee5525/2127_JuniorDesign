package com.moneytransfer.backendserver.repositories;

import com.moneytransfer.backendserver.objects.User;

import java.util.HashMap;
import java.util.Map;

public class TempUserRepository implements UserRepository{

    private static Map<String, User> userMap = new HashMap<>();

    /**
     * Registers an user to the system
     *
     * @param user User object that holds information of the user to be signed up
     * @return true if sign up was successful, false otherwise
     */
    @Override
    public boolean save(User user) {
        String userEmail = user.getEmail();
        if (userMap.containsKey(userEmail)) {
            return false;
        } else {
            userMap.put(userEmail, user);
        }
        return true;
    }

    /**
     * remove user with assigned token.
     *
     * @param email user email
     * @return true if successfully saved, else return false.
     */
    @Override
    public boolean remove(String email) {
        if (userMap.containsKey(email)) {
            return userMap.remove(email) != null;
        } else {
            return false;
        }
    }

    /**
     * lookup user information from the store using email value,
     * and return the user object.
     *
     * @param email email String.
     * @return UserObject, if it is not exist then return null.
     */
    @Override
    public User lookupUser(String email) {
        if (userMap.containsKey(email)) {
            return userMap.get(email);
        } else {
            return null;
        }
    }

    /**
     * return number of user currently have.
     *
     * @return size of user stored in store.
     */
    @Override
    public int size() {
        return userMap.size();
    }
}
