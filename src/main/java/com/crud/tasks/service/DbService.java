package com.crud.tasks.service;

import com.crud.tasks.controller.TaskNotFoundException;
import com.crud.tasks.domain.Task;
import com.crud.tasks.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DbService {
    @Autowired
    private final TaskRepository repository;

    public List<Task> getAllTasks() {
        return repository.findAll();
    }

    public Task getTask(final long id) throws TaskNotFoundException {
        if (repository.existsById(id)) {
            return repository.findById(id);
        } else {
            throw new TaskNotFoundException("Task with id " + id + " not exists");
        }
    }

    public Task saveTask(final Task task) {
         return repository.save(task);
    }

    public void deleteTask(Long id) throws TaskNotFoundException {
        if (repository.existsById(id)) {
            repository.deleteById(id);
        } else {
            throw new TaskNotFoundException("Task with id " + id + " not exists");
        }
    }
}
