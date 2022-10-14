package com.moneytransfer.backendserver.controllers;

import com.moneytransfer.backendserver.Util;
import com.moneytransfer.backendserver.objects.User;
import com.moneytransfer.backendserver.objects.User2;
import com.moneytransfer.backendserver.repositories.TempUserRepository;
import com.moneytransfer.backendserver.repositories.UserRepository;
import com.moneytransfer.backendserver.services.userService.CreateUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import org.json.JSONObject;

@Controller
public class CreateUserContoller {

    private CreateUserService createUserService;

    @Autowired
    public CreateUserContoller(CreateUserService createUserService) {
        this.createUserService = createUserService;
    }

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
