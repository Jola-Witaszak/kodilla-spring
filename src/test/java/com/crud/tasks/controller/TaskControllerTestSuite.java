package com.crud.tasks.controller;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import com.crud.tasks.mapper.TaskMapper;
import com.crud.tasks.service.DbService;
import com.google.gson.Gson;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@SpringJUnitWebConfig
@WebMvcTest(TaskController.class)
class TaskControllerTestSuite {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DbService dbService;

    @MockBean
    private TaskMapper taskMapper;

    @Test
    void shouldGetAllTasks() throws Exception {
        //Given
        List<Task> tasksList = List.of(new Task(1L, "Shopping list", "water, milk, iceCream"));
        List<TaskDto> taskDtoList = List.of(new TaskDto(1L, "Shopping list", "water, milk, iceCream"));
        when(dbService.getAllTasks()).thenReturn(tasksList);
        when(taskMapper.mapToTaskDtoList(tasksList)).thenReturn(taskDtoList);
        //When & Then
        mockMvc.perform(MockMvcRequestBuilders
                    .get("/v1/task/getTasks")
                    .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1)));
    }

    @Test
    void testGetTaskById() throws Exception {
        //Given
        Task task = new Task(2L, "shopping", "iceCream");
        TaskDto taskDto = new TaskDto(2L, "shopping", "iceCream");
        when(taskMapper.mapToTaskDto(task)).thenReturn(taskDto);
        when(taskMapper.mapToTask(taskDto)).thenReturn(task);
        when(dbService.getTask(2L)).thenReturn(task);
        //When & Then
        mockMvc.perform(MockMvcRequestBuilders
                    .get("/v1/task/getTask")
                    .contentType(MediaType.APPLICATION_JSON)
                    .param("taskId", "2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("shopping"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", Matchers.is("iceCream")))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void shouldDeleteTask() throws Exception {
        //Given
        doNothing().when(dbService).deleteTask(isA(Long.class));
        //When & Then
        mockMvc.perform(MockMvcRequestBuilders
                    .delete("/v1/task/deleteTask")
                    .param("taskId", "1"))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Test
    void testCreateTask() throws Exception {
        //Given
        Task task = new Task(null, "test_title", "test_content");
        Task createdTask = new Task(3L,"test_title", "test_content");
        TaskDto taskDto = new TaskDto(null, "test_title", "test_content");
        when(taskMapper.mapToTask(taskDto)).thenReturn(task);
        when(dbService.saveTask(task)).thenReturn(createdTask);
        when(taskMapper.mapToTaskDto(task)).thenReturn(taskDto);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(taskDto);
        //When $ Then
        mockMvc.perform(MockMvcRequestBuilders
                    .post("/v1/task/createTask")
                    .contentType(MediaType.APPLICATION_JSON)
                    .characterEncoding("UTF-8")
                    .content(jsonContent))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(3))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("test_title"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").value("test_content"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}