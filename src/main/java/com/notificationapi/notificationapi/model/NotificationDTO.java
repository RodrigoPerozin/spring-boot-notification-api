package com.notificationapi.notificationapi.model;

import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class NotificationDTO {

    @Schema(hidden = true)
    private String id = UUID.randomUUID().toString();
    
    @Schema(description = "Title of notification", required = false, minLength = 3, maxLength = 200, example = "Test Notification")
    @Size(min = 3, max = 200, message = "Title must be between 3 and 200 characters")
    private String title;

    @Schema(description = "Message of notification", required = false, minLength = 3, maxLength = 2000, example = "This is a test message")
    @Size(min = 3, max = 2000, message = "Message must be between 3 and 2000 characters")
    private String message;

    @Schema(description = "Type of notification", required = false, minLength = 3, maxLength = 20, example = "alert")
    @Size(min = 3, max = 20, message = "Type must be between 3 and 20 characters")
    private String type;

    @Schema(description = "Recipient", required = false, minLength = 3, maxLength = 200, example = "Your Name")
    @Size(min = 3, max = 200, message = "Recipient must be between 3 and 200 characters")
    private String recipient;

    @Schema(description = "Sender", required = false, minLength = 3, maxLength = 200, example = "System")
    @Size(min = 3, max = 200, message = "Sender must be between 3 and 200 characters")
    private String sender;

    @Schema(description = "Timestamp", required = false, minLength = 3, maxLength = 25, example = "2023-10-01 23:00:00", format = "yyyy-MM-dd' 'HH:mm:ss")
    @Size(min = 3, max = 25, message = "Sender must be between 3 and 25 characters")
    private String timestamp;

    public NotificationDTO(String title, String message, String type, String recipient, String sender, String timestamp) {
        this.title = title;
        this.message = message;
        this.type = type;
        this.recipient = recipient;
        this.sender = sender;
        this.timestamp = timestamp;
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
