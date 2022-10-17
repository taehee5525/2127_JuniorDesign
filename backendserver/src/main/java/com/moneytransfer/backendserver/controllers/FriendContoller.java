package com.moneytransfer.backendserver.controllers;

import com.moneytransfer.backendserver.Util;
import com.moneytransfer.backendserver.repositories.FriendRepo;
import com.moneytransfer.backendserver.services.userService.CreateUserService;
import com.moneytransfer.backendserver.services.userService.LoginService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.UnsupportedEncodingException;

@Controller
public class FriendContoller {

    @Autowired
    public FriendContoller(FriendRepo friendRepo) {
        this.friendRepo = friendRepo;
    }

    private FriendRepo friendRepo;

    @PostMapping(value = "users/mkuser")
    @ResponseBody
    public String userJoin(@RequestBody String data) throws UnsupportedEncodingException {

        JSONObject req = new JSONObject(Util.errorDecoder(data));
        JSONObject ret = new JSONObject();

        if (createUserService.createUser(req.get("email").toString(), req.get("password").toString()
                , req.opt("phone number") != null ? req.opt("phone number").toString() : null)) {
            ret.put("isSuccess", true);
        } else {
            ret.put("isSuccess", false);
            ret.put("reason", "user email already in use.");
        }

        return ret.toString();
    }
}
