package com.moneytransfer.backendserver.controllers;

import com.moneytransfer.backendserver.Util;
import com.moneytransfer.backendserver.exceptions.*;
import com.moneytransfer.backendserver.services.userService.AuthService;
import com.moneytransfer.backendserver.services.userService.CreateUserService;
import com.moneytransfer.backendserver.services.userService.LoginService;
import com.moneytransfer.backendserver.services.userService.UserProfileService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;

@Controller
public class UserController {

    @Autowired
    public UserController(CreateUserService createUserService, LoginService loginService
            , AuthService authService, UserProfileService userProfileService) {
        this.createUserService = createUserService;
        this.loginService = loginService;
        this.authService = authService;
        this.userProfileService = userProfileService;
    }
    private AuthService authService;
    private CreateUserService createUserService;
    private LoginService loginService;
    private UserProfileService userProfileService;

    @PostMapping(value = "users/mkuser")
    @ResponseBody
    public String userJoin(@RequestBody String data) throws UnsupportedEncodingException {

        JSONObject req = new JSONObject(Util.errorDecoder(data));
        JSONObject ret = new JSONObject();

        if (createUserService.createUser(req.get("email").toString(), req.get("name").toString(), req.get("password").toString()
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
        String name = new String();

        try {
            String[] userInfo = loginService.login(email, password);
            name = userInfo[0];
            token = userInfo[1];
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
        res.put("name", name);
        return res.toString();
    }

    @PostMapping("users/image")
    @ResponseBody
    public String uploadImage(@RequestParam("file") MultipartFile file, @RequestParam("token") String token) throws IOException {

        String requesterToken = token;
        String requesterEmail = new String();

        try {
            requesterEmail = authService.getUserIdFromToken(requesterToken);
        } catch (AuthException e) {
            return makeStatusResponse(e, null).toString();
        }

        try {
            userProfileService.updateProfileImage(requesterEmail, file.getContentType()
                    , file.getOriginalFilename(), file.getBytes());
        } catch (UserProfileException e) {
            return makeStatusResponse(null, e).toString();
        }

        JSONObject res = makeStatusResponse(null, null);
        return res.toString();
    }

    @GetMapping("/users/image")
    public ResponseEntity<byte[]> findOne(@RequestParam("email") String email) throws UserProfileException {

        Map<String, String> imgInfo = userProfileService.getUserProfile(email);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", imgInfo.get("Content-Type"));
        headers.add("Content-Length", imgInfo.get("Content-Length"));

        return new ResponseEntity<byte[]>(userProfileService.getUserProfileData(email), headers, HttpStatus.OK);
    }

    private JSONObject makeStatusResponse(AuthException ae, UserProfileException ue){
        JSONObject res = new JSONObject();
        if (ae == null && ue == null) {
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

        if (ue != null) {
            res.put("transactionRequestStatus", false);
            res.put("reason", ue.getErrorDescription());
        } else {
            res.put("transactionRequestStatus", true);
        }

        return res;
    }

}
