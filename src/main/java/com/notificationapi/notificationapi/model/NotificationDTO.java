package com.notificationapi.notificationapi.model;

import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class NotificationDTO {

    @Schema(hidden = true)
    private String id = UUID.randomUUID().toString();
    
    @Schema(description = "Title of notification", required = false)
    private String title;

    @Schema(description = "Message of notification", required = false)
    private String message;

    @Schema(description = "Type of notification", required = false)
    private String type;

    @Schema(description = "Recipient", required = false)
    private String recipient;

    @Schema(description = "Sender", required = false)
    private String sender;

    @Schema(description = "Timestamp", required = false)
    private String timestamp;

    public Notification(String title, String message, String type, String recipient, String sender, String timestamp) {
        this.title = title;
        this.message = message;
        this.type = type;
        this.recipient = recipient;
        this.sender = sender;
        this.timestamp = timestamp;
    }

    public Notification() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
