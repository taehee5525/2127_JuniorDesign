package com.moneytransfer.backendserver.repositories;

import com.moneytransfer.backendserver.objects.User;

public interface TokenRepository {

    /**
     * save user with token into the store.
     * @param user user Object
     * @param token token String
     * @return true if successfully saved, else reuturn false.
     */
    boolean save(User user, String token);

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
     * @return UserObject, if it is not exist then return null.
     */
    User lookupUser(String token);

    /**
     * Iterate all the token we got and clear the expired token.
     * @param time limit
     * @return number of token expired and removed.
     */
    int removeExpiredToken(long time);

    /**
     * return number of token currently have.
     * @return size of token stored in store.
     */
    int size();
}
