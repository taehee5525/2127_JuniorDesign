package com.moneytransfer.backendserver.services.userService;

import com.moneytransfer.backendserver.BackendserverApplication;
import com.moneytransfer.backendserver.Util;
import com.moneytransfer.backendserver.helpers.ApiCallHelper;
import com.moneytransfer.backendserver.objects.User2;
import com.moneytransfer.backendserver.repositories.UserRepo;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PartyResService {

    private UserRepo userRepo;
    private ApiCallHelper apicall = new ApiCallHelper();
    private static Logger logger = LoggerFactory.getLogger(BackendserverApplication.class);

    public PartyResService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public void partyCallBack(String email, String fspDest) {
        JSONObject ret = lookupUser(email);
        if (ret == null) {
            return;
        }
        Map<String, String> headerMap = new HashMap<>();
        headerMap.put("Host", "http://localhost:8080");
        headerMap.put("Accept", Util.headerMap.get("participantsAccept"));
        headerMap.put("Content-type", Util.headerMap.get("participantsContentType"));
        headerMap.put("Date", Util.headerMap.get("tempHeaderDate"));
        headerMap.put("FSPIOP-Source", Util.FSP_NAME);
        headerMap.put("FSPIOP-Destination", fspDest);

        String url = endPointString(endPointLookup(fspDest));
        try {
            apicall.callPut(url + "ACCOUNT_ID/" + email, headerMap, ret);
            logger.info("Party API CALL: Spring -> " + fspDest + " (\"" + email + "\") <sending user information>");
        } catch (Exception e) {
            logger.error("exception occurred while sending put party response.");
        }

    }

    /**
     * If user exists, return the obj else return null.
     * @param email user email
     * @return user obj
     */
    private JSONObject lookupUser(String email) {
        Optional<User2> userOpt = userRepo.findByUserEmail(email);
        if (userOpt.isEmpty()) {
            return null;
        }
        User2 userObj = userOpt.get();
        JSONObject ret = new JSONObject();

        ret.put("partyIdType", "ACCOUNT_ID");
        ret.put("partyIdentifier", userObj.getUserEmail());

        JSONObject party = new JSONObject();
        JSONObject partyIdInfo = new JSONObject();
        partyIdInfo.put("partyIdType", "ACCOUNT_ID");
        partyIdInfo.put("partyIdentifier", userObj.getUserEmail());
        partyIdInfo.put("fspId", Util.FSP_NAME);
        party.put("partyIdInfo", partyIdInfo);
        party.put("name", userObj.getName());
        ret.put("party", party);

        return ret;
    }

    private String endPointString(String input) {
        int cnt = 0;
        int i = 0;
        StringBuffer sb = new StringBuffer();
        while(cnt < 4) {
            if (input.charAt(i) == '/') {
                cnt++;
            }
            sb.append(input.charAt(i));
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
            res = new JSONArray(apicall.callGet(Util.urlMap.get("Central_Ledger") + "/participants/" + fspId + "/endpoints"
                    , headerMap, paramMap, true));
            logger.info("Ledger API CALL: Spring -> Central-Ledger (\"" + fspId + "\") <lookup fsp endpoint>");
        }  catch (Exception e) {
            logger.info("Ledger API CALL: Exception is Occurred");
            e.printStackTrace();
        }

        for (int i = 0; i < res.length(); i++) {
            JSONObject obj = res.getJSONObject(i);
            if (obj.get("type").toString().equalsIgnoreCase("FSPIOP_CALLBACK_URL_PARTIES_SUB_ID_PUT")) {
                return obj.get("value").toString();
            }
        }
        return "";
    }


}
