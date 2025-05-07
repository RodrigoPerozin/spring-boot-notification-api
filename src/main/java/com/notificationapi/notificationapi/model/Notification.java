package com.notificationapi.notificationapi.model;

import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class Notification {

    @Schema(hidden = true)
    private String id = UUID.randomUUID().toString();
    
    @NotBlank(message = "Title is required")
    @Schema(description = "Title of notification", required = true, minLength = 3, maxLength = 200, example = "Test Notification")
    @Size(min = 3, max = 200, message = "Username must be between 3 and 200 characters")
    private String title;

    @NotBlank(message = "Message is required")
    @Schema(description = "Message of notification", required = true, minLength = 3, maxLength = 2000, example = "This is a test message")
    @Size(min = 3, max = 2000, message = "Message must be between 3 and 2000 characters")
    private String message;

    @NotBlank(message = "Type is required")
    @Schema(description = "Type of notification", required = true, minLength = 3, maxLength = 20, example = "alert")
    @Size(min = 3, max = 20, message = "Type must be between 3 and 20 characters")
    private String type;

    @NotBlank(message = "Recipient is required")
    @Schema(description = "Recipient", required = true, minLength = 3, maxLength = 200, example = "Your Name")
    @Size(min = 3, max = 200, message = "Recipient must be between 3 and 200 characters")
    private String recipient;

    @NotBlank(message = "Sender is required")
    @Schema(description = "Sender", required = true, minLength = 3, maxLength = 200, example = "System")
    @Size(min = 3, max = 200, message = "Sender must be between 3 and 200 characters")
    private String sender;

    @NotBlank(message = "Timestamp is required")
    @Schema(description = "Timestamp", required = true, minLength = 3, maxLength = 25, example = "2025-05-07 00:06:00", format = "yyyy-MM-dd' 'HH:mm:ss")
    @Size(min = 3, max = 25, message = "Timestamp must be between 3 and 25 characters")
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
