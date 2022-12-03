package com.moneytransfer.backendserver.controllers;

import com.moneytransfer.backendserver.BackendserverApplication;
import com.moneytransfer.backendserver.exceptions.AuthException;
import com.moneytransfer.backendserver.services.userService.PartyResService;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.util.List;

@Controller
public class PartyController {

    @Autowired
    public PartyController(PartyResService partyResService) {
        this.partyResService = partyResService;
    }

    private PartyResService partyResService;
    private static Logger logger = LoggerFactory.getLogger(BackendserverApplication.class);

    @GetMapping(value = "parties/ACCOUNT_ID/{email}")
    public void getRequest(@PathVariable String email, @RequestHeader(value = "FSPIOP-Source") String fspSrc) {
        logger.info("SYNC API RES: " + fspSrc + " -> Spring (\"" + email + "\") <Get party information request>");
        Thread thread = new Thread(() -> partyResService.partyCallBack(email, fspSrc));
        thread.start();
    }

}
