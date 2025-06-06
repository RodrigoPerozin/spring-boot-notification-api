package com.notificationapi.notificationapi.repositories;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.notificationapi.notificationapi.model.Notification;
import com.notificationapi.notificationapi.model.DTOs.NotificationDTO;
import com.notificationapi.notificationapi.service.OneTimeTaskManager;

@Repository
public class NotificationsRepository {
    
    private List<Notification> notifications = new ArrayList<Notification>();
    public OneTimeTaskManager oneTimeTaskManager = new OneTimeTaskManager();

    public NotificationsRepository() {
        // Constructor
    }

    public void addNotification(Notification notification) {
        
        notifications.add(notification);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime localDateTime = LocalDateTime.parse(notification.getTimestamp(), formatter);
        ZoneId zoneId = ZoneId.of("America/Sao_Paulo");
        ZonedDateTime zonedDateTime = localDateTime.atZone(zoneId);

        Runnable task = () -> {
            System.out.println("Notification: " + notification.getMessage());
        };

        oneTimeTaskManager.scheduleTask(notification.getId(), zonedDateTime, task);
    }

    public void destroyNotification(Notification notification) {
        
        notifications.remove(notification);
        
        oneTimeTaskManager.cancelTask(notification.getId());

    }

    public void destroyNotifications() {

        notifications.clear();
    
        oneTimeTaskManager.cancelAllTasks();

    }

    public List<Notification> getNotifications() {
        return notifications;
    }

    public Notification getNotification(String id) {
        for (Notification notification : notifications) {
            if (notification.getId().equals(id)) {
                return notification;
            }
        }
        return null;
    }

    public Notification updateNotification(String id, NotificationDTO notificationDto) {
        for (int i = 0; i < notifications.size(); i++) {

            if (notifications.get(i).getId().equals(id)) {

                Notification notification = new Notification();
                notification.setId(id);

                if (notificationDto.getTitle() != null) notification.setTitle(notificationDto.getTitle()); else notification.setTitle(notifications.get(i).getTitle());
                
                if (notificationDto.getMessage() != null) notification.setMessage(notificationDto.getMessage()); else notification.setMessage(notifications.get(i).getMessage());
                
                if (notificationDto.getType() != null) notification.setType(notificationDto.getType()); else notification.setType(notifications.get(i).getType());

                if (notificationDto.getRecipient() != null) notification.setRecipient(notificationDto.getRecipient()); else notification.setRecipient(notifications.get(i).getRecipient());

                if (notificationDto.getSender() != null) notification.setSender(notificationDto.getSender()); else notification.setSender(notifications.get(i).getSender());   

                if (notificationDto.getTimestamp() != null) notification.setTimestamp(notificationDto.getTimestamp()); else notification.setTimestamp(notifications.get(i).getTimestamp());

                notifications.set(i, notification);
                return notification;

            }
        }
        return null;
    }

}
