package com.moneytransfer.backendserver.controllers;

import com.moneytransfer.backendserver.Util;
import com.moneytransfer.backendserver.objects.User;
import com.moneytransfer.backendserver.repositories.TempUserRepository;
import com.moneytransfer.backendserver.repositories.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import org.json.JSONObject;

@Controller
public class CreateUserContoller {

    UserRepository usrRepo = new TempUserRepository();

    @PostMapping(value = "users/mkuser")
    @ResponseBody
    public String userJoin(@RequestBody String data) throws UnsupportedEncodingException {

        JSONObject req = new JSONObject(Util.errorDecoder(data));
        User user = new User(req.get("email").toString(), req.get("password").toString());

        JSONObject ret = new JSONObject();

        if (usrRepo.save(user)) {
            ret.put("isSuccess", true);
        } else {
            ret.put("isSuccess", false);
            ret.put("reason", "user email already in use.");
        }

        return ret.toString();
    }



}
