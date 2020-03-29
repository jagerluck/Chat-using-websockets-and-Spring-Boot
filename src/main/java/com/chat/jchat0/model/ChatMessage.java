package com.chat.jchat0.model;

import java.time.LocalDate;
import java.time.LocalTime;

public class ChatMessage {
    private MessageType type;
    private String content;
    private String sender;
    private String time;

    public enum MessageType {
        CHAT,
        JOIN,
        LEAVE
    }

    public MessageType getType() {
        return type;
    }

    public void setType(MessageType type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getTime() {
        LocalTime localTime = LocalTime.now();
        LocalDate localDate = LocalDate.now();
        return time = localTime + "\n" + localDate;
    }
}
