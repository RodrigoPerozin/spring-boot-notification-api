package com.notificationapi.notificationapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;

@Service
public class OneTimeTaskManager {

    @Autowired
    private ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
    {
        taskScheduler.initialize();
    }

    // Keep track of tasks
    private final Map<String, ScheduledFuture<?>> scheduledTasks = new ConcurrentHashMap<>();

    public void scheduleTask(String taskId, ZonedDateTime dateTime, Runnable task) {
        cancelTask(taskId); // Cancel existing task if any
        Date triggerTime = Date.from(dateTime.toInstant());

        @SuppressWarnings("deprecation")
        ScheduledFuture<?> future = taskScheduler.schedule(() -> {
            try {
                task.run();
            } finally {
                scheduledTasks.remove(taskId);
            }
        }, triggerTime);

        scheduledTasks.put(taskId, future);
    }

    public boolean cancelTask(String taskId) {
        ScheduledFuture<?> future = scheduledTasks.get(taskId);
        if (future != null) {
            boolean cancelled = future.cancel(false);
            scheduledTasks.remove(taskId);
            return cancelled;
        }
        return false;
    }

    public void cancelAllTasks() {
        for (String taskId : scheduledTasks.keySet()) {
            cancelTask(taskId);
        }
    }

    public boolean isScheduled(String taskId) {
        return scheduledTasks.containsKey(taskId);
    }
}
