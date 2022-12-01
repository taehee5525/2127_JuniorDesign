package com.moneytransfer.backendserver.objects;

public class ExtUser {

    private String partyIdType;
    private String partyIdentifier;
    private String fspId;
    private String name;

    public String getPartyIdType() {
        return partyIdType;
    }

    public void setPartyIdType(String partyIdType) {
        this.partyIdType = partyIdType;
    }

    public String getPartyIdentifier() {
        return partyIdentifier;
    }

    public void setPartyIdentifier(String partyIdentifier) {
        this.partyIdentifier = partyIdentifier;
    }

    public String getFspId() {
        return fspId;
    }

    public void setFspId(String fspId) {
        this.fspId = fspId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
