package com.moneytransfer.backendserver.objects;

import com.moneytransfer.backendserver.Util;

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
    private UUID quotes_id;
    private String userNote;
    private boolean completed;

    public UUID getId() {
        return quotes_id;
    }

    public void setId() { quotes_id = Util.generateQuotingUID(); }

    public void setUserNote(String userNote) {
        this.userNote = userNote;
    }

    public String getUserNote() {return userNote;}

    public void setCompleted(boolean completed) { this.completed = completed; }

    public boolean getCompleted() {return completed;}
}
