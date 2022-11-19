package com.moneytransfer.backendserver.objects;

import org.json.JSONObject;
import org.springframework.context.ApplicationEvent;

public class EventPacket extends ApplicationEvent {
    private int eventCode;
    /**
     * reserved code.
     * 0: mojaSyncService -> MojaParticipantsController
     * 1: MojaParticipantsController -> mojaSyncService
     *
     */

    private JSONObject data;

    public EventPacket(int eventCode, JSONObject data, Object publisher) {
        super(publisher);
        this.eventCode = eventCode;
        this.data = data;
    }

    public int getEventCode() {
        return eventCode;
    }

    public void setEventCode(int eventCode) {
        this.eventCode = eventCode;
    }

    public JSONObject getData() {
        return data;
    }

    public void setData(JSONObject data) {
        this.data = data;
    }
}
