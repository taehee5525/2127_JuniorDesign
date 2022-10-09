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

    UserRepository store = new TempUserRepository();


    @PostMapping(value = "users/mkuser")
    @ResponseBody
    public String userJoin(@RequestBody String data) throws UnsupportedEncodingException {

        JSONObject req = new JSONObject(Util.errorDecoder(data));
        User user = new User(req.get("email").toString(), req.get("password").toString());

        JSONObject ret = new JSONObject();

        if (store.save(user)) {
            ret.put("isSuccess", true);
        } else {
            ret.put("isSuccess", false);
        }

        return ret.toString();
    }


    @PostMapping("users/userlogin")
    @ResponseBody
    public String userLogin(@RequestBody String data) throws UnsupportedEncodingException {

        JSONObject req = new JSONObject(Util.errorDecoder(data));
        String token = store.login(req.get("email").toString(), req.get("password").toString());
        JSONObject res = new JSONObject();

        if (token == null) {
            res.put("isSuccess", false);
            res.put("token", "");
        } else {
            res.put("isSuccess", true);
            res.put("token", token);
        }

        return res.toString();
    }



    class LoginResponse {
        boolean isSuccess;
        String token;
    }
    class JoinResponse {
        public void setSuccess(boolean success) {
            isSuccess = success;
        }

        boolean isSuccess;
    }
}
