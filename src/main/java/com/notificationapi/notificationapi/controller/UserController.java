package com.notificationapi.notificationapi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.notificationapi.notificationapi.model.DTOs.UserDTO;
import com.notificationapi.notificationapi.model.DTOs.UserResponseDTO;
import com.notificationapi.notificationapi.model.Notification;
import com.notificationapi.notificationapi.model.Role;
import com.notificationapi.notificationapi.repositories.UsersRepository;
import com.notificationapi.notificationapi.utils.MD5Util;
import com.notificationapi.notificationapi.repositories.RoleRepository;

import com.notificationapi.notificationapi.model.User;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {
    
    @Autowired
    public UsersRepository usersRepository;

    @Autowired
    public RoleRepository rolesRepository;

    @PostMapping("create")
    @Operation(summary = "Cria um novo usuário")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuário criado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Erro na criação do usuário"),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<UserResponseDTO> create(@RequestBody @Valid UserDTO userDTO) {
        
        try {
            Role role = rolesRepository.findById(userDTO.getRoleId()).orElseThrow(() -> new RuntimeException("Role not found with id: " + userDTO.getRoleId()));
            User user = new User(userDTO.getUser(), userDTO.getPassword(), userDTO.getEmail(), role);
            
            user.setPassword(MD5Util.toMD5(user.getPassword()));
            User user_saved = usersRepository.save(user);
            
            UserResponseDTO userResponseDTO = new UserResponseDTO(user_saved);
            return ResponseEntity.ok(userResponseDTO);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(null);
        }
        
    }

    @GetMapping("select")
    @Operation(summary = "Lista todos os usuários")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Todos os usuários listados com sucesso"),
        @ApiResponse(responseCode = "400", description = "Erro ao listar os usuários"),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<List<UserResponseDTO>> select() {
        
        try {
            List<User> users = usersRepository.findAll();
            List<UserResponseDTO> usersListDTO = users.stream()
                .map(UserResponseDTO::new)
                .toList();
            return ResponseEntity.ok(usersListDTO);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(null);
        }
        
    }

    @GetMapping("select/{id}")
    @Operation(summary = "Lista um usuario específico")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuário listado com sucesso"),
        @ApiResponse(responseCode = "401", description = "Usuário não encontrado"),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<UserResponseDTO> selectOne(@PathVariable("id") Long id) {

        try {
            User user = usersRepository.getReferenceById(id);
            return ResponseEntity.ok(user.toResponseDTO());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatusCode.valueOf(401)).body(null);
        }

    }

}
