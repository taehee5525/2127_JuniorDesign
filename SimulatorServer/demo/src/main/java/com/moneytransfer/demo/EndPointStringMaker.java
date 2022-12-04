package com.moneytransfer.demo;

public class EndPointStringMaker {
    private String preFix;
    private String postFix;
    private String portNum;

    public EndPointStringMaker(String preFix, String postFix) {
        this.preFix = preFix;
        this.postFix = postFix;
    }

    public String getEndPoint(String portNum) {
        return preFix + portNum + postFix;
    }
}
