package com.moneytransfer.backendserver.services.userService;

import com.moneytransfer.backendserver.BackendserverApplication;
import com.moneytransfer.backendserver.Util;
import com.moneytransfer.backendserver.helpers.ApiCallHelper;
import com.moneytransfer.backendserver.objects.EventPacket;
import org.json.JSONArray;
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
public class MojaUserLookupService implements ApplicationListener<EventPacket> {

    private ApiCallHelper apicall = new ApiCallHelper();
    private static Logger logger = LoggerFactory.getLogger(BackendserverApplication.class);
    private Map<String, String> stateMap = new HashMap<>();
    private Map<String, String> fspIdMap = new HashMap<>();
    private Map<String, JSONObject> objMap = new HashMap<>();


    @Autowired
    public ApplicationEventPublisher eventPublisher;

    /**
     * make new thread and call this to avoid main thread stops.
     * @param email
     * @throws InterruptedException
     */
    public JSONObject partyLookup (String email) throws InterruptedException {
        synchronized (stateMap) {
            stateMap.put(email, "REQUEST_Participants");
        }
        fspIdLookUp(email);
        while(stateMap.get(email).equalsIgnoreCase("REQUEST_Participants")) {
            wait();
            if (!stateMap.get(email).equalsIgnoreCase("REQUEST_Participants")) {
                break;
            }
        }
        if (stateMap.get(email).equalsIgnoreCase("NotFound")) {
            return null;
        }
        String fspId = fspIdMap.get(email);
        synchronized (fspIdMap) {
            fspIdMap.remove(email);
        }
        String endPoint = endPointLookup(fspId);
        if (endPoint.equals("")) {
            return null;
        }
        endPoint = endPointString(endPoint);
        endPoint += "ACCOUNT_ID/";

        synchronized (stateMap) {
            stateMap.put(email, "REQUEST_Party");
        }

        Thread thread = new Thread() {
            public void run() {
                try {
                    this.sleep(Util.FSP_RESPONSE_WAIT_LIMIT);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                synchronized (stateMap) {
                    stateMap.put(email, "FAIL");
                }
            }
        };
        thread.start();

        partyLookUp(endPoint, email);
        while(stateMap.get(email).equalsIgnoreCase("REQUEST_Party")) {
            wait();
            if (!stateMap.get(email).equalsIgnoreCase("REQUEST_Party")) {
                break;
            }
        }

        if (stateMap.get(email).equalsIgnoreCase("FAIL")) {
            synchronized (stateMap) {
                stateMap.remove(email);
            }
            return null;
        }

        JSONObject ret = objMap.get(email);
        synchronized (objMap) {
            objMap.remove(email);
        }
        synchronized (stateMap) {
            stateMap.remove(email);
        }
        return ret;
    }

    private void partyLookUp(String url, String email) {
        JSONObject obj = new JSONObject();
        obj.put("email", email);
        eventPublisher.publishEvent(new EventPacket(6, obj, this));
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
            res = new JSONObject(apicall.callGet(url + email, headerMap, paramMap, false));
            logger.info("SYNC API CALL: Spring -> ALS (\"" + email + "\") <Looking up User from Moja>");
        }  catch (Exception e) {
            logger.info("SYNC API CALL: Exception is Occurred");
            e.printStackTrace();
        }
    }

    private String endPointString(String input) {
        int cnt = 0;
        int i = 0;
        StringBuffer sb = new StringBuffer();
        while(cnt < 4) {
            if (input.charAt(i) == '/') {
                cnt++;
            }
            sb.append(input);
            i++;
        }
        return sb.toString();
    }

    private String endPointLookup(String fspId) {
        JSONArray res = new JSONArray();

        Map<String, String> paramMap = new HashMap<>();
        Map<String, String> headerMap = new HashMap<>();
        headerMap.put("Host", "http://localhost:8080");
        headerMap.put("Accept", Util.headerMap.get("participantsAccept"));
        headerMap.put("Content-type", Util.headerMap.get("participantsContentType"));
        headerMap.put("Date", Util.headerMap.get("tempHeaderDate"));
        headerMap.put("FSPIOP-Source", Util.FSP_NAME);
        headerMap.put("FSPIOP-Destination", "Account Lookup");

        try {
            res = new JSONArray(apicall.callGet(Util.urlMap.get("Account_Lookup_Service") + "/participants/" + fspId + "/endpoints"
                    , headerMap, paramMap, true));
            logger.info("Ledger API CALL: Spring -> Central-Ledger (\"" + fspId + "\") <lookup fsp endpoint>");
        }  catch (Exception e) {
            logger.info("Ledger API CALL: Exception is Occurred");
            e.printStackTrace();
        }

        for (int i = 0; i < res.length(); i++) {
            JSONObject obj = new JSONObject(res.get(i));
            if (obj.get("type").toString().equalsIgnoreCase("FSPIOP_CALLBACK_URL_PARTIES_SUB_ID_GET")) {
                return obj.get("value").toString();
            }
        }
        return "";
    }



    private void fspIdLookUp(String email) {
        JSONObject obj = new JSONObject();
        obj.put("email", email);
        eventPublisher.publishEvent(new EventPacket(4, obj, this));

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
            res = new JSONObject(apicall.callGet(Util.urlMap.get("Account_Lookup_Service") + "/participants/ACCOUNT_ID/" + email
                    , headerMap, paramMap, false));
            logger.info("SYNC API CALL: Spring -> ALS (\"" + email + "\") <Looking up User from Moja>");
        }  catch (Exception e) {
            logger.info("SYNC API CALL: Exception is Occurred");
            e.printStackTrace();
        }
    }

    @Override
    public void onApplicationEvent(EventPacket event) {
        if(event.getEventCode() == 5) {
            String email = event.getData().get("email").toString();
            if(event.getData().get("result").toString().equalsIgnoreCase("NotFound")) {
                synchronized (stateMap) {
                    stateMap.put(email, "NotFound");
                }
                notifyAll();
            } else if(event.getData().get("result").toString().equalsIgnoreCase("Found")) {
                String fapId = event.getData().get("fspId").toString();
                synchronized (stateMap) {
                    stateMap.put(email, "Found");
                }
                synchronized (fspIdMap) {
                    fspIdMap.put(email, fapId);
                }
                notifyAll();
            }
        } else if (event.getEventCode() == 7) {
            String email = event.getData().get("email").toString();

            synchronized (objMap) {
                objMap.put(email, event.getData());
            }
            synchronized (stateMap) {
                stateMap.put(email, "FIN");
            }
            notifyAll();
        }
    }
}
