package com.example.todoapi.Service.task;

public class TaskEitityNotFoundException extends RuntimeException {
    
    private long taskId;

    public TaskEitityNotFoundException(long taskId) {
        super("TaskEntity (id = " + taskId + ") is not found.");
        this.taskId = taskId;
    }

    
}
