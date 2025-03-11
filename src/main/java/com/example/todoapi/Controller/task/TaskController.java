package com.example.todoapi.Controller.task;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.example.todoapi.Service.task.TaskEntity;
import com.example.todoapi.Service.task.TaskService;
import com.example.todoapi.controller.TasksApi;
import com.example.todoapi.model.PageDTO;
import com.example.todoapi.model.TaskDTO;
import com.example.todoapi.model.TaskForm;
import com.example.todoapi.model.TaskListDTO;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class TaskController implements TasksApi{

    private final TaskService taskService;


    @Override
    public ResponseEntity<TaskDTO> showTask(Long taskId) {
        var entity = taskService.find(taskId);
        var dto = toTaskDTO(entity);
        return ResponseEntity.ok(dto);
    }

    @Override
    public ResponseEntity<TaskDTO> createTask(TaskForm form) {
        var entity = taskService.create(form.getTitle());
        var dto = toTaskDTO(entity);

        return ResponseEntity
                .created(URI.create("/tasks/" + dto.getId()))
                .body(dto);
    }

    @Override
    public ResponseEntity<TaskListDTO> listTasks(Integer limit, Long offset) {
        var entityList = taskService.find(limit, offset);
        var dtoList = entityList.stream()
                .map(this::toTaskDTO)
                .collect(Collectors.toList());
        var pageDTO = new PageDTO();
        pageDTO.setLimit(limit);
        pageDTO.setOffset(offset);
        pageDTO.setSize(dtoList.size());

        var dto = new TaskListDTO();
        dto.setPage(pageDTO);
        dto.setResults(dtoList);
        // System.out.println("pageDTO: " + pageDTO);
        return ResponseEntity.ok(dto);
    }

    private TaskDTO toTaskDTO(TaskEntity taskEntity) {
            var taskDTO = new TaskDTO();
            taskDTO.setId(taskEntity.getId());
            taskDTO.setTitle(taskEntity.getTitle());
            return taskDTO;
    }
}
