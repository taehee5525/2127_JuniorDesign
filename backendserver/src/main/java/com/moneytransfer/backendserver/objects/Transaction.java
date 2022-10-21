package com.moneytransfer.backendserver.objects;

import javax.persistence.*;

@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String transactionId;
    private double amount;
    private boolean payerConfirmed;
    private boolean payeeConfirmed;
    private Long timestamp;

    //Payer field
    @Column(name="payer_FSP")
    private String payerFSP;
    private String payerEmail;
    private String payerPhoneNumber;
    private String payerRoutingNum;
    private String payerAccNum;

    //Payee field
    @Column(name="payee_FSP")
    private String payeeFSP;
    private String payeeEmail;
    private String payeePhoneNumber;
    private String payeeRoutingNum;
    private String payeeAccNum;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public boolean isPayerConfirmed() {
        return payerConfirmed;
    }

    public void setPayerConfirmed(boolean payerConfirmed) {
        this.payerConfirmed = payerConfirmed;
    }

    public boolean isPayeeConfirmed() {
        return payeeConfirmed;
    }

    public void setPayeeConfirmed(boolean payeeConfirmed) {
        this.payeeConfirmed = payeeConfirmed;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public String getPayerFSP() {
        return payerFSP;
    }

    public void setPayerFSP(String payerFSP) {
        this.payerFSP = payerFSP;
    }

    public String getPayerEmail() {
        return payerEmail;
    }

    public void setPayerEmail(String payerEmail) {
        this.payerEmail = payerEmail;
    }

    public String getPayerPhoneNumber() {
        return payerPhoneNumber;
    }

    public void setPayerPhoneNumber(String payerPhoneNumber) {
        this.payerPhoneNumber = payerPhoneNumber;
    }

    public String getPayerRoutingNum() {
        return payerRoutingNum;
    }

    public void setPayerRoutingNum(String payerRoutingNum) {
        this.payerRoutingNum = payerRoutingNum;
    }

    public String getPayerAccNum() {
        return payerAccNum;
    }

    public void setPayerAccNum(String payerAccNum) {
        this.payerAccNum = payerAccNum;
    }

    public String getPayeeFSP() {
        return payeeFSP;
    }

    public void setPayeeFSP(String payeeFSP) {
        this.payeeFSP = payeeFSP;
    }

    public String getPayeeEmail() {
        return payeeEmail;
    }

    public void setPayeeEmail(String payeeEmail) {
        this.payeeEmail = payeeEmail;
    }

    public String getPayeePhoneNumber() {
        return payeePhoneNumber;
    }

    public void setPayeePhoneNumber(String payeePhoneNumber) {
        this.payeePhoneNumber = payeePhoneNumber;
    }

    public String getPayeeRoutingNum() {
        return payeeRoutingNum;
    }

    public void setPayeeRoutingNum(String payeeRoutingNum) {
        this.payeeRoutingNum = payeeRoutingNum;
    }

    public String getPayeeAccNum() {
        return payeeAccNum;
    }

    public void setPayeeAccNum(String payeeAccNum) {
        this.payeeAccNum = payeeAccNum;
    }
}
