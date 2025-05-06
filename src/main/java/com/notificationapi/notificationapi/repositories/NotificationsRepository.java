package com.notificationapi.notificationapi.repositories;

import java.util.ArrayList;
import java.util.List;

import com.notificationapi.notificationapi.model.Notification;

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

    public Notification updateNotification(String id, NotificationDTO notification) {
        for (int i = 0; i < notifications.size(); i++) {
            if (notifications.get(i).getId().equals(id)) {
                notification.setId(id);
                notifications.set(i, notification);
                return notification;
            }
        }
        return null;
    }

}
