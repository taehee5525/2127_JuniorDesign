package com.moneytransfer.backendserver.objects;

public class Friend {

    private String friendAEmail; // A friend who want to be friend of B
    private String friendBEmail;
    private boolean accepted;

    public String getFriendAEmail() {
        return friendAEmail;
    }

    public void setFriendAEmail(String friendAEmail) {
        this.friendAEmail = friendAEmail;
    }

    public String getFriendBEmail() {
        return friendBEmail;
    }

    public void setFriendBEmail(String friendBEmail) {
        this.friendBEmail = friendBEmail;
    }

    public boolean isAccepted() {
        return accepted;
    }

    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }
}
