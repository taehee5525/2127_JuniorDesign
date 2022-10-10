package com.moneytransfer.backendserver.repositories;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface FriendRepository {

    /**
     * save user emails in store. userEmail1 and userEmail2 are friends
     * @param userEmail1 user1
     * @param userEmail2 user2
     * @return true if success, else return false
     */
    boolean save(String userEmail1, String userEmail2);

    /**
     * remove user in store, this is the case for user removes the account.
     * @param userEmail userEmail
     * @return true if success, else return false
     */
    boolean remove(String userEmail);


    /**
     * disconnect the friends relationship in store, this is the case for user removes
     * another users in his/her friendslist by email address.
     * @param userEmail1 user1
     * @param userEmail2 user2
     * @return true if success, else return false
     */
    boolean disconnect(String userEmail1, String userEmail2);

    /**
     * disconnect the friends relationship in store, this is the case for user removes
     * another users in his/her friendslist by phone number.
     * @param pnum1 phone number of user1
     * @param pnum2 phone number of user2
     * @return true if success, else return false
     */
    boolean disconnectByPhoneNum(String pnum1, String pnum2);

    /**
     * Getting the friends list from the store.
     * @param userEmail user
     * @return List of user's friends
     */
    List<String> getFriendList(String userEmail);

}
