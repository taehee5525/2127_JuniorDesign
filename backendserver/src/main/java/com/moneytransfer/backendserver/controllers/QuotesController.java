package com.moneytransfer.backendserver.controllers;

import com.moneytransfer.backendserver.BackendserverApplication;
import com.moneytransfer.backendserver.Util;
import com.moneytransfer.backendserver.services.userService.QuotesService;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.UnsupportedEncodingException;
import java.util.UUID;

@Controller
public class QuotesController {

    @Autowired
    public QuotesController(QuotesService quotesService) {
        this.quotesService = quotesService;
    }

    private QuotesService quotesService;
    private static Logger logger = LoggerFactory.getLogger(BackendserverApplication.class);

    @PutMapping(value = "quotes/{quotesId}")
    public void putQuotes(@RequestBody String body, @PathVariable UUID quotesID) {

    }

    @PostMapping(value = "quotes")
    public void postQuotes(@RequestBody String body) throws UnsupportedEncodingException {
        JSONObject data = new JSONObject();
        try {
            data = new JSONObject(body);
        } catch (Exception e) {
            data = new JSONObject(Util.errorDecoder(body));
        }

        String quoteId = data.get("quoteId").toString();
        String transactionId = data.get("transactionId").toString();

        String payee = data.get("payee").toString();
        JSONObject payeeObj = new JSONObject(payee);
        String payeePartyInfo = payeeObj.get("partyInfo").toString();
        JSONObject payeePartyInfoObj = new JSONObject(payeePartyInfo);
        String payeePartyIdType = payeePartyInfoObj.get("partyIdType").toString();
        String payeePartyIdentifier = payeePartyInfoObj.get("partyIdentifier").toString();
        String payeeFspId = payeePartyInfoObj.get("fspId").toString();

        String payer = data.get("payer").toString();
        JSONObject payerObj = new JSONObject(payee);
        String payerPartyInfo = payeeObj.get("partyInfo").toString();
        JSONObject payerPartyInfoObj = new JSONObject(payerPartyInfo);
        String payerPartyIdType = payerPartyInfoObj.get("partyIdType").toString();
        String payerPartyIdentifier = payerPartyInfoObj.get("partyIdentifier").toString();
        String payerFspId = payerPartyInfoObj.get("fspId").toString();

        String amountType = data.get("amountType").toString();
        String amount = data.get("amount").toString();
        String fees = data.has("fees") ? data.get("fees").toString() : "";



    }

}
