package com.moneytransfer.backendserver.controllers;

import com.moneytransfer.backendserver.Util;
import com.moneytransfer.backendserver.objects.EventPacket;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Controller
public class MojaUserLookupController implements ApplicationListener<EventPacket> {

    @Autowired
    public ApplicationEventPublisher eventPublisher;
    private Map<String, String> waitings = new HashMap<>();

    @PutMapping(value = "parties/ACCOUNT_ID/{email}")
    public void response(@RequestBody String data, @PathVariable String email) {
        String purpose = waitings.getOrDefault(email, "");
        if (purpose.equalsIgnoreCase("PartyLookup")) {

        }
    }


    @Override
    public void onApplicationEvent(EventPacket event) {
        if(event.getEventCode() == 6) {
            String email = event.getData().get("email").toString();
            waitings.put(email, "PartyLookup");
    }
}
