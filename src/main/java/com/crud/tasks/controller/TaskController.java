package com.crud.tasks.controller;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import com.crud.tasks.mapper.TaskMapper;
import com.crud.tasks.service.DbService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/task")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class TaskController {
    @Autowired
    private final DbService dbService;
    @Autowired
    private final TaskMapper taskMapper;

    @GetMapping(value = "getTasks")
    public List<TaskDto> getTasks() {
        List<Task> tasks = dbService.getAllTasks();
        return taskMapper.mapToTaskDtoList(tasks);
    }

    @GetMapping(value = "getTask")
    public TaskDto getTask(@RequestParam long taskId) {
        return taskMapper.mapToTaskDto(dbService.getTask(taskId));
    }

    @DeleteMapping(value = "deleteTask")
    public void deleteTask(@RequestParam long taskId) throws TaskNotFoundException {
        dbService.deleteTask(taskId);
    }

    @PutMapping(value = "updateTask", consumes = MediaType.APPLICATION_JSON_VALUE)
    public TaskDto updateTask(@RequestBody TaskDto taskDto) {
        Task task = taskMapper.mapToTask(taskDto);
        Task savedTask = dbService.saveTask(task);
        return taskMapper.mapToTaskDto(savedTask);
    }

    @PostMapping(value = "createTask", consumes = MediaType.APPLICATION_JSON_VALUE)
    public TaskDto createTask(@RequestBody TaskDto taskDto) {
        Task createdTask = taskMapper.mapToTask(taskDto);
        dbService.saveTask(createdTask);
        return taskMapper.mapToTaskDto(createdTask);
    }
}
