package com.moneytransfer.backendserver.controllers;

import com.moneytransfer.backendserver.Util;
import com.moneytransfer.backendserver.exceptions.AuthException;
import com.moneytransfer.backendserver.exceptions.FriendException;
import com.moneytransfer.backendserver.objects.Friend;
import com.moneytransfer.backendserver.repositories.FriendRepo;
import com.moneytransfer.backendserver.services.userService.AuthService;
import com.moneytransfer.backendserver.services.userService.CreateUserService;
import com.moneytransfer.backendserver.services.userService.FriendService;
import com.moneytransfer.backendserver.services.userService.LoginService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.util.List;

@Controller
public class FriendContoller {

    @Autowired
    public FriendContoller(FriendService friendService, AuthService authService) {
        this.friendService = friendService;
        this.authService = authService;
    }

    private FriendService friendService;
    private AuthService authService;

    @PostMapping(value = "friends/request")
    @ResponseBody
    public String requestFriend(@RequestBody String data) throws UnsupportedEncodingException {

        JSONObject req = new JSONObject(Util.errorDecoder(data));

        String requesterToken = req.get("token").toString();
        String friendEmail = req.get("email").toString();

        String requesterEmail = new String();
        try {
            requesterEmail = authService.getUserIdFromToken(requesterToken);
        } catch (AuthException e) {
            return makeStatusResponse(e, null).toString();
        }

        try {
            friendService.requestFriend(requesterEmail, friendEmail);
        } catch (FriendException e) {
            return makeStatusResponse(null, e).toString();
        }

        JSONObject res = makeStatusResponse(null, null);
        return res.toString();
    }

    @GetMapping(value = "friends/getRequestList")
    @ResponseBody
    public String getRequestList(@RequestParam("token") String requesterToken) throws UnsupportedEncodingException {

        String requesterEmail = new String();
        try {
            requesterEmail = authService.getUserIdFromToken(requesterToken);
        } catch (AuthException e) {
            return makeStatusResponse(e, null).toString();
        }

        List<String> friendList = friendService.getRequest(requesterEmail);
        JSONObject res = makeStatusResponse(null, null);
        res.put("requestList", friendList);

        return res.toString();
    }

    @PostMapping(value = "friends/requestAccept")
    @ResponseBody
    public String acceptRequestFriend(@RequestBody String data) throws UnsupportedEncodingException {

        JSONObject req = new JSONObject(Util.errorDecoder(data));

        String requesterToken = req.get("token").toString();
        String friendEmail = req.get("email").toString();
        String acceptOrNot = req.get("accept").toString();

        String requesterEmail = new String();
        try {
            requesterEmail = authService.getUserIdFromToken(requesterToken);
        } catch (AuthException e) {
            return makeStatusResponse(e, null).toString();
        }

        try {
            if (acceptOrNot.equalsIgnoreCase("accept")) {
                friendService.acceptFriend(requesterEmail, friendEmail);
            } else {
                friendService.declineFriend(requesterEmail, friendEmail);
            }
        } catch (FriendException e) {
            return makeStatusResponse(null, e).toString();
        }

        JSONObject res = makeStatusResponse(null, null);
        return res.toString();
    }

    @DeleteMapping(value = "friends/removeFriend")
    @ResponseBody
    public String removeFriends(@RequestBody String data) throws UnsupportedEncodingException {

        JSONObject req = new JSONObject(Util.errorDecoder(data));

        String requesterToken = req.get("token").toString();
        String friendEmail = req.get("email").toString();

        String requesterEmail = new String();
        try {
            requesterEmail = authService.getUserIdFromToken(requesterToken);
        } catch (AuthException e) {
            return makeStatusResponse(e, null).toString();
        }
        try {
            friendService.removeFriend(requesterEmail, friendEmail);
        } catch (FriendException e) {
            return makeStatusResponse(null, e).toString();
        }
        JSONObject res = makeStatusResponse(null, null);
        return res.toString();
    }

    @GetMapping(value = "friends/getFriendList")
    @ResponseBody
    public String getFriendsList(@RequestParam("token") String requesterToken) throws UnsupportedEncodingException {

        String requesterEmail = new String();
        try {
            requesterEmail = authService.getUserIdFromToken(requesterToken);
        } catch (AuthException e) {
            return makeStatusResponse(e, null).toString();
        }

        List<String> friendList = friendService.getFriendList(requesterEmail);
        JSONObject res = makeStatusResponse(null, null);
        res.put("friendList", friendList);

        return res.toString();
    }

    private JSONObject makeStatusResponse(AuthException ae, FriendException fe){
        JSONObject res = new JSONObject();
        if (ae == null && fe == null) {
            res.put("isSuccess", true);
        } else {
            res.put("isSuccess", false);
        }

        if (ae != null) {
            res.put("tokenStatus", false);
            res.put("reason", ae.getErrorDescription());
        } else {
            res.put("tokenStatus", true);
        }

        if (fe != null) {
            res.put("friendRequestStatus", false);
            res.put("reason", fe.getErrorDescription());
        } else {
            res.put("friendRequestStatus", true);
        }

        return res;
    }
}
