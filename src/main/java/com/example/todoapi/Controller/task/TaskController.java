package com.example.todoapi.Controller.task;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.example.todoapi.Service.task.TaskService;
import com.example.todoapi.controller.TasksApi;
import com.example.todoapi.model.TaskDTO;
import com.example.todoapi.model.TaskForm;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class TaskController implements TasksApi{

    private final TaskService taskService;


    @Override
    public ResponseEntity<TaskDTO> showTask(Long taskId) {
        var entity = taskService.find(taskId);

        var dto = new TaskDTO();
        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());

        return ResponseEntity.ok(dto);
    }

    @Override
    public ResponseEntity<TaskDTO> createTask(TaskForm form) {
        var entity = taskService.create(form.getTitle());
        var dto = new TaskDTO();
        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());

        return ResponseEntity
                .created(URI.create("/tasks/" + dto.getId()))
                .body(dto);
    }

}
