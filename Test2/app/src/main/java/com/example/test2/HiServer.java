package com.example.test2;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class HiServer {

    public static final String urlForSignUp = "http://localhost:8080/users/mkuser";
    public static final String urlForSignIn = "http://localhost:8080/users/login";

    public static boolean reqSignUp(String email, String pw) throws JSONException {
        ApiCallMaker apicall = new ApiCallMaker();
        JSONObject req = new JSONObject();
        JSONObject res = new JSONObject();
        Map<String, String> headerMap = new HashMap<>();
        req.put("email", email);
        req.put("password", pw);
        try {
            res = apicall.callPost(urlForSignUp, headerMap, req);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (res.get("isSuccess") == "true") {
            return true;
        }
        return false;
    }

    public static String reqSignIn(String email, String pw) throws JSONException {
        ApiCallMaker apicall = new ApiCallMaker();
        JSONObject req = new JSONObject();
        JSONObject res = new JSONObject();
        Map<String, String> headerMap = new HashMap<>();
        req.put("email", email);
        req.put("password", pw);
        try {
            res = apicall.callPost(urlForSignIn, headerMap, req);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (res.get("isSuccess") == "true") {
            return (String) res.get("token");
        }
        return "";
    }
}
