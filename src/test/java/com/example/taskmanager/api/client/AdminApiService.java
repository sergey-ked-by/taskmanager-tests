package com.example.taskmanager.api.client;

import com.example.taskmanager.dto.UserDto;
import com.example.taskmanager.dto.StatisticsDto;
import io.qameta.allure.Step;
import java.util.List;

public class AdminApiService {
    private final RestClient restClient;

    public AdminApiService() {
        this.restClient = new RestClient();
    }

    @Step("Получить всех пользователей (JWT: {jwt})")
    public List<UserDto> getAllUsers(String jwt) {
        return restClient.getList("/api/admin/users", jwt, UserDto.class);
    }

    @Step("Обновить роль пользователя {id} на {role} (JWT: {jwt})")
    public UserDto updateUserRole(Long id, String role, String jwt) {
        String url = String.format("/api/admin/users/%d/role?role=%s", id, role);
        return restClient.put(url, jwt, UserDto.class);
    }

    @Step("Удалить пользователя {id} (JWT: {jwt})")
    public void deleteUser(Long id, String jwt) {
        String url = String.format("/api/admin/users/%d", id);
        restClient.delete(url, jwt);
    }

    @Step("Получить статистику системы (JWT: {jwt})")
    public StatisticsDto getStatistics(String jwt) {
        return restClient.get("/api/admin/statistics", jwt, StatisticsDto.class);
    }
} 