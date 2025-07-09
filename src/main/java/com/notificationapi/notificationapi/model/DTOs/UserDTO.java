package com.notificationapi.notificationapi.model.DTOs;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserDTO {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(hidden = true, required = false)
    private Long id;

    @NotBlank(message = "User is required")
    @Schema(description = "System user", required = true, minLength = 3, maxLength = 200)
    @Size(min = 3, max = 200, message = "User must be between 3 and 200 characters")
    private String user;

    @NotBlank(message = "Password is required")
    @Schema(description = "User password", required = true, minLength = 3, maxLength = 35)
    @Size(min = 3, max = 35, message = "Password must be 35 characters")
    private String password;

    @NotBlank(message = "Email is required")
    @Schema(description = "User e-mail", required = true, minLength = 3, maxLength = 100)
    @Size(min = 3, max = 100, message = "Email must be 100 characters")
    private String email;
    
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_role", nullable = false)
    @Schema(description = "User role ID", required = true)
    private Long roleId;

    public UserDTO(
            @NotBlank(message = "User is required") @Size(min = 3, max = 200, message = "User must be between 3 and 200 characters") String user,
            @NotBlank(message = "Password is required") @Size(min = 3, max = 35, message = "Password must be 35 characters") String password,
            @NotBlank(message = "Email is required") @Size(min = 3, max = 100, message = "Email must be 100 characters") String email,
            Long roleId) {
        this.user = user;
        this.password = password;
        this.email = email;
        this.roleId = roleId;
    }
    
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
