package com.notificationapi.notificationapi.controller;

import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

import com.notificationapi.notificationapi.model.Notification;
import com.notificationapi.notificationapi.model.NotificationDTO;
import com.notificationapi.notificationapi.repositories.NotificationsRepository;

import java.util.List;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@RequestMapping("/notification")
public class NotificationController {

    public NotificationsRepository notificationsRepository = new NotificationsRepository();

    @PostMapping("create")
    @Operation(summary = "Cria uma nova notificação")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Notificação criada com sucesso"),
        @ApiResponse(responseCode = "400", description = "Erro na criação da notificação"),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public Notification create(@RequestBody @Valid Notification notification) {
        notificationsRepository.addNotification(notification);
        return notification;
    }

    @GetMapping("select")
    @Operation(summary = "Lista todas as notificações")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Notificações listadas com sucesso"),
        @ApiResponse(responseCode = "400", description = "Erro na listagem das notificações"),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<List<Notification>> select() {
        List<Notification> notifications = notificationsRepository.getNotifications();
        if (notifications == null || notifications.isEmpty()) {
            return ResponseEntity.status(HttpStatusCode.valueOf(401)).body(null);
        }
        return ResponseEntity.ok(notifications);
    }

    @GetMapping("select/{id}")
    @Operation(summary = "Lista uma notificação")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Notificação listada com sucesso"),
        @ApiResponse(responseCode = "401", description = "Notificação não encontrada"),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<Notification> selectOne(@PathVariable("id") String id) {
        Notification notification = notificationsRepository.getNotification(id);
        if (notification == null) {
            return ResponseEntity.status(HttpStatusCode.valueOf(401)).body(null);
        }
        return ResponseEntity.ok(notification);
    }

    @DeleteMapping("delete")
    @Operation(summary = "Destrói todas as notificações")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Notificações destruídas com sucesso"),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<String> destroy() {
        notificationsRepository.destroyNotifications();
        return ResponseEntity.ok().body(null);
    }

    @DeleteMapping("delete/{id}")
    @Operation(summary = "Destrói uma notificação")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Notificação destruída com sucesso"),
        @ApiResponse(responseCode = "401", description = "Notificação não encontrada"),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<Notification> destroyOne(@PathVariable("id") String id) {
        Notification notification = notificationsRepository.getNotification(id);
        if (notification == null) {
            return ResponseEntity.status(HttpStatusCode.valueOf(401)).body(null);
        }else{
            notificationsRepository.destroyNotification(notification);
        }
        return ResponseEntity.ok().body(null);
    }

    @PutMapping("update/{id}")
    @Operation(summary = "Atualiza uma notificação")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Notificação atualizada com sucesso"),
        @ApiResponse(responseCode = "401", description = "Notificação não encontrada"),
        @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<Notification> update(@PathVariable("id") String id, @RequestBody @Valid NotificationDTO notificationDto) {
        Notification notificationToUpdate = notificationsRepository.getNotification(id);
        if (notificationToUpdate == null) {
            return ResponseEntity.status(HttpStatusCode.valueOf(401)).body(null);
        }else{
            notificationsRepository.updateNotification(id, notificationDto);
        }
        return ResponseEntity.ok().body(null);
    }

}
