package com.notificationapi.notificationapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

import com.notificationapi.notificationapi.model.Subscription;
import com.notificationapi.notificationapi.repositories.SubscriptionRepository;
import com.notificationapi.notificationapi.service.PushNotificationService;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class SubscriptionController {

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @PostMapping("/subscribe")
    @Operation(summary = "Faz a inscrição nas notificações push")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Inscrição realizada com sucesso"),
        @ApiResponse(responseCode = "400", description = "Erro na inscrição"),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<String> subscribe(@RequestBody @Valid Subscription subscription) throws Exception{

        try {
            PushNotificationService pushNotificationService = new PushNotificationService();
            subscriptionRepository.save(subscription);
            pushNotificationService.sendNotification(subscription, "Notification API", "Inscrição confirmada com sucesso!");
            return ResponseEntity.ok("Inscrição confirmada com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro ao inscrever-se nas notificação: " + e.getMessage());
            return ResponseEntity.status(500).body("Erro ao inscrever-se nas notificações: " + e.getMessage());
        }

    }
    
}
