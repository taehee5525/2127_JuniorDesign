package com.moneytransfer.backendserver.controllers;

import com.moneytransfer.backendserver.Util;
import com.moneytransfer.backendserver.objects.User;
import com.moneytransfer.backendserver.repositories.TempTokenRepository;
import com.moneytransfer.backendserver.repositories.TempUserRepository;
import com.moneytransfer.backendserver.repositories.TokenRepository;
import com.moneytransfer.backendserver.repositories.UserRepository;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class LoginController {

    UserRepository usrRepo = new TempUserRepository();
    TokenRepository tokenRepo = new TempTokenRepository();

    @PostMapping("users/userlogin")
    @ResponseBody
    public String userLogin(@RequestBody String data) throws UnsupportedEncodingException {

        JSONObject req = new JSONObject(Util.errorDecoder(data));
        JSONObject res = new JSONObject();

        String email = req.get("email").toString();
        String password = req.get("password").toString();

        //Lookup User in the user repository first.
        User usr = usrRepo.lookupUser(email);

        //if we cannot find user.
        if (usr == null) {
            res.put("isSuccess", false);
            res.put("token", "");
            res.put("reason", "invalid email");
            return res.toString();
        }

        //if password is incorrect.
        if (!usr.passwordChecker(password)) {
            res.put("isSuccess", false);
            res.put("token", "");
            res.put("reason", "invalid password");
            return res.toString();
        }

        String token = Util.makeRandomString(32);
        //to prevent duplicate token.
        while(tokenRepo.getUserEmail(token) != null) {
            token = Util.makeRandomString(32);
        }

        tokenRepo.save(usr.getEmail(), token, Long.toString(System.currentTimeMillis()));

        res.put("isSuccess", true);
        res.put("token", token);

        return res.toString();
    }
}
