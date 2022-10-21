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
    @Query(value = "SELECT * FROM Friend f " +
            "WHERE f.accepted = true " +
            "and (f.friend_A_Email = :email or f.friend_B_Email = :email)", nativeQuery = true)
    List<Friend> getUserFriends(@Param("email") String user);

    @Override
    @Query(value = "SELECT * FROM Friend f " +
            "WHERE f.accepted = false " +
            "and f.friend_B_Email = :email", nativeQuery = true)
    List<Friend> getPendingList(@Param("email") String user);

    @Override
    @Query(value = "SELECT * FROM Friend f " +
            "WHERE (f.friend_A_Email = :user1 and f.friend_B_Email = :user2)" +
            "or (f.friend_A_Email = :user2 and f.friend_B_Email = :user1)", nativeQuery = true)
    Optional<Friend> checkRelationship(@Param("user1") String user1
            , @Param("user2") String user2);

    @Override
    @Query(value = "SELECT * FROM Friend f " +
            "where f.accepted = true and" +
            "((f.friend_A_Email = :user1 and f.friend_B_Email = :user2)" +
            "or (f.friend_A_Email = :user2 and f.friend_B_Email = :user1))", nativeQuery = true)
    Optional<Friend> areTheyFriend(@Param("user1") String user1
            , @Param("user2") String user2);

    @Override
    @Modifying
    @Query("update Friend set accepted = true where friendAEmail = :user1 and friendBEmail = :user2")
    void updateAccepted(@Param("user1") String user1, @Param("user2") String user2);

    @Override
    @Modifying
    @Query("delete from Friend f where (f.friendAEmail = :user1 and f.friendBEmail = :user2) " +
            "or (f.friendAEmail = :user2 and f.friendBEmail = :user1)")
    void removeFriend(@Param("user1") String user1, @Param("user2") String user2);

    @Override
    List<Friend> findAll();
}
