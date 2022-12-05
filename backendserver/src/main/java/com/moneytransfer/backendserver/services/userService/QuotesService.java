package com.moneytransfer.backendserver.services.userService;
import com.moneytransfer.backendserver.BackendserverApplication;
import com.moneytransfer.backendserver.Util;
import com.moneytransfer.backendserver.helpers.ApiCallHelper;
import com.moneytransfer.backendserver.objects.EventPacket;
import com.moneytransfer.backendserver.repositories.QuotesRepo;
import com.moneytransfer.backendserver.repositories.UserRepo;
import com.moneytransfer.backendserver.objects.Quotes;
import com.moneytransfer.backendserver.objects.User2;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.Optional;


@Service
@Transactional(readOnly = true)
public class QuotesService {

    private ApiCallHelper apicall = new ApiCallHelper();
    private static Logger logger = LoggerFactory.getLogger(BackendserverApplication.class);
    private QuotesRepo quotesRepo;
    private UserRepo userRepo;

    public QuotesService(QuotesRepo quotesRepo, UserRepo userRepo) {
        this.quotesRepo = quotesRepo;
        this.userRepo = userRepo;
    }

    /**
     * Creating a new quote and save it in our database
     * @param userNote The note that user enters
     * @param payerName Payer's email address
     * @param payeeName Payee's email address
     * @return the Quotes object
     */
    public Quotes createNewQuotes(String userNote, String payerName, String payeeName) {
        UUID id = Util.generateQuotingUID();
        while (quotesRepo.findByQuotesId(id).isPresent()) {
            id = Util.generateQuotingUID();
        }

        Quotes quote = new Quotes();

        quote.setId(id);
        quote.setUserNote(userNote);
        quote.setPayeeName(payeeName);
        quote.setPayerName(payerName);
        quote.setCompleted(false);

        quotesRepo.save(quote);

        return quote;
    }

    /**
     * Posting quotes method
     * @param type either "SEND" or "RECEIVE"
     * @param amount amount in USD
     * @param userNote The notes that the user input
     * @param payerName Even though it says payerName, it should be an email
     * @param payeeName Even though it says payeeName, it should be an email
     */
    public void postQuotes(String type, String amount, String userNote, String payerName, String payeeName, String targetFSP, String currentFSP) {
        JSONObject req = new JSONObject();

        Map<String, String> headerMap = new HashMap<>();
        headerMap.put("Host", "http://localhost:8080");
        headerMap.put("Accept", Util.headerMap.get("quotesAccept"));
        headerMap.put("Content-type", Util.headerMap.get("quotesContentType"));
        headerMap.put("Date", Util.headerMap.get("tempHeaderDate"));
        headerMap.put("FSPIOP-Source", Util.FSP_NAME);
        headerMap.put("FSPIOP-Destination", targetFSP);

        // Create a quote and saves it internally
        Quotes quote = createNewQuotes(userNote, payerName, payeeName);

        req.put("quoteId", quote.getId());

        // Temporary UUID used to create quotes.
        // This can be overwritten with our own transaction ID later
        req.put("transactionId", UUID.randomUUID());

        // Get party info using party look up
        // payerName will be the email ..

        JSONObject payeePartyInfo = new JSONObject();
        payeePartyInfo.put("partyIdType", "ACCOUNT_ID");
        payeePartyInfo.put("partyIdentifer", payeeName);
        payeePartyInfo.put("fspId", targetFSP);
        req.put("payee", payeePartyInfo);

        // Personal info of the payer
        Optional<User2> PayerOpt = userRepo.findByUserEmail(payerName);
        User2 Payer = PayerOpt.get();
        JSONObject personalInfo = new JSONObject();
        JSONObject complexName = new JSONObject();
        complexName.put("firstName", Payer.getName());
        complexName.put("lastName", "TESTING");
        personalInfo.put("complexName", complexName);
        req.put("personalInfo", personalInfo);

        JSONObject payerPartyInfo = new JSONObject();
        payerPartyInfo.put("partyIdType", "ACCOUNT_ID");
        payerPartyInfo.put("partyIdentifier", payerName);
        payerPartyInfo.put("fspId", currentFSP);
        req.put("partyIdInfo", payerPartyInfo);

        // Either Send or Receive
        req.put("amountType", type);

        // Creating amount data
        JSONObject amountData = new JSONObject();
        amountData.put("amount", amount);
        amountData.put("currency", Util.CURRENCY);
        req.put("amount", amountData.toString());

        // Creating transaction type
        JSONObject transactionTypeData = new JSONObject();
        transactionTypeData.put("scenario", "TRANSFER");
        transactionTypeData.put("initiator", payerName);
        transactionTypeData.put("initiatorType", "CONSUMER");
        req.put("transactionType", transactionTypeData.toString());

        // Creating fees.. which will be 0
        JSONObject fees = new JSONObject();
        fees.put("amount", "0");
        fees.put("currency", Util.CURRENCY);
        req.put("fees", fees);

        req.put("note", quote.getUserNote());

//        Debugging purpose
//        System.out.println(req.toString());
        try {
            apicall.callPost(Util.urlMap.get("Quoting_Service"), headerMap, req, false);
            logger.info("SYNC API CALL: Spring -> Quoting Service");
        }  catch (Exception e) {
            logger.info("SYNC API CALL: Exception is Occurred");
            e.printStackTrace();
        }
    }
}
