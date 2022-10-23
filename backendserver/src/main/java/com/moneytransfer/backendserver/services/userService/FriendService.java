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
    private UserRepo userRepo;

    public FriendService(FriendRepo friendRepo, UserRepo userRepo) {
        this.userRepo = userRepo;
        this.friendRepo = friendRepo;
    }

    public boolean requestFriend(String requesterEmail, String friendEmail) throws FriendException {
        if (userRepo.findByUserEmail(friendEmail).isEmpty()) {
            throw new FriendException(1); //friend not exists
        }
        if (friendRepo.checkRelationship(requesterEmail, friendEmail).isPresent()) {
            throw new FriendException(0); // relationship already exists
        }

        Friend friend = new Friend();
        friend.setFriendAEmail(requesterEmail);
        friend.setFriendBEmail(friendEmail);
        friend.setAccepted(false);

        friendRepo.save(friend);
        return true;
    }

    public boolean acceptFriend(String requesterEmail, String friendEmail) throws FriendException {
        if (userRepo.findByUserEmail(friendEmail).isEmpty()) {
            throw new FriendException(1); //friend not exists
        }
        if (friendRepo.checkRelationship(requesterEmail, friendEmail).isEmpty()) {
            throw new FriendException(0); // relationship already exists
        }
        friendRepo.updateAccepted(requesterEmail, friendEmail);
        return true;
    }

    public boolean declineFriend(String requesterEmail, String friendEmail) throws FriendException {
        if (userRepo.findByUserEmail(friendEmail).isEmpty()) {
            throw new FriendException(1); //friend not exists
        }
        if (friendRepo.checkRelationship(requesterEmail, friendEmail).isEmpty()) {
            throw new FriendException(2); // relationship does not exist
        }
        friendRepo.removeFriend(requesterEmail, friendEmail);
        return true;
    }

    public boolean removeFriend(String user1Email, String user2Email) throws FriendException {
        if (userRepo.findByUserEmail(user2Email).isEmpty()) {
            throw new FriendException(1); //friend not exists
        }
        if (friendRepo.checkRelationship(user1Email, user2Email).isEmpty()) {
            throw new FriendException(2); // relationship does not exist
        }
        friendRepo.removeFriend(user1Email, user2Email);
        return true;
    }

    public List<String> getFriendList(String userEmail) {
        List<Friend> fList = friendRepo.getUserFriends(userEmail);
        List<String> retList = new ArrayList<>();
        for (Friend friend : fList) {
            retList.add(friend.getFriendAEmail().equals(userEmail)
                    ? friend.getFriendBEmail() : friend.getFriendAEmail());
        }
        return retList;
    }

    public List<String> getRequest(String userEmail) {
        List<Friend> fList = friendRepo.getPendingList(userEmail);
        List<String> retList = new ArrayList<>();
        for (Friend friend : fList) {
            retList.add(friend.getFriendAEmail());
        }
        return retList;
    }
}
