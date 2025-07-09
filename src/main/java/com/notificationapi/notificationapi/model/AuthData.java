package com.notificationapi.notificationapi.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class AuthData {
    
    @Schema(description = "System user e-mail", required = true, minLength = 3, maxLength = 200)
    public String user;

    @Schema(description = "System user password", required = true, minLength = 3, maxLength = 32)
    public String password;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
