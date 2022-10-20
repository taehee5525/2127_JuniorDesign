package com.moneytransfer.backendserver.objects;

public class Transaction {

    private long transactionId;
    private double amount;
    private boolean payerConfirmed;
    private boolean payeeConfirmed;
    private long timestamp;

    //Payer field
    private String payerFSP;
    private String payerEmail;
    private String payerPhoneNumber;
    private String payerRoutingNum;
    private String payerAccNum;

    //Payee field
    private String payeeFSP;
    private String payeeEmail;
    private String payeePhoneNumber;
    private String payeeRoutingNum;
    private String payeeAccNum;
}
