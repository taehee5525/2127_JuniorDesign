package com.moneytransfer.backendserver.controllers;

import com.moneytransfer.backendserver.Util;
import com.moneytransfer.backendserver.exceptions.AuthException;
import com.moneytransfer.backendserver.exceptions.FriendException;
import com.moneytransfer.backendserver.exceptions.TransactionException;
import com.moneytransfer.backendserver.objects.Transaction;
import com.moneytransfer.backendserver.services.userService.AuthService;
import com.moneytransfer.backendserver.services.userService.FriendService;
import com.moneytransfer.backendserver.services.userService.TransactionService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class TransactionController {

    private TransactionService transactionService;
    private AuthService authService;

    @Autowired
    public TransactionController(TransactionService transactionService, AuthService authService) {
        this.transactionService = transactionService;
        this.authService = authService;
    }

    @GetMapping(value = "transactions/getTransactionList")
    @ResponseBody
    public String getTransactionList(@RequestParam("token") String requesterToken) throws UnsupportedEncodingException {

        String requesterEmail = new String();
        try {
            requesterEmail = authService.getUserIdFromToken(requesterToken);
        } catch (AuthException e) {
            return makeStatusResponse(e, null).toString();
        }

        List<Transaction> transactionList = new ArrayList<>();

        try {
            transactionList = transactionService.getTransactionList(requesterEmail);
        } catch (TransactionException e) {
            return makeStatusResponse(null, e).toString();
        }
        JSONObject res = makeStatusResponse(null, null);
        res.put("transactionList", transactionList);

        return res.toString();
    }

    @GetMapping(value = "transactions/getPendingTransactionListThatUserNeedToConfirm")
    @ResponseBody
    public String getPendingTransactionListThatUserNeedToConfirm(@RequestParam("token") String requesterToken)
            throws UnsupportedEncodingException {

        String requesterEmail = new String();
        try {
            requesterEmail = authService.getUserIdFromToken(requesterToken);
        } catch (AuthException e) {
            return makeStatusResponse(e, null).toString();
        }
        List<Transaction> transactionList = new ArrayList<>();
        try {
            transactionList = transactionService.getPendingTransactionList(requesterEmail);
        } catch (TransactionException e) {
            return makeStatusResponse(null, e).toString();
        }
        List<Transaction> pendingTransaction = new ArrayList<>();

        for (Transaction transaction : transactionList) {
            if (transaction.getPayeeEmail().equalsIgnoreCase(requesterEmail)
             && !transaction.isPayeeConfirmed()) {
                pendingTransaction.add(transaction);
            } else if (transaction.getPayerEmail().equalsIgnoreCase(requesterEmail)
                    && !transaction.isPayerConfirmed()) {
                pendingTransaction.add(transaction);
            }
        }

        JSONObject res = makeStatusResponse(null, null);
        res.put("pendingTransaction", pendingTransaction);

        return res.toString();
    }

    @GetMapping(value = "transactions/getPendingTransactionListThatUserWaitingForConfirm")
    @ResponseBody
    public String getPendingTransactionListThatUserWaitingForConfirm(@RequestParam("token") String requesterToken)
            throws UnsupportedEncodingException {

        String requesterEmail = new String();
        try {
            requesterEmail = authService.getUserIdFromToken(requesterToken);
        } catch (AuthException e) {
            return makeStatusResponse(e, null).toString();
        }
        List<Transaction> transactionList = new ArrayList<>();
        try {
            transactionList = transactionService.getPendingTransactionList(requesterEmail);
        } catch (TransactionException e) {
            return makeStatusResponse(null, e).toString();
        }
        List<Transaction> pendingTransaction = new ArrayList<>();

        for (Transaction transaction : transactionList) {
            if (transaction.getPayeeEmail().equalsIgnoreCase(requesterEmail)
                    && transaction.isPayeeConfirmed()) {
                pendingTransaction.add(transaction);
            } else if (transaction.getPayerEmail().equalsIgnoreCase(requesterEmail)
                    && transaction.isPayerConfirmed()) {
                pendingTransaction.add(transaction);
            }
        }

        JSONObject res = makeStatusResponse(null, null);
        res.put("pendingTransaction", pendingTransaction);

        return res.toString();
    }

    @GetMapping(value = "transactions/getUserBalance")
    @ResponseBody
    public String getUserBalance(@RequestParam("token") String requesterToken) throws UnsupportedEncodingException {

        String requesterEmail = new String();
        try {
            requesterEmail = authService.getUserIdFromToken(requesterToken);
        } catch (AuthException e) {
            return makeStatusResponse(e, null).toString();
        }

        double userBalance = 0;
        try {
            userBalance = transactionService.getUserBalance(requesterEmail);
        } catch (TransactionException e) {
            return makeStatusResponse(null, e).toString();
        }

        JSONObject res = makeStatusResponse(null, null);
        res.put("userBalance", userBalance);

        return res.toString();
    }

    @PostMapping(value = "transactions/sendMoneyINT")
    @ResponseBody
    public String sendMoneyINT(@RequestBody String data) throws UnsupportedEncodingException {

        JSONObject req = new JSONObject(Util.errorDecoder(data));

        String requesterToken = req.get("token").toString();
        String friendEmail = req.get("email").toString();
        double amount = Double.parseDouble(req.get("amount").toString());

        String requesterEmail = new String();
        try {
            requesterEmail = authService.getUserIdFromToken(requesterToken);
        } catch (AuthException e) {
            return makeStatusResponse(e, null).toString();
        }

        String transactionId = new String();
        try {
            transactionId = transactionService.makeInternalTransaction(requesterEmail, friendEmail, amount);
            if (transactionService.getUserBalance(requesterEmail) >= amount) {
                transactionService.userAccept(transactionId, requesterEmail, true);
            }
        } catch (TransactionException e) {
            return makeStatusResponse(null, e).toString();
        }

        JSONObject res = makeStatusResponse(null, null);
        return res.toString();
    }

    @PostMapping(value = "transactions/requestMoneyINT")
    @ResponseBody
    public String requestMoneyINT(@RequestBody String data) throws UnsupportedEncodingException {

        JSONObject req = new JSONObject(Util.errorDecoder(data));

        String requesterToken = req.get("token").toString();
        String friendEmail = req.get("email").toString();
        double amount = Double.parseDouble(req.get("amount").toString());

        String requesterEmail = new String();
        try {
            requesterEmail = authService.getUserIdFromToken(requesterToken);
        } catch (AuthException e) {
            return makeStatusResponse(e, null).toString();
        }

        String transactionId = new String();
        try {
            transactionId = transactionService.makeInternalTransaction(friendEmail, requesterEmail, amount);
            transactionService.userAccept(transactionId, requesterEmail, true);
        } catch (TransactionException e) {
            return makeStatusResponse(null, e).toString();
        }

        JSONObject res = makeStatusResponse(null, null);
        return res.toString();
    }

    @PostMapping(value = "transactions/confirmTransaction")
    @ResponseBody
    public String confirmTransaction(@RequestBody String data) throws UnsupportedEncodingException {

        JSONObject req = new JSONObject(data);

        String requesterToken = req.get("token").toString();
        String transactionId = req.get("transactionId").toString();
        boolean confirmed = Boolean.parseBoolean(req.get("confirmed").toString());

        String requesterEmail = new String();
        try {
            requesterEmail = authService.getUserIdFromToken(requesterToken);
        } catch (AuthException e) {
            return makeStatusResponse(e, null).toString();
        }

        try {
            Transaction transaction = transactionService.getTransaction(transactionId);
            if (transaction.getPayerEmail().equalsIgnoreCase(requesterEmail)) {
                if (transaction.getAmount() < transactionService.getUserBalance(requesterEmail)) {
                    transactionService.userAccept(transactionId, requesterEmail, confirmed);
                }
                if (!confirmed) {
                    transactionService.userAccept(transactionId, requesterEmail, confirmed);
                }
            } else if (transaction.getPayeeEmail().equalsIgnoreCase((requesterEmail))) {
                transactionService.userAccept(transactionId, requesterEmail, confirmed);
            }
        } catch (TransactionException e) {
            return makeStatusResponse(null, e).toString();
        }

        JSONObject res = makeStatusResponse(null, null);
        return res.toString();
    }


    private JSONObject makeStatusResponse(AuthException ae, TransactionException te){
        JSONObject res = new JSONObject();
        if (ae == null && te == null) {
            res.put("isSuccess", true);
        } else {
            res.put("isSuccess", false);
        }

        if (ae != null) {
            res.put("tokenStatus", false);
            res.put("reason", ae.getErrorDescription());
        } else {
            res.put("tokenStatus", true);
        }

        if (te != null) {
            res.put("transactionRequestStatus", false);
            res.put("reason", te.getErrorDescription());
        } else {
            res.put("transactionRequestStatus", true);
        }

        return res;
    }

}