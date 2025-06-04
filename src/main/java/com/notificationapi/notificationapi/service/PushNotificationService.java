package com.notificationapi.notificationapi.service;

import nl.martijndwars.webpush.Notification;
import nl.martijndwars.webpush.PushService;
import nl.martijndwars.webpush.Utils;

import com.google.gson.Gson;
import com.notificationapi.notificationapi.model.Subscription;
import com.notificationapi.notificationapi.model.VapidKeys;
import com.notificationapi.notificationapi.model.DTOs.NotificationDTO;

public class PushNotificationService {

    private final String subject = "mailto:rodrigodperozin@email.com";

    public void sendNotification(Subscription subscription, String title, String message) throws Exception {

        try {
            NotificationDTO notificationDTO = new NotificationDTO(title, message, "info", subscription.getEndpoint(), "NotificationAPI", String.valueOf(System.currentTimeMillis()));
            Gson gson = new Gson();
            String jsonNotification = gson.toJson(notificationDTO);
            
            Notification notification = new Notification(
                subscription.getEndpoint(),
                subscription.getKeys().getP256dh(),
                subscription.getKeys().getAuth(),
                jsonNotification
            );
            PushService pushService = new PushService();
            pushService.setPublicKey(Utils.loadPublicKey(VapidKeys.getPUBLIC_KEY()));
            pushService.setPrivateKey(Utils.loadPrivateKey(VapidKeys.getPRIVATE_KEY()));
            pushService.setSubject(subject);
    
            pushService.send(notification);
        } catch (Exception e) {
            System.out.println("Erro ao enviar notificação: " + e.getMessage());
        }

    }
}
