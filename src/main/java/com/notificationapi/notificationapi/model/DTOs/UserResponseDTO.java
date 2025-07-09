package com.notificationapi.notificationapi.model.DTOs;

import com.notificationapi.notificationapi.model.User;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class UserResponseDTO {

    @Schema(hidden = true)
    private Long id;

    @Schema(description = "System user", required = true, minLength = 3, maxLength = 200)
    private String user;

    @Schema(description = "User e-mail", required = true, minLength = 3, maxLength = 100)
    private String email;

    @Schema(description = "User role", required = true)
    private RoleDTO roleDTO;

    public UserResponseDTO(User user) {
        this.id = user.getId();
        this.user = user.getUser();
        this.email = user.getEmail();
        this.roleDTO = user.getRole().toDTO();
    }

    public UserResponseDTO() {
        
    }

    public Long getId() {
        return id;
    }

    public String getUser() {
        return user;
    }

    public String getEmail() {
        return email;
    }

    public RoleDTO getRoleDTO() {
        return roleDTO;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setRoleDTO(RoleDTO roleDTO) {
        this.roleDTO = roleDTO;
    }

}
