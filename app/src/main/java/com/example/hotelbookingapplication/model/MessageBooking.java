package com.example.hotelbookingapplication.model;

import java.io.Serializable;

public class MessageBooking implements Serializable {
    private int code;
    private String message;

    public MessageBooking(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "MessageBooking{" +
                "code=" + code +
                ", message='" + message + '\'' +
                '}';
    }
}
