package com.moneytransfer.backendserver.objects;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.UUID;

@Entity
public class Quotes {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Id
    private UUID quotesId;
    private String userNote;
    private boolean completed;
    private String payerName;
    private String payeeName;

    public UUID getId() {
        return quotesId;
    }

    public void setId(UUID uid) { quotesId = uid; }

    public void setUserNote(String userNote) {
        this.userNote = userNote;
    }

    public String getUserNote() {return userNote;}

    public void setCompleted(boolean completed) { this.completed = completed; }

    public boolean getCompleted() {return completed;}
    public String getPayerName() {return this.payerName;}
    public String getPayeeName() {return this.payeeName;}
    public void setPayerName(String payerName) {this.payerName = payerName;}
    public void setPayeeName(String payeeName) {this.payeeName = payeeName;}
}
