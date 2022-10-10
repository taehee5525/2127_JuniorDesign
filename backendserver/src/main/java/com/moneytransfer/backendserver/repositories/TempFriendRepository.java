package com.moneytransfer.backendserver.repositories;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TempFriendRepository implements FriendRepository {

    private static Map<String, List<String>> friendMap = new HashMap<String, List<String>>();
    // K = friendA, V = List of friends.

    /**
     * save user emails in store. userEmail1 and userEmail2 are friends
     *
     * @param userEmail1 user1
     * @param userEmail2 user2
     * @return true if success, else return false
     */
    @Override
    public boolean save(String userEmail1, String userEmail2) {

        if (friendMap.containsKey(userEmail1)) {
            friendMap.get(userEmail1).add(userEmail2);
        } else {
            List<String> temp = new ArrayList<>();
            temp.add(userEmail2);
            friendMap.put(userEmail1, temp);
        }

        if (friendMap.containsKey(userEmail2)) {
            friendMap.get(userEmail2).add(userEmail1);
        } else {
            List<String> temp = new ArrayList<>();
            temp.add(userEmail1);
            friendMap.put(userEmail2, temp);
        }

        return true;
    }

    /**
     * remove user in store, this is the case for user removes the account.
     *
     * @param userEmail userEmail
     * @return true if success, else return false
     */
    @Override
    public boolean remove(String userEmail) {
        if (!friendMap.containsKey(userEmail)) {
            return true; // he/she doesn't have friends...
        }
        List<String> friendList = friendMap.get(userEmail);

        int cnt = 0;

        for (String friend : friendList) {
            // FriendsList of user's friend
            List<String> friendsfriendlist = friendMap.get(friend);

//            for (int i = 0; i < friendsfriendlist.size(); i++) {
//                if (friendsfriendlist.get(i).equals(userEmail)) {
//                    friendsfriendlist.remove(i);
//                    cnt++;
//                    break;
//                }
//            }
            if (friendsfriendlist.remove(userEmail)) {
                cnt++;
                break;
            }
        }

//        int size = friendMap.size();
        int size = friendList.size();
        friendMap.remove(userEmail);

        return cnt == size;
    }

    /**
     * remove the relationship in store, this is the case for user removes
     * another users in his/her friendslist.
     *
     * @param userEmail1 user1
     * @param userEmail2 user2
     * @return true if success, else return false
     */
    public boolean remove(String userEmail1, String userEmail2) {
        if (!friendMap.containsKey(userEmail1) || !friendMap.containsKey(userEmail2)) {
            return false;
        }

        List<String> friendList1 = friendMap.get(userEmail1);
        List<String> friendList2 = friendMap.get(userEmail2);

//        boolean removeFriend1 = false;
//        boolean removeFriend2 = false;

//        for (int i = 0; i < friendList1.size(); i++) {
//            if (friendList1.get(i).equals(userEmail2)) {
//                friendList1.remove(i);
//                removeFriend2 = true;
//                break;
//            }
//        }

//        for (int i = 0; i < friendList2.size(); i++) {
//            if (friendList2.get(i).equals(userEmail1)) {
//                friendList2.remove(i);
//                removeFriend1 = true;
//                break;
//            }
//        }

//        return removeFriend1 & removeFriend2;

        return friendList1.remove(userEmail2) && friendList2.remove(userEmail1);
    }

    /**
     * Get the friends list from the store.
     *
     * @param userEmail user
     * @return List of user's friends and null if user has no friend
     */
    @Override
    public List<String> getFriendList(String userEmail) {
        if (!friendMap.containsKey(userEmail)) {
            return friendMap.get(userEmail);
        }
        return null;
    }
}
