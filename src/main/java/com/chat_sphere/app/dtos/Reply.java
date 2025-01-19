package com.chat_sphere.app.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class Reply {

    public final static short INFO = 1;
    public final static short ERROR = 2;
    public final static short WARN = 3;

    private short flag;
    private Object data;
    private List<String> messages;
    private boolean hasMore;
    private long totalNumberOfRecords;
    private String token;
    @JsonIgnore
    private Map<String,Object> session;

    public Reply() {
        session = new HashMap<>();
        messages = new ArrayList<>();
        flag = INFO;
    }

    public void setError(String message){
        flag = ERROR;
        messages.add(message);
    }

    public void setInfo(String message){
        flag = INFO;
        messages.add(message);
    }
    public void setWarning(String message){
        flag = WARN;
        messages.add(message);
    }

    public void setAttribute(String key , Object value){
        session.put(key,value);
    }

    public Object getAttribute(String key){
        return session.get(key);
    }

    public boolean containsAttribute(String key){
        return session.containsKey(key);
    }

    public void setSession(Map<String, Object> session) {
        this.session = session == null ? new HashMap<>() : session;
    }
}
