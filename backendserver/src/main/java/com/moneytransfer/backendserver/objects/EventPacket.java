package com.moneytransfer.backendserver.objects;

import org.json.JSONObject;
import org.springframework.context.ApplicationEvent;

public class EventPacket extends ApplicationEvent {
    private int eventCode;
    /**
     * reserved code.
     * 0: mojaSyncService -> MojaParticipantsController (init lookup check)
     * 1: MojaParticipantsController -> mojaSyncService (result)
     * 2: accountLookupService -> MojaParticipantsController (als add checkup)
     * 3: MojaParticipantsController -> accountLookupService (als add checkup)
     * 4: MojaUserLookupService -> MojaParticipantsController (lookup external user)
     * 5: MojaParticipantsController -> MojaUserLookupService (lookup external user)
     * 6: MojaUserLookupService -> --- MojaUserLookupController (lookup external user)
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
