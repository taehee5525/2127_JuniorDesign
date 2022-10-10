package com.moneytransfer.backendserver.repositories;

import com.moneytransfer.backendserver.objects.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TempTokenRepository implements TokenRepository{

    private static Map<String, List<String>> tokenMap = new HashMap<String, List<String>>();
    // K = Token, V = {User Email, TimeStamp}

    /**
     * save user with token into the store.
     *
     * @param userEmail   userEmail String
     * @param token       token String
     * @param currentTime currentTime
     * @return true if successfully saved, else reuturn false.
     */
    @Override
    public boolean save(String userEmail, String token, String currentTime) {
        if (tokenMap.containsKey(token)) {
            return false;
        } else {
            List<String> temp = new ArrayList<>();
            temp.add(userEmail);
            temp.add(currentTime);

            tokenMap.put(token, temp);
            return true;
        }
    }

    /**
     * remove user with assigned token.
     *
     * @param token token String
     * @return true if successfully saved, else return false.
     */
    @Override
    public boolean remove(String token) {
        if (!tokenMap.containsKey(token)) {
            return false;
        } else {
            return tokenMap.remove(token) != null;
        }
    }

    /**
     * lookup user information from the store using token value,
     * and return the user object.
     *
     * @param token token String.
     * @return userEmail, if it is not exist then return null.
     */
    @Override
    public String getUserEmail(String token) {
        return tokenMap.containsKey(token) ? tokenMap.get(token).get(0) : null;
    }

    /**
     * getter for TimeString
     *
     * @param token token
     * @return number of token expired and removed.
     */
    @Override
    public String getTime(String token) {
        return tokenMap.containsKey(token) ? tokenMap.get(token).get(1) : null;
    }

    /**
     * return number of token currently have.
     *
     * @return size of token stored in store.
     */
    @Override
    public int size() {
        return tokenMap.size();
    }
}
