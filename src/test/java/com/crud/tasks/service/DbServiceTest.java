package com.crud.tasks.service;

import com.crud.tasks.controller.TaskNotFoundException;
import com.crud.tasks.domain.Task;
import com.crud.tasks.repository.TaskRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class DbServiceTest {
    @Autowired
    private DbService dbService;

    @Autowired
    private TaskRepository taskRepository;

    @Test
    void shouldSaveTask() {
        //Given
        Task task = new Task(null,"testName", "testDescription");
        //When
        dbService.saveTask(task);
        long id = task.getId();
        //Then
        assertNotEquals(0, id);
        assertEquals("testName", task.getTitle());
        assertNotNull(task.getId());
    }

    @Test
    void shouldRemoveTaskById() {
        //Given
        Task task = new Task(null, "Test_title", "testContent");
        dbService.saveTask(task);
        long taskId = task.getId();
        //When & Then
        assertDoesNotThrow(() -> dbService.deleteTask(taskId));
        //CleanUp
    }

    @Test
    void shouldThrowExceptionWhenTaskIdNotExistsInDatabase() throws TaskNotFoundException {
        //Given
        Task task = new Task(null, "Test_title", "testContent");
        dbService.saveTask(task);
        long taskId = task.getId();
        dbService.deleteTask(taskId);
        // When & Then
        assertThrows(TaskNotFoundException.class, () -> dbService.deleteTask(taskId));
    }

    @Test
    void shouldFetchAllTasks() {
        //Given
        Task task = new Task(null, "Test_title", "testContent");
        Task task1 = new Task(null, "Test_title", "testContent");
        Task task2 = new Task(null, "Test_title", "testContent");
        dbService.saveTask(task);
        dbService.saveTask(task1);
        dbService.saveTask(task2);
        long id = task.getId();
        //When
        List<Task> tasks = dbService.getAllTasks();
        //Then
        assertEquals(tasks.size(), taskRepository.findAll().size());
        assertNotEquals(0, id);
        assertTrue(taskRepository.existsById(task2.getId()));
        assertTrue(taskRepository.findById(task1.getId()).isPresent());
    }

    @Test
    void shouldDownloadOneTask() throws TaskNotFoundException {
        //Given
        Task task = new Task(null, "Test_title", "testContent");
        dbService.saveTask(task);
        long id = task.getId();
        //When
        Task downloadedTask = dbService.getTask(id);
        //Then
        assertEquals("Test_title", downloadedTask.getTitle());
        assertEquals("testContent", downloadedTask.getContent());
    }
}
