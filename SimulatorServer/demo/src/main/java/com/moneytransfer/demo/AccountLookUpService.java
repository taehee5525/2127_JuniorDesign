package com.moneytransfer.demo;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional(readOnly = true)
public class AccountLookUpService {
    ApiCallHelper apicall = new ApiCallHelper();
    private static Logger logger = LoggerFactory.getLogger(DemoApplication.class);

    /**
     * Method to register a party inside a dfsp
     * @param fspName
     * @param participantEmail
     */
    public void registerParty(String fspName, String participantEmail) {
        Map<String, String> headerMap = new HashMap<>();
        headerMap.put("Content-type", Util.headerMap.get(participantsContentType));
        headerMap.put("Accept", Util.headerMap.get(participantsAccept));

        JSONObject paramMap = new JSONObject();
        paramMap.put("fspId", fspName);
        paramMap.put("currency", Util.CURRENCY);

        try {
            apicall.callPost(Util.urlMap.get("Account_Lookup_Service") + "/participants/ACCOUNT_ID" + participantEmail, headerMap, paramMap, false);
            logger.info("Registering a Participant of the (\"" + fspName + "\") to the Mojaloop.");
        }  catch (Exception e) {
            logger.error("SYNC API CALL: Exception is Occurred");
            e.printStackTrace();
        }
    }


    /**
     * A method used to acheive the party information
     * @param email
     */
    public void getParty(String email) {
        Map<String, String> headerMap = new HashMap<>();

        headerMap.put("Host", "http://localhost:8080");
        headerMap.put("Content-type", Util.headerMap.get(partiesContentType));
        headerMap.put("Accept", Util.headerMap.get(partiesAccept));
        headerMap.put("Date", Util.headerMap.get(date));
        headerMap.put("FSPIOP-Source", "techpay");
        headerMap.put("FSPIOP-Destination", "Account Lookup");

        JSONObject body = new JSONObject();
        body.put("currency", Util.CURRENCY);

        try {
             apicall.callGet(Util.urlMap.get("Account_Lookup_Service") + "/parties/ACCOUNT_ID" + email, headerMap, paramMap, false);
             logger.info("Looking for a party information of " + email + "Inside the Mojaloop");
        } catch (Exception e) {
            logger.error("error occured while trying to achieve the party information.");
            e.printStackTrace();
        }
    }



}
