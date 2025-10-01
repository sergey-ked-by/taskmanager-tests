package com.example.taskmanager.api.client;

import com.example.taskmanager.dto.TaskDto;
import com.example.taskmanager.dto.TaskCreateDto;
import com.example.taskmanager.dto.TaskUpdateDto;
import io.qameta.allure.Step;
import java.util.List;

public class TaskApiService {
    private final RestClient restClient;

    public TaskApiService() {
        this.restClient = new RestClient();
    }

    @Step("Получить задачи пользователя (JWT: {jwt})")
    public List<TaskDto> getUserTasks(String jwt) {
        return restClient.getList("/api/tasks", jwt, TaskDto.class);
    }

    @Step("Получить все задачи (JWT: {jwt})")
    public List<TaskDto> getAllTasks(String jwt) {
        return restClient.getList("/api/tasks/all", jwt, TaskDto.class);
    }

    @Step("Получить задачу по id {id} (JWT: {jwt})")
    public TaskDto getTask(Long id, String jwt) {
        return restClient.get(String.format("/api/tasks/%d", id), jwt, TaskDto.class);
    }

    @Step("Создать задачу {dto.title} (JWT: {jwt})")
    public TaskDto createTask(TaskCreateDto dto, String jwt) {
        return restClient.post("/api/tasks", dto, jwt, TaskDto.class);
    }

    @Step("Обновить задачу {id} (JWT: {jwt})")
    public TaskDto updateTask(Long id, TaskUpdateDto dto, String jwt) {
        return restClient.put(String.format("/api/tasks/%d", id), dto, jwt, TaskDto.class);
    }

    @Step("Удалить задачу {id} (JWT: {jwt})")
    public void deleteTask(Long id, String jwt) {
        restClient.delete(String.format("/api/tasks/%d", id), jwt);
    }

    @Step("Получить задачи по статусу {status} (JWT: {jwt})")
    public List<TaskDto> getTasksByStatus(String status, String jwt) {
        return restClient.getList(String.format("/api/tasks/status/%s", status), jwt, TaskDto.class);
    }

    @Step("Поиск задач по названию {title} (JWT: {jwt})")
    public List<TaskDto> searchTasks(String title, String jwt) {
        return restClient.getList(String.format("/api/tasks/search?title=%s", title), jwt, TaskDto.class);
    }
} 