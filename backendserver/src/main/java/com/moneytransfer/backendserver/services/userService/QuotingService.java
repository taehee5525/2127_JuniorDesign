package com.moneytransfer.backendserver.services.userService;

import com.moneytransfer.backendserver.objects.Quotes;
import com.moneytransfer.backendserver.repositories.QuotesRepo;
import com.moneytransfer.backendserver.repositories.TransactionRepo;
import com.moneytransfer.backendserver.BackendserverApplication;
import com.moneytransfer.backendserver.Util;
import com.moneytransfer.backendserver.helpers.ApiCallHelper;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class QuotingService{
    private ApiCallHelper apicall = new ApiCallHelper();
    private static Logger logger = LoggerFactory.getLogger(BackendserverApplication.class);
    private QuotesRepo quotesRepo;
    private TransactionRepo transactionRepo;


    public QuotingService(QuotesRepo quotesRepo, TransactionRepo transactionRepo) {
        this.quotesRepo = quotesRepo;
        this.transactionRepo = transactionRepo;

    }

    public Quotes createQuotes(String userNote) {
        UUID quotesId = Util.generateQuotingUID();
        while (quotesRepo.findByQuotesId(quotesId).isPresent()) {
            quotesId = Util.generateQuotingUID();
        }
        Quotes quote = new Quotes();
        quote.setId(quotesId);
        quote.setUserNote(userNote);

        return quote;
    }
    public void postQuotes(String email, String type, String amount, String userNote) {
        JSONObject req = new JSONObject();

        Map<String, String> headerMap = new HashMap<>();
        headerMap.put("Host", "http://localhost:8080");
        headerMap.put("Accept", Util.headerMap.get("quotesAccept"));
        headerMap.put("Content-type", Util.headerMap.get("quotesContentType"));
        headerMap.put("Date", Util.headerMap.get("tempHeaderDate"));
        headerMap.put("FSPIOP-Source", Util.FSP_NAME);
        headerMap.put("FSPIOP-Signature", "TestSignature" );
        headerMap.put("FSPIOP-Destination", "simulationfsp");
        headerMap.put("FSPIOP-HTTP-Method", "POST");
        headerMap.put("FSPIOP-URI", "/quotes");

        Quotes quote = createQuotes(userNote);

        req.put("quoteId", quote.getId());
        req.put("transactionId",quote.getId());
//        req.put("transactionRequestId",);
        req.put("payee", "testPayee");
        req.put("payer", "testPayer");
        req.put("amountType", type);
        req.put("amount", amount);
        req.put("fees", "0");
        req.put("transactionType", "testTransactionType");
//        req.put("geoCode",);
        req.put("note", userNote);


        try {
            apicall.callPost(Util.urlMap.get("Quoting_Service"), headerMap, req, false);
            logger.info("SYNC API CALL: Spring -> Quoting Service Posting Quotes");
        }  catch (Exception e) {
            logger.info("SYNC API CALL: Exception is Occurred");
            e.printStackTrace();
        }
    }
}
