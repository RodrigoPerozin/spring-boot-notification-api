package com.notificationapi.notificationapi.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Data
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(hidden = true)
    private Long id;

    @NotBlank(message = "Endpoint is required")
    @Schema(description = "Endpoint of notification", required = true, minLength = 3, maxLength = 500)
    @Size(min = 3, max = 500, message = "Endpoint must be between 3 and 500 characters")
    private String endpoint;

    @Nullable
    @Column(name = "expiration_time", nullable = true)
    @Schema(description = "Expiration time of subscription", required = false, minLength = 3)
    private Long expirationTime;

    @Embedded
    @Schema(description = "Keys of subscription", required = true)
    private Keys keys;

    public Keys getKeys() {
        return keys;
    }

    public void setKeys(Keys keys) {
        this.keys = keys;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public Long getExpirationTime() {
        return expirationTime;
    }
    
    public void setExpirationTime(Long expirationTime) {
        this.expirationTime = expirationTime;
    }
    
}
