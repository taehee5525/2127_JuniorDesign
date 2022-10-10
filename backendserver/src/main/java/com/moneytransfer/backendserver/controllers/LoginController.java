package com.moneytransfer.backendserver.controllers;

import com.moneytransfer.backendserver.Util;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.UnsupportedEncodingException;

@Controller
public class LoginController {

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
}
