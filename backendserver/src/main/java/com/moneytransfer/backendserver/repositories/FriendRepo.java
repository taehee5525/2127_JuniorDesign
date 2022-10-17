package com.moneytransfer.backendserver.repositories;

import com.moneytransfer.backendserver.objects.Friend;
import java.util.List;
import java.util.Optional;

public interface FriendRepo {

    Friend save(Friend user2);

    Optional<Friend> findByFriendAEmailOrFriendBEmail(String friendA);

    Optional<Friend> findByFriendBEmail(String friendB);

    List<Friend> findAll();

}
