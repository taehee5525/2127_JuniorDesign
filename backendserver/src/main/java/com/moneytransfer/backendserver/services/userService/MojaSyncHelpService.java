package com.moneytransfer.backendserver.services.userService;


import com.moneytransfer.backendserver.BackendserverApplication;
import com.moneytransfer.backendserver.Util;
import com.moneytransfer.backendserver.helpers.ApiCallHelper;
import com.moneytransfer.backendserver.objects.EventPacket;
import com.moneytransfer.backendserver.objects.User2;
import com.moneytransfer.backendserver.repositories.TokenRepo;
import com.moneytransfer.backendserver.repositories.UserRepo;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Service
public class MojaSyncHelpService  implements ApplicationListener<EventPacket>, DisposableBean {

    private UserRepo userRepo;
    private Set<String> requestList;
    private ApiCallHelper apicall = new ApiCallHelper();
    private static Logger logger = LoggerFactory.getLogger(BackendserverApplication.class);

    public MojaSyncHelpService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Autowired
    public ApplicationEventPublisher eventPublisher;

    @PostConstruct //<-- remove if you have mojaloop on local.
    private void init() {
        requestList = new HashSet<>();
        List<User2> user2List = userRepo.findAll();
        for (int i = 0; i < user2List.size(); i++) {
            alsLookUp(user2List.get(i).getUserEmail());
            requestList.add(user2List.get(i).getUserEmail());

            if (i == user2List.size() - 1) {
                Thread cntThread = new Thread() {
                    public void run() {
                        while (requestList.size() > 0) {
                            try {
                                this.sleep(Util.RESPONSE_WAIT_LIMIT);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                            if (requestList.size() > 0) {
                                logger.error("Server could not get all responses from Mojaloop.");
                                logger.error("Retry to add userInfo to Mojaloop.");

                                List<User2> user2List = userRepo.findAll();
                                for (int i = 0; i < user2List.size(); i++) {
                                    alsLookUp(user2List.get(i).getUserEmail());
                                    requestList.add(user2List.get(i).getUserEmail());
                                }
                            }
                        }
                        logger.info("###############################################################");
                        logger.info("############ All users have been added to MOJALOOP ############");
                        logger.info("############           YOU ARE FREE TO GO          ############");
                        logger.info("############                   ^_^                 ############");
                        logger.info("###############################################################");
                    }
                };
                cntThread.start();
            }
        }
    }

    @Override
    public void destroy() throws Exception {
        List<User2> user2List = userRepo.findAll();
        for (int i = 0; i < user2List.size(); i++) {
            alsRemove(user2List.get(i).getUserEmail());
        }
    }

    private void alsRemove(String email) {
        JSONObject res = new JSONObject();
        Map<String, String> headerMap = new HashMap<>();
        headerMap.put("Host", "http://localhost:8080");
        headerMap.put("Accept", Util.headerMap.get("participantsAccept"));
        headerMap.put("Content-type", Util.headerMap.get("participantsContentType"));
        headerMap.put("Date", Util.headerMap.get("tempHeaderDate"));
        headerMap.put("FSPIOP-Source", Util.FSP_NAME);
        headerMap.put("FSPIOP-Destination", "Account Lookup");

        try {
            res = apicall.callDelete(Util.urlMap.get("Account_Lookup_Service") + "/participants/ACCOUNT_ID/" + email
                    , headerMap, false);
            logger.info("SYNC API CALL: Spring -> ALS (lookup \"" + email + "\") <Removing User from Moja>");
        }  catch (Exception e) {
            logger.error("SYNC API CALL: Exception is Occurred");
            e.printStackTrace();
        }
    }

    private void alsLookUp(String email) {
        JSONObject obj = new JSONObject();
        obj.put("email", email);
        eventPublisher.publishEvent(new EventPacket(0, obj, this));

        JSONObject res = new JSONObject();

        Map<String, String> paramMap = new HashMap<>();
        Map<String, String> headerMap = new HashMap<>();
        headerMap.put("Host", "http://localhost:8080");
        headerMap.put("Accept", Util.headerMap.get("participantsAccept"));
        headerMap.put("Content-type", Util.headerMap.get("participantsContentType"));
        headerMap.put("Date", Util.headerMap.get("tempHeaderDate"));
        headerMap.put("FSPIOP-Source", Util.FSP_NAME);
        headerMap.put("FSPIOP-Destination", "Account Lookup");

        try {
            res = apicall.callGet(Util.urlMap.get("Account_Lookup_Service") + "/participants/ACCOUNT_ID/" + email
                    , headerMap, paramMap, false);
            logger.info("SYNC API CALL: Spring -> ALS (\"" + email + "\") <Looking up User from Moja>");
        }  catch (Exception e) {
            logger.info("SYNC API CALL: Exception is Occurred");
            e.printStackTrace();
        }
    }

    private void alsAdd(String email) {

        JSONObject req = new JSONObject();
        req.put("fspId", Util.FSP_NAME);
        req.put("currency", Util.CURRENCY);

        Map<String, String> headerMap = new HashMap<>();
        headerMap.put("Host", "http://localhost:8080");
        headerMap.put("Accept", Util.headerMap.get("participantsAccept"));
        headerMap.put("Content-type", Util.headerMap.get("participantsContentType"));
        headerMap.put("Date", Util.headerMap.get("tempHeaderDate"));
        headerMap.put("FSPIOP-Source", Util.FSP_NAME);
        headerMap.put("FSPIOP-Destination", "Account Lookup");

        try {
            apicall.callPost(Util.urlMap.get("Account_Lookup_Service") + "/participants/ACCOUNT_ID/" + email, headerMap, req, false);
            logger.info("SYNC API CALL: Spring -> ALS (\"" + email + "\") <Adding User to Moja>");
        }  catch (Exception e) {
            logger.info("SYNC API CALL: Exception is Occurred");
            e.printStackTrace();
        }
        Thread thread = new Thread() {
            public void run() {
                try {
                    this.sleep(2000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                alsLookUp(email);
            }
        };
        thread.start();
    }
    @Override
    public void onApplicationEvent(EventPacket event) {
        // event code 1 -> @see EventPacket class.
        if(event.getEventCode() == 1) {
            String email = event.getData().get("email").toString();
            if(event.getData().get("result").toString().equalsIgnoreCase("doesNotHave")) {
                alsAdd(email);
            } else if(event.getData().get("result").toString().equalsIgnoreCase("hasDiffFspId")) {
                alsAdd(email);
            } else if(event.getData().get("result").toString().equalsIgnoreCase("checked")) {
                requestList.remove(email);
            }
        }
    }


}