package com.notificationapi.notificationapi.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Embeddable
@Data
public class Keys {

    @NotBlank(message = "Auth is required")
    @Schema(description = "Auth key of subscription", required = true, minLength = 3, maxLength = 200)
    @Size(min = 3, max = 200, message = "Auth key must be between 3 and 200 characters")
    private String auth;

    @NotBlank(message = "P256dh is required")
    @Schema(description = "P256dh key of subscription", required = true, minLength = 3, maxLength = 500)
    @Size(min = 3, max = 500, message = "P256dh key must be between 3 and 500 characters")
    private String p256dh;
    
    public String getP256dh() {
        return p256dh;
    }

    public void setP256dh(String p256dh) {
        this.p256dh = p256dh;
    }

    public String getAuth() {
        return auth;
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }
}
