package com.moneytransfer.backendserver.services.userService;

import com.moneytransfer.backendserver.BackendserverApplication;
import com.moneytransfer.backendserver.Util;
import com.moneytransfer.backendserver.helpers.ApiCallHelper;
import com.moneytransfer.backendserver.objects.EventPacket;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service
@Transactional(readOnly = true)
public class AccountLookupService implements ApplicationListener<EventPacket>{
    private ApiCallHelper apicall = new ApiCallHelper();
    private static Logger logger = LoggerFactory.getLogger(BackendserverApplication.class);
    @Autowired
    public ApplicationEventPublisher eventPublisher;

    @Override
    public void onApplicationEvent(EventPacket event) {
        if(event.getEventCode() == 3) {
            String email = event.getData().get("email").toString();
            if(event.getData().get("result").toString().equalsIgnoreCase("doesNotHave")) {
                alsAdd(email);
            }
        }
    }
    public void alsAdd(String email) {

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
                alsAddChecker(email);
            }
        };
        thread.start();
    }

    private void alsAddChecker(String email) {
        JSONObject obj = new JSONObject();
        obj.put("email", email);
        eventPublisher.publishEvent(new EventPacket(2, obj, this));

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


    private void getParty(String email) {
        JSONObject obj = new JSONObject();
        obj.put("email", email);
        eventPublisher.publishEvent(new EventPacket(2, obj, this));

        JSONObject res = new JSONObject();

        Map<String, String> paramMap = new HashMap<>();
        Map<String, String> headerMap = new HashMap<>();
        headerMap.put("Host", "http://localhost:8080");
        headerMap.put("Accept", Util.headerMap.get("partiesAccept"));
        headerMap.put("Content-Type", Util.headerMap.get("partiesContentType"));
        headerMap.put("Date", Util.headerMap.get("tempHeaderDate"));
        headerMap.put("FSPIOP-Source", Util.FSP_NAME);
        headerMap.put("FSPIOP-Destination", "Account Lookup");

        try {
            res = apicall.callGet(Util.urlMap.get("Account_Lookup_Service") + "/parties/ACCOUNT_ID/" + email
                    , headerMap, paramMap, false);
            logger.info("SYNC API CALL: Spring -> ALS (\"" + email + "\") <Receiving an information of a Party from Moja>");
        }  catch (Exception e) {
            logger.info("SYNC API CALL: Exception is Occurred");
            e.printStackTrace();
        }
    }


}
