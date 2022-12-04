package com.moneytransfer.backendserver.controllers;

import com.moneytransfer.backendserver.BackendserverApplication;
import com.moneytransfer.backendserver.services.userService.QuotesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.PathVariable;
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

}
