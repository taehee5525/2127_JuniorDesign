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
     * remove user with assigned token.
     * @param email user email
     * @return true if successfully saved, else return false.
     */
    boolean remove(String email);

    /**
     * lookup user information from the store using email value,
     * and return the user object.
     * @param email email String.
     * @return UserObject, if it is not exist then return null.
     */
    User lookupUser(String email);

    /**
     * return number of user currently have.
     * @return size of user stored in store.
     */
    int size();
}
