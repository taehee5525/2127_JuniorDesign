package com.moneytransfer.backendserver.controllers;

import com.moneytransfer.backendserver.Util;
import com.moneytransfer.backendserver.exceptions.LoginException;
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
public class UserController {

    @Autowired
    public UserController(CreateUserService createUserService, LoginService loginService) {
        this.createUserService = createUserService;
        this.loginService = loginService;
    }

    private CreateUserService createUserService;
    private LoginService loginService;

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

    @PostMapping("users/userlogin")
    @ResponseBody
    public String userLogin(@RequestBody String data) throws UnsupportedEncodingException {

        JSONObject req = new JSONObject(Util.errorDecoder(data));
        JSONObject res = new JSONObject();

        String email = req.get("email").toString();
        String password = req.get("password").toString();

        String token = new String();

        try {
            token = loginService.login(email, password);
        } catch (LoginException e) {
            if (e.getERROR_CODE() == 0) {
                res.put("isSuccess", false);
                res.put("token", "");
                res.put("reason", "invalid email");
                return res.toString();
            } else if (e.getERROR_CODE() == 1) {
                res.put("isSuccess", false);
                res.put("token", "");
                res.put("reason", "invalid password");
                return res.toString();
            }
        }

        res.put("isSuccess", true);
        res.put("token", token);
        return res.toString();
    }

}
