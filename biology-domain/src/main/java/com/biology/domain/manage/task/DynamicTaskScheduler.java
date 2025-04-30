package com.biology.domain.manage.task;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Component;

@Component
public class DynamicTaskScheduler {
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(10);
    private final Map<String, ScheduledFuture<?>> tasks = new ConcurrentHashMap<>();

    public void addTask(String taskId, Runnable task, long initialDelay, long period, TimeUnit unit) {
        removeTask(taskId); // 防止重复任务
        ScheduledFuture<?> future = scheduler.scheduleAtFixedRate(task, initialDelay, period, unit);
        tasks.put(taskId, future);
    }

    public void removeTask(String taskId) {
        ScheduledFuture<?> future = tasks.remove(taskId);
        if (future != null) {
            future.cancel(false);
        }
    }

    public boolean hasTask(String taskId) {
        return tasks.containsKey(taskId);
    }
}
