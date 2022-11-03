package com.moneytransfer.backendserver.services.userService;

import com.moneytransfer.backendserver.Util;
import com.moneytransfer.backendserver.helpers.ApiCallHelper;
import com.moneytransfer.backendserver.objects.User2;
import com.moneytransfer.backendserver.repositories.TokenRepo;
import com.moneytransfer.backendserver.repositories.UserRepo;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Service
public class MojaUpdatePartyService {

    private UserRepo userRepo;
    private Set<String> requestList;
    private ApiCallHelper apicall = new ApiCallHelper();
    private static Logger logger = LoggerFactory.getLogger(MojaUpdatePartyService.class);

    public MojaUpdatePartyService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    //@PostConstruct //<-- remove if you have mojaloop on local.
    private void init() {
        List<User2> user2List = userRepo.findAll();
        requestList = new HashSet<>();
        for (User2 user : user2List) {
            alsLookUp(user.getUserEmail());
            requestList.add(user.getUserEmail());
        }
    }

    @RequestMapping(value = "/parties/email/{email}/error")
    public void errResponse(@RequestBody String data, @PathVariable String email
            , HttpServletRequest request, HttpServletResponse response, Model model) {
        alsAdd(email);
    }

    @RequestMapping(value = "/parties/email/{email}")
    public void response(@RequestBody String data, @PathVariable String email
            , HttpServletRequest request, HttpServletResponse response, Model model) {

        JSONObject req = new JSONObject(data);
        String partyName = req.get("party").toString();

        if (!partyName.equalsIgnoreCase(Util.FSP_NAME)) {
            alsAdd(email);
        } else {
            logger.debug("API: ALS -> Spring ( \"" + email + "\" is in " + Util.FSP_NAME + ".)");
        }
    }

    private void alsLookUp(String email) {
        JSONObject req = new JSONObject();
        JSONObject res = new JSONObject();

        Map<String, String> paramMap = new HashMap<>();
        Map<String, String> headerMap = new HashMap<>();

        try {
            res = apicall.callGet(Util.urlMap.get("Account_Lookup_Service") + "/parties/email/" + email, headerMap, paramMap);
            logger.debug("API: Spring -> ALS (lookup \"" + email + "\")");
        }  catch (Exception e) {
            logger.debug("API: Exception is Occurred");
            e.printStackTrace();
        }
    }

    private void alsAdd(String email) {
        JSONObject req = new JSONObject();
        JSONObject res = new JSONObject();
        req.put("fspId", Util.FSP_NAME);
        req.put("currency", Util.CURRENCY);

        Map<String, String> headerMap = new HashMap<>();

        try {
            res = apicall.callPost(Util.urlMap.get("Account_Lookup_Service") + "/participants/email/" + email, headerMap, req);
            logger.debug("API: Spring -> ALS (Add \"" + email + "\")");
        }  catch (Exception e) {
            logger.debug("API: Exception is Occurred");
            e.printStackTrace();
        }
    }
}
