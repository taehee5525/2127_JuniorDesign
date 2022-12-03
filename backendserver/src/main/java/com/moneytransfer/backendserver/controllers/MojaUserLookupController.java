package com.moneytransfer.backendserver.controllers;

import com.moneytransfer.backendserver.BackendserverApplication;
import com.moneytransfer.backendserver.Util;
import com.moneytransfer.backendserver.objects.EventPacket;
import com.moneytransfer.backendserver.services.userService.MojaUserLookupService;
import com.moneytransfer.backendserver.services.userService.PartyResService;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Controller
public class MojaUserLookupController implements ApplicationListener<EventPacket> {

    @Autowired
    public MojaUserLookupController(MojaUserLookupService mojaUserLookupService) {
        this.mojaUserLookupService = mojaUserLookupService;
    }

    @Autowired
    public ApplicationEventPublisher eventPublisher;
    private Map<String, String> waitings = new HashMap<>();
    private MojaUserLookupService mojaUserLookupService;
    private static Logger logger = LoggerFactory.getLogger(BackendserverApplication.class);

    @GetMapping(value = "extuserlookup/ACCOUNT_ID/{email}")
    @ResponseBody
    public String getRequest(@PathVariable String email) throws InterruptedException {
        JSONObject ret = mojaUserLookupService.partyLookup(email);
        return ret.toString();
    }

    @PutMapping(value = "parties/ACCOUNT_ID/{email}")
    public void response(@RequestBody String data, @PathVariable String email) throws UnsupportedEncodingException {
        String purpose = waitings.getOrDefault(email, "");
        if (purpose.equalsIgnoreCase("PartyLookup")) {
            JSONObject req = new JSONObject(data);
            req = new JSONObject(data);

            String partyIdType = req.get("partyIdType").toString();
            String partyIdentifier = req.get("partyIdentifier").toString();
            JSONObject party = new JSONObject(req.get("party"));
            JSONObject partyIdInfo = new JSONObject(party.get("partyIdInfo"));
            String fspId = partyIdInfo.get("fspId").toString();
            String name = party.get("name").toString();

            JSONObject obj = new JSONObject();
            obj.put("partyIdType", partyIdType);
            obj.put("partyIdentifier", partyIdentifier);
            obj.put("fspId", fspId);
            obj.put("name", name);

            obj.put("email", email);
            eventPublisher.publishEvent(new EventPacket(7, obj, this));
            waitings.remove(email);
        }
    }


    @Override
    public void onApplicationEvent(EventPacket event) {
        if (event.getEventCode() == 6) {
            String email = event.getData().get("email").toString();
            waitings.put(email, "PartyLookup");
        }
    }
}
