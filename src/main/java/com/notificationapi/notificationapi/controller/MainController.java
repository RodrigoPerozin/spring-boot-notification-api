package com.notificationapi.notificationapi.controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@Controller
public class MainController {
    
    @GetMapping("/")
    @Operation(summary = "Redireciona para a tela de apresentação")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Página entregue comm sucesso"),
        @ApiResponse(responseCode = "400", description = "Erro na obtenção da página"),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public String index() {
        return "index";
    }
    
}
