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
    private UUID quotes_Id;
    private String user_Note;
    private boolean completed;
    private String payer_Name;
    private String payee_Name;
    private String type;
    private String amount;
    private String target_FSP;
    private String current_FSP;
    
    public UUID getId() {
        return quotes_Id;
    }

    public void setId(UUID uid) { quotes_Id = uid; }

    public void setUserNote(String userNote) {
        this.user_Note = userNote;
    }

    public String getUserNote() {return user_Note;}

    public void setCompleted(boolean completed) { this.completed = completed; }

    public boolean getCompleted() {return completed;}
    public String getPayerName() {return this.payer_Name;}
    public String getPayeeName() {return this.payee_Name;}
    public void setPayerName(String payerName) {this.payer_Name = payerName;}
    public void setPayeeName(String payeeName) {this.payee_Name = payeeName;}

    public void setType(String type) { this.type = type; }

    public String getType() { return this.type; }

    public void setAmount(String amount) { this.amount = amount; }

    public String getAmount() { return this.amount; }

    public void setTargetFSP(String targetFSP) { this.target_FSP = targetFSP; }

    public String getTargetFSP() { return this.target_FSP; }

    public void setCurrentFSP(String currentFSP) { this.current_FSP = currentFSP; }

    public String getCurrentFSP() { return this.current_FSP; }

}
