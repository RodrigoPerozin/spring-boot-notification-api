package com.notificationapi.notificationapi.model;

import com.notificationapi.notificationapi.model.DTOs.RoleDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(hidden = true, description = "Unique identifier for the role", example = "1")
    private Long id;

    @NotBlank(message = "Role is required")
    @Schema(description = "User role description", required = true, minLength = 3, maxLength = 100)
    @Size(min = 3, max = 100, message = "Role description must be between 3 and 100 characters")
    private String role;

    public Role(
            @NotBlank(message = "Role is required") @Size(min = 3, max = 100, message = "Role description must be between 3 and 100 characters") String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RoleDTO toDTO() {
        return new RoleDTO(this.id, this.role);
    }

}
