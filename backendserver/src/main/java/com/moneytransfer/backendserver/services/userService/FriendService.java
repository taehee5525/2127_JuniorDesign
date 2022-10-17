package com.moneytransfer.backendserver.services.userService;

import com.moneytransfer.backendserver.exceptions.FriendException;
import com.moneytransfer.backendserver.objects.Friend;
import com.moneytransfer.backendserver.repositories.FriendRepo;
import com.moneytransfer.backendserver.repositories.UserRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class FriendService {

    private FriendRepo friendRepo;

    public FriendService(FriendRepo friendRepo) {
        this.friendRepo = friendRepo;
    }

    public boolean requestFriend(String requesterEmail, String friendEmail) throws FriendException {
        if (friendRepo.checkRelationship(requesterEmail, friendEmail).isPresent()) {
            throw new FriendException(0);
        }

        Friend friend = new Friend();
        friend.setFriendAEmail(requesterEmail);
        friend.setFriendBEmail(friendEmail);
        friend.setAccepted(false);

        friendRepo.save(friend);
        return true;
    }

    public boolean acceptFriend(String requesterEmail, String friendEmail) {
        friendRepo.updateAccepted(requesterEmail, friendEmail);
        return true;
    }

    public boolean declineFriend(String requesterEmail, String friendEmail) {
        friendRepo.removeFriend(requesterEmail, friendEmail);
        return true;
    }

    public boolean removeFriend(String user1Email, String user2Email) {
        friendRepo.removeFriend(user1Email, user2Email);
        return true;
    }

    public List<Friend> getFriendList(String userEmail) {
        return friendRepo.getUserFriends(userEmail);
    }

    public List<Friend> getRequest(String userEmail) {
        return friendRepo.getPendingList(userEmail);
    }
}
