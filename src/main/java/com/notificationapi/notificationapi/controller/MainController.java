package com.notificationapi.notificationapi.controller;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.notificationapi.notificationapi.model.VapidKeys;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@Controller
public class MainController {
    
    @GetMapping("/")
    @Operation(summary = "Redireciona para a tela de login")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Página entregue comm sucesso"),
        @ApiResponse(responseCode = "400", description = "Erro na obtenção da página"),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public String index() {
        return "index";
    }

    @GetMapping("/home")
    @Operation(summary = "Redireciona para a tela de início")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Página entregue comm sucesso"),
        @ApiResponse(responseCode = "400", description = "Erro na obtenção da página"),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public String home() {
        return "home";
    }

    @GetMapping("/profile")
    @Operation(summary = "Redireciona para a tela de perfil do usuário")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Página entregue comm sucesso"),
        @ApiResponse(responseCode = "400", description = "Erro na obtenção da página"),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public String profile() {
        return "profile";
    }

    @GetMapping("/public_key")
    @Operation(summary = "Obtem a chave pública do servidor")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Chave pública entregue com sucesso"),
        @ApiResponse(responseCode = "400", description = "Erro na obtenção da chave pública"),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<String> publicKey() {
        return ResponseEntity.ok(VapidKeys.getPUBLIC_KEY());
    }

    @GetMapping("/favicon.ico")
    @Operation(summary = "Redireciona para o favicon")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Favicon entregue com sucesso"),
        @ApiResponse(responseCode = "400", description = "Erro na obtenção do favicon"),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<String> favicon() {
        return ResponseEntity.ok("https://cdn.pixabay.com/photo/2015/12/16/17/41/bell-1096280_1280.png");
    }
    
}
