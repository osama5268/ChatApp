package com.chatapp.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Messages {
    @Id
    @GeneratedValue
    private long id;
    private String message;
    @ManyToOne(targetEntity = User.class)
    private User userFrom;
    @ManyToOne(targetEntity = User.class)
    private User userTo;
    private LocalDateTime time;
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public User getUserFrom() {
        return userFrom;
    }
    public void setUserFrom(User userFrom) {
        this.userFrom = userFrom;
    }
    public User getUserTo() {
        return userTo;
    }
    public void setUserTo(User userTo) {
        this.userTo = userTo;
    }
    public LocalDateTime getTime() {
        return time;
    }
    public void setTime(LocalDateTime time) {
        this.time = time;
    }
    @Override
    public String toString() {
        return "Messages [id=" + id + ", message=" + message + ", time=" + time + ", userFrom=" + userFrom + ", userTo="
                + userTo + "]";
    }    
}
