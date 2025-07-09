package com.notificationapi.notificationapi.model.DTOs;

import com.notificationapi.notificationapi.model.User;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.google.gson.Gson;

@Data
public class AuthResponseDTO {

    @Schema(description = "Dados do usuário autenticado", required = true)
    private UserResponseDTO userDTO;

    @Schema(description = "timestamp de expiração do token em milisegundos", required = true)
    private Long expiresAt;

    public AuthResponseDTO(){
        this.expiresAt = System.currentTimeMillis() + (12 * 60 * 60 * 1000); //12 h
        this.userDTO = new UserResponseDTO();
    }
    
    public AuthResponseDTO(User user) {
        this.userDTO = user.toResponseDTO();
        this.expiresAt = System.currentTimeMillis() + (12 * 60 * 60 * 1000); //12 h
    }
    
    public UserResponseDTO getUserDTO() {
        return userDTO;
    }

    public void setUserDTO(UserResponseDTO userDTO) {
        this.userDTO = userDTO;
    }

    public Long getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(Long expiresAt) {
        this.expiresAt = expiresAt;
    }

    public String getAuthResponseToken() {
        try {
            Gson gson = new Gson();
            String jsonPayload = gson.toJson(this);
    
            Algorithm algorithm = Algorithm.HMAC256("hybf678fv46".getBytes());
    
            String token = JWT.create()
                .withIssuer("notification-api")
                .withAudience("notification-api-users")
                .withSubject("user-session")
                .withClaim("user", jsonPayload)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(this.expiresAt))
                .sign(algorithm);
    
            return token;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao gerar o token de autenticação", e);
        }
    }

}
