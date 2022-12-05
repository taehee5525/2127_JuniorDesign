package com.moneytransfer.backendserver.controllers;

import com.moneytransfer.backendserver.BackendserverApplication;
import com.moneytransfer.backendserver.objects.Quotes;
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
    public void postQuotes(@RequestBody String body) {
        JSONObject data = new JSONObject(body);

        UUID quoteId = UUID.fromString(data.get("quoteId").toString());
        String type = data.get("amountType").toString();
        String targetFSP = "";
        String currentFSP = "";

        if (type == "SEND") {
            // payer -> curr
            // payee -> target
            targetFSP = data.getJSONObject("payee").getJSONObject("partyIdInfo").get("fspId").toString();
            currentFSP = data.getJSONObject("payer").getJSONObject("partyIdInfo").get("fspId").toString();
        } else {
            currentFSP = data.getJSONObject("payee").getJSONObject("partyIdInfo").get("fspId").toString();
            targetFSP = data.getJSONObject("payer").getJSONObject("partyIdInfo").get("fspId").toString();
        }

        String payerName =  data.getJSONObject("payer").getJSONObject("partyIdInfo")
                .get("partyIdentifier").toString();
        String payeeName = data.getJSONObject("payee").getJSONObject("partyIdInfo")
                .get("partyIdentifier").toString();
        String amount = data.getJSONObject("amount").get("amount").toString();
        String userNote = data.get("note").toString();

        quotesService.saveCreatedQuotes(quoteId, type, amount, userNote, payerName, payeeName, targetFSP, currentFSP);
    }
}
