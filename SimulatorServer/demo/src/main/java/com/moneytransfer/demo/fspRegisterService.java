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
public class fspRegisterService {
    ApiCallHelper apicall = new ApiCallHelper();
    private static Logger logger = LoggerFactory.getLogger(DemoApplication.class);

    public void registerFspAndEndPoint(List<String> fspNameList) throws InterruptedException {
        registerFsps(fspNameList);
        registerEndPoints(fspNameList);
    }

    public void registerFsps(List<String> fspNameList) throws InterruptedException {
        for (String fspName: fspNameList) {
            registerFsp(fspName);
        }
        Set<String> registeredFspNames = getFspNames();

        for(String registeredFspName : registeredFspNames) {
            if (fspNameList.contains(registeredFspName)) {
                fspNameList.remove(registeredFspName);
            }
        }
        if (fspNameList.size() > 0) {
            Thread.sleep(2000);
            registerFsps(fspNameList);
        }
    }

    public void registerEndPoints(List<String> fspNameList) throws InterruptedException {
        for (String fspName: fspNameList) {
            registerEndPoints(fspName);
        }
    }

    public void registerEndPoints(String fspName) throws InterruptedException {
        int i = 0;
        for (String endPointName : Util.endPointMap.keySet()) {
            EndPointStringMaker esm = Util.endPointMap.get(endPointName);
            String endPoint = esm.getEndPoint(Util.PORT_NUMBER);
            registerFspEndPoint(fspName, endPointName, endPoint);
            i++;
        }
        Set<String> endPointNameSet = getEndPointNames(fspName);
        if (endPointNameSet.size() < i) {
            Thread.sleep(1000);
            registerEndPoints(fspName);
        }
    }

    /**
     * return endpoint name
     * @param fspName
     * @return
     */
    public Set<String> getEndPointNames(String fspName) {
        Set<String> endPointNameSet = new HashSet<>();

        Map<String, String> headerMap = new HashMap<>();
        headerMap.put("Content-type", "application/json");
        Map<String, String> paramMap = new HashMap<>();

        JSONArray endPointList = new JSONArray();
        try {
            endPointList = new JSONArray(apicall.callGet(Util.urlMap.get("Central_Ledger") + "/participants/"
                    + fspName + "/endpoints", headerMap, paramMap, true));
        } catch (Exception e) {
            logger.error("error occured during get the fsp endpoint names.");
        }
        for (int i = 0; i < endPointList.length(); i++) {
            JSONObject obj = endPointList.getJSONObject(i);
            String endPointType = obj.get("type").toString();
            endPointNameSet.add(endPointType);
        }

        return endPointNameSet;
    }

    public void registerFspEndPoint(String fspName, String type, String value) {
        Map<String, String> headerMap = new HashMap<>();
        headerMap.put("Content-type", "application/json");

        JSONObject paramMap = new JSONObject();
        paramMap.put("type", type);
        paramMap.put("value", value);

        try {
            apicall.callPost(Util.urlMap.get("Central_Ledger") + "/participants/"
                    + fspName + "/endpoints", headerMap, paramMap, false);
            logger.info("Registering FSP endpoint (\"" + fspName + "\") to the Mojaloop.");
        }  catch (Exception e) {
            logger.error("SYNC API CALL: Exception is Occurred");
            e.printStackTrace();
        }
    }
    /**
     * register fsp
     * @param fspName
     */
    public void registerFsp(String fspName) {
        Map<String, String> headerMap = new HashMap<>();
        headerMap.put("Content-type", "application/json");

        JSONObject paramMap = new JSONObject();
        paramMap.put("name", fspName);
        paramMap.put("currency", "USD");

        try {
            apicall.callPost(Util.urlMap.get("Central_Ledger") + "/participants", headerMap, paramMap, false);
            logger.info("Registering FSP (\"" + fspName + "\") to the Mojaloop.");
        }  catch (Exception e) {
            logger.error("SYNC API CALL: Exception is Occurred");
            e.printStackTrace();
        }
    }

    /**
     * method to get Fsp names
     * @return
     * @throws Exception
     */
    public Set<String> getFspNames() {
        Set<String> fspNameSet = new HashSet<>();

        Map<String, String> headerMap = new HashMap<>();
        headerMap.put("Content-type", "application/json");
        Map<String, String> paramMap = new HashMap<>();

        JSONArray fspList = new JSONArray();
        try {
             fspList = new JSONArray(apicall.callGet(Util.urlMap.get("Central_Ledger") + "/participants"
                    , headerMap, paramMap, true));
        } catch (Exception e) {
            logger.error("error occured during get the fsp names.");
        }
        for (int i = 0; i < fspList.length(); i++) {
            JSONObject obj = fspList.getJSONObject(i);
            String fspName = obj.get("name").toString();
            fspNameSet.add(fspName);
        }

        return fspNameSet;
    }

    public void initPositionAndLimit(String fspName, String value) {
        Map<String, String> headerMap = new HashMap<>();
        headerMap.put("Content-type", "application/json");

        JSONObject paramMap = new JSONObject();
        JSONObject limitMap = new JSONObject();
        limitMap.put("type", "NET_DEBIT_CAP");
        limitMap.put("value", value);

        paramMap.put("currency", Util.CURRENCY);
        paramMap.put("limit", limitMap);
        paramMap.put("initialPosition", "0");
        
        try {
            apicall.callPost(Util.urlMap.get("Central_Ledger") + "/participants/"
                    + fspName + "/initialPositionAndLimits", headerMap, paramMap, false);
            logger.info("Settling Initial Position and Limit of (\"" + fspName + "\") to the Mojaloop.");
        }  catch (Exception e) {
            logger.error("SYNC API CALL: Exception is Occurred");
            e.printStackTrace();
        }
    }



}
