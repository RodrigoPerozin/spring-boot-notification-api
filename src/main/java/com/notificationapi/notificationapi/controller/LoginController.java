package com.notificationapi.notificationapi.controller;

import java.util.Base64;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.notificationapi.notificationapi.model.AuthData;
import com.notificationapi.notificationapi.model.User;
import com.notificationapi.notificationapi.model.DTOs.AuthResponseDTO;
import com.notificationapi.notificationapi.model.DTOs.TokenDTO;
import com.notificationapi.notificationapi.repositories.UsersRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/login")
public class LoginController {
    
    @Autowired
    public UsersRepository usersRepository;

    @PostMapping()
    @Operation(summary = "Realiza o login do usuário")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Login realizado com sucesso"),
        @ApiResponse(responseCode = "401", description = "Credenciais inválidas"),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<String> auth(@RequestBody @Valid AuthData authData) {
        if (authData.getUser() == null || authData.getPassword() == null) {
            return ResponseEntity.badRequest().body("Usuário e senha são obrigatórios");
        }
        Optional<User> userOptional = usersRepository.findByEmailAndPassword(authData.getUser(), authData.getPassword());
        if (userOptional.isEmpty()) {
            return ResponseEntity.status(401).build();
        }
        User user = userOptional.get();
        AuthResponseDTO authResponseDTO = new AuthResponseDTO(user);

        return ResponseEntity.ok(authResponseDTO.getAuthResponseToken());
    }

    @PostMapping("refresh")
    @Operation(summary = "Renova o login do usuário")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Login realizado com sucesso"),
        @ApiResponse(responseCode = "401", description = "Credenciais inválidas"),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<String> refresh(@RequestBody @Valid TokenDTO tokenDTO) {
        
        try {
            String[] tokenSplited = tokenDTO.getToken().split("\\.");
            byte[] decodedBytes = Base64.getDecoder().decode(tokenSplited[1]);
            String decodedString = new String(decodedBytes);
            ObjectMapper objectMapper = new ObjectMapper();

            @SuppressWarnings("unchecked")
            Map<String, Object> map1 = objectMapper.readValue(decodedString, Map.class);
            String userDataString = map1.get("user").toString();

            @SuppressWarnings("unchecked")
            Map<String, Object> map2 = objectMapper.readValue(userDataString, Map.class);
            String userDTODataString = map2.get("userDTO").toString();
            Map<String, Object> mapUser = parseToMap(userDTODataString);
            Long userId = Long.parseLong(mapUser.get("id").toString());

            User user = usersRepository.findById(userId)
                .orElseThrow(() -> new Exception("Usuário não encontrado"));

            if(user != null) {
                AuthResponseDTO authResponseDTO = new AuthResponseDTO(user);
                return ResponseEntity.ok(authResponseDTO.getAuthResponseToken());
            } else {
                return ResponseEntity.status(401).body("Usuário não encontrado");
            }

        } catch (Exception e) {
            System.out.println("Erro ao decodificar o token: " + e.getMessage());
            return ResponseEntity.status(401).body("Token inválido");
        }
        
    }

    public static Map<String, Object> parseToMap(String input) {
        input = input.trim();
        if (input.startsWith("{")) input = input.substring(1);
        if (input.endsWith("}")) input = input.substring(0, input.length() - 1);

        Map<String, Object> map = new LinkedHashMap<>();
        int braceLevel = 0;
        StringBuilder keyVal = new StringBuilder();
        for (char c : input.toCharArray()) {
            if (c == ',' && braceLevel == 0) {
                putKeyValue(map, keyVal.toString().trim());
                keyVal.setLength(0);
            } else {
                if (c == '{') braceLevel++;
                else if (c == '}') braceLevel--;
                keyVal.append(c);
            }
        }
        if (keyVal.length() > 0) {
            putKeyValue(map, keyVal.toString().trim());
        }
        return map;
    }

    private static void putKeyValue(Map<String, Object> map, String entry) {
        int idx = entry.indexOf('=');
        if (idx == -1) return;
        String key = entry.substring(0, idx).trim();
        String value = entry.substring(idx + 1).trim();

        if (value.startsWith("{") && value.endsWith("}")) {
            map.put(key, parseToMap(value));
        } else {
            map.put(key, value);
        }
    }

}
