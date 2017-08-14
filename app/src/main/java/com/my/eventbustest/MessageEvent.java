package com.my.eventbustest;

import java.io.Serializable;

/**
 * Created by ZongJie on 2017/8/9.
 */

public class MessageEvent implements Serializable{
    private static final long serialVersionUID = 1L;
    private String message;
    private String time;

    public MessageEvent(String message, String time) {
        this.message = message;
        this.time = time;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
