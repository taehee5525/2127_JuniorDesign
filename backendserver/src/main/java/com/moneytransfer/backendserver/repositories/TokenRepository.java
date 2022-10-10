package com.moneytransfer.backendserver.repositories;

import com.moneytransfer.backendserver.objects.User;

public interface TokenRepository {

    /**
     * save user with token into the store.
     * @param userEmail userEmail String
     * @param token token String
     * @param currentTime currentTime
     * @return true if successfully saved, else return false.
     */
    boolean save(String userEmail, String token, String currentTime);

    /**
     * remove user with assigned token.
     * @param token token String
     * @return true if successfully saved, else return false.
     */
    boolean remove(String token);

    /**
     * lookup user information from the store using token value,
     * and return the user object.
     * @param token token String.
     * @return userEmail, if it is not exist then return null.
     */
    String getUserEmail(String token);

    /**
     * getter for TimeString
     * @param token token
     * @return time the token was referred for the last time
     */
    String getTime(String token);

    /**
     * return number of token currently have.
     * @return size of token stored in store.
     */
    int size();
}
