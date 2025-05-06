package com.notificationapi.notificationapi.repositories;

import java.util.ArrayList;
import java.util.List;

import com.notificationapi.notificationapi.model.Notification;
import com.notificationapi.notificationapi.model.NotificationDTO;

import lombok.Data;

@Data
public class NotificationsRepository {
    
    private List<Notification> notifications = new ArrayList<Notification>();

    public NotificationsRepository() {
        // Constructor
    }

    public void addNotification(Notification notification) {
        notifications.add(notification);
    }

    public void destroyNotification(Notification notification) {
        notifications.remove(notification);
    }

    public void destroyNotifications() {
        notifications.clear();
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
