package com.moneytransfer.backendserver.repositories;

import com.moneytransfer.backendserver.objects.Friend;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FriendRepo {

    Friend save(Friend friendEdge);

    List<Friend> getUserFriends(String user);

    List<Friend> getPendingList(String user);

    Optional<Friend> checkRelationship(String user1, String user2);

    void updateAccepted(String user1, String user2);

    void removeFriend(String user1, String user2);

    List<Friend> findAll();

}
