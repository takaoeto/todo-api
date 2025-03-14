package com.example.todoapi.Service.task;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.todoapi.Repository.task.TaskRecord;
import com.example.todoapi.Repository.task.TaskRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskEntity find(long taskId) {
        return taskRepository.select(taskId)
            .map(record -> new TaskEntity(record.getId(), record.getTitle()))  //selectで取得したものをTaskEntityに変換する
            .orElseThrow(() -> new TaskEntityNotFoundException(taskId)); //自作エラークラスに投げるている　TaskEntityNotFoundException(taskId)
    }

    public List<TaskEntity> find(Integer limit, Long offset) {
        return taskRepository.selectList(limit, offset)
                .stream()
                .map(record -> new TaskEntity(record.getId(), record.getTitle()))
                .collect(Collectors.toList());
    }

    public TaskEntity create(String title) {
        var record = new TaskRecord(null, title); //オートインクリメントされたidがセットされるため null でよい（repositoryを参照）
        taskRepository.insert(record);
        return new TaskEntity(record.getId(), record.getTitle());
    }

}
