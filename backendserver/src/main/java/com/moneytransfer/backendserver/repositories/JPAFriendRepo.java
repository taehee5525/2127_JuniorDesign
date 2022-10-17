package com.moneytransfer.backendserver.repositories;

import com.moneytransfer.backendserver.objects.Friend;
import com.moneytransfer.backendserver.objects.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface JPAFriendRepo extends JpaRepository<Friend, Long>, FriendRepo{

    @Override
    Friend save(Friend friendEdge);

    @Override
    @Query("SELECT Friend FROM Friend f " +
            "WHERE f.accepted = true " +
            "and (f.friendAEmail = :email or f.friendBEmail = :email)")
    List<Friend> getUserFriends(@Param("email") String user);

    @Override
    @Query("SELECT Friend FROM Friend f " +
            "WHERE f.accepted = false " +
            "and f.friendBEmail = :email")
    List<Friend> getPendingList(@Param("email") String user);

    @Override
    @Query("SELECT Friend FROM Friend f " +
            "WHERE (f.friendAEmail = :user1 and f.friendBEmail = :user2)" +
            "or (f.friendAEmail = :user2 and f.friendBEmail = :user1)")
    Optional<Friend> checkRelationship(@Param("user1") String user1
            , @Param("user2") String user2);

    @Override
    @Modifying
    @Query("update Friend set accepted = true where friendAEmail = :user1 and friendBEmail = :user2")
    void updateAccepted(@Param("user1") String user1, @Param("user2") String user2);

    @Override
    @Modifying
    @Query("delete from Friend f where f.friendAEmail = :user1 and f.friendBEmail = :user2")
    void removeFriend(@Param("user1") String user1, @Param("user2") String user2);

    @Override
    List<Friend> findAll();
}
