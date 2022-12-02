package com.chatapp.model;

import java.time.LocalDateTime;
public class ChatMessage {
    //this class is for client server communication
    private String message;
    private LocalDateTime time;
    private String sentTo;
    private String sentFrom;

    public ChatMessage() {
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public String getSentTo() {
        return sentTo;
    }

    public void setSentTo(String sentTo) {
        this.sentTo = sentTo;
    }

    public String getSentFrom() {
        return sentFrom;
    }

    public void setSentFrom(String sentFrom) {
        this.sentFrom = sentFrom;
    }

    @Override
    public String toString() {
        return "ChatMessage [message=" + message + ", sentFrom=" + sentFrom + ", sentTo=" + sentTo + ", time=" + time
                + "]";
    }

}