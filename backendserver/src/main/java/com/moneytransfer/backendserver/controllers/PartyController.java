package com.moneytransfer.backendserver.controllers;

import com.moneytransfer.backendserver.exceptions.AuthException;
import com.moneytransfer.backendserver.services.userService.PartyResService;
import org.json.JSONObject;
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

    @PutMapping(value = "parties/ACCOUNT_ID/{email}")
    public void errResponse(@RequestBody String data, @PathVariable String email) {

        
    }

}
