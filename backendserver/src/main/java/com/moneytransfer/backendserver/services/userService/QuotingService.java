//package com.moneytransfer.backendserver.services.userService;
//
//import com.moneytransfer.backendserver.BackendserverApplication;
//import com.moneytransfer.backendserver.Util;
//import com.moneytransfer.backendserver.helpers.ApiCallHelper;
//import com.moneytransfer.backendserver.objects.EventPacket;
//import org.json.JSONObject;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.ApplicationEventPublisher;
//import org.springframework.context.ApplicationListener;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.HashMap;
//import java.util.Map;
//
//@Service
//@Transactional(readOnly = true)
//public class QuotingService{
//    private ApiCallHelper apicall = new ApiCallHelper();
//    private static Logger logger = LoggerFactory.getLogger(BackendserverApplication.class);
//
//    public void postQuotes(String type, String amount, String userNote) {
//        JSONObject req = new JSONObject();
//
//        Map<String, String> headerMap = new HashMap<>();
//        headerMap.put("Host", "http://localhost:8080");
//        headerMap.put("Accept", Util.headerMap.get("quotesAccept"));
//        headerMap.put("Content-type", Util.headerMap.get("quotesContentType"));
//        headerMap.put("Date", Util.headerMap.get("tempHeaderDate"));
//        headerMap.put("FSPIOP-Source", Util.FSP_NAME);
//        headerMap.put("FSPIOP-Signature", )
//        headerMap.put("FSPIOP-Destination", "simulationfsp");
//        headerMap.put("FSPIOP-HTTP-Method", "POST");
//        headerMap.put("FSPIOP-URI", "/quotes");
//
//
//        req.put("quoteId", Util.generateQuotingUID());
//        req.put("transactionId",Util.generateTransactionUID());
////        req.put("transactionRequestId",);
//        req.put("payee",);
//        req.put("payer",);
//        req.put("amountType", type);
//        req.put("amount", amount);
//        req.put("fees", "0");
//        req.put("transactionType",);
////        req.put("geoCode",);
//        req.put("note", userNote);
//
//
//        try {
//            apicall.callPost(Util.urlMap.get("Quoting_Service"), headerMap, req, false);
//            logger.info("SYNC API CALL: Spring -> Quoting Service Posting Quotes");
//        }  catch (Exception e) {
//            logger.info("SYNC API CALL: Exception is Occurred");
//            e.printStackTrace();
//        }
//    }
//}
