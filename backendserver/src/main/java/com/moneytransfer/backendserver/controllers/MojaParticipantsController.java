package com.moneytransfer.backendserver.controllers;

import com.moneytransfer.backendserver.BackendserverApplication;
import com.moneytransfer.backendserver.Util;
import com.moneytransfer.backendserver.objects.EventPacket;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashMap;
import java.util.Map;

@Controller
public class MojaParticipantsController implements ApplicationListener<EventPacket> {

    private static Logger logger = LoggerFactory.getLogger(BackendserverApplication.class);
    private Map<String, String> waitings = new HashMap<>();
    @Autowired
    public ApplicationEventPublisher eventPublisher;

    @EventListener
    @Override
    public void onApplicationEvent(EventPacket event) {
        // event code 0 -> @see EventPacket class.
        if(event.getEventCode() == 0) {
            String email = event.getData().get("email").toString();
            waitings.put(email, "SYNC_Check");
        }
    }

    @PutMapping(value = "participants/ACCOUNT_ID/{email}/error")
    public void errResponse(@RequestBody String data, @PathVariable String email) {
        //JSONObject req = new JSONObject(data);
        String purpose = waitings.getOrDefault(email, "");
        if (purpose.equalsIgnoreCase("SYNC_Check")) {
            waitings.remove(email);
            JSONObject obj = new JSONObject();
            obj.put("result", "error");
            obj.put("email", email);
            eventPublisher.publishEvent(new EventPacket(1, obj, this));
        }
    }

    @PutMapping(value = "participants/ACCOUNT_ID/{email}")
    public void response(@RequestBody String data, @PathVariable String email) {

        JSONObject req = new JSONObject(data);
        String partyName = req.get("party").toString();

        if (!partyName.equalsIgnoreCase(Util.FSP_NAME)) {

        } else {

        }
    }
}
