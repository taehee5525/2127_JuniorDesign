package com.moneytransfer.backendserver.services.userService;
import com.moneytransfer.backendserver.Util;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.Optional;


public class test {
    public static void postQuotes(String type, String amount, String userNote, String payerName, String payeeName, String targetFSP, String currentFSP) {
        JSONObject req = new JSONObject();

        Map<String, String> headerMap = new HashMap<>();
        headerMap.put("Host", "http://localhost:8080");
        headerMap.put("Accept", Util.headerMap.get("quotesAccept"));
        headerMap.put("Content-type", Util.headerMap.get("quotesContentType"));
        headerMap.put("Date", Util.headerMap.get("tempHeaderDate"));
        headerMap.put("FSPIOP-Source", Util.FSP_NAME);
        headerMap.put("FSPIOP-Destination", targetFSP);

        // Create a quote and saves it internally
//        Quotes quote = createNewQuotes(userNote, payerName, payeeName);

        req.put("quoteId", UUID.randomUUID());

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
//        Optional<User2> PayerOpt = userRepo.findByUserEmail(payerName);
//        User2 Payer = PayerOpt.get();
        JSONObject payerInfo = new JSONObject();
        JSONObject personalInfo = new JSONObject();
        JSONObject complexName = new JSONObject();
        complexName.put("firstName", "First Name");
        complexName.put("lastName", "TESTING");
        personalInfo.put("complexName", complexName);
        payerInfo.put("personalInfo", personalInfo);

        JSONObject payerPartyInfo = new JSONObject();
        payerPartyInfo.put("partyIdType", "ACCOUNT_ID");
        payerPartyInfo.put("partyIdentifier", payerName);
        payerPartyInfo.put("fspId", currentFSP);
        payerInfo.put("partyIdInfo", payerPartyInfo);
        req.put("payer", payerInfo);

        // Either Send or Receive
        req.put("amountType", type);

        // Creating amount data
        JSONObject amountData = new JSONObject();
        amountData.put("amount", amount);
        amountData.put("currency", Util.CURRENCY);
        req.put("amount", amountData);

        // Creating transaction type
        JSONObject transactionTypeData = new JSONObject();
        transactionTypeData.put("scenario", "TRANSFER");
        transactionTypeData.put("initiator", payerName);
        transactionTypeData.put("initiatorType", "CONSUMER");
        req.put("transactionType", transactionTypeData);

        // Creating fees.. which will be 0
        JSONObject fees = new JSONObject();
        fees.put("amount", "0");
        fees.put("currency", Util.CURRENCY);
        req.put("fees", fees);

        req.put("note", "Note here");

//        Debugging purpose
        System.out.println(req.toString());
    }

    public static void main(String[] args) {
        postQuotes("Receive", "100", "Note", "taehee5525@gmail.com", "Payee@test.com", "targetFSP", "techfsp");
    }
}
