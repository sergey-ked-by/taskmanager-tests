package com.example.taskmanager.api;

import com.example.taskmanager.api.client.AuthApiService;
import com.example.taskmanager.api.client.AdminApiService;
import com.example.taskmanager.api.client.TaskApiService;
import com.example.taskmanager.dto.*;
import io.qameta.allure.Step;


import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;

public abstract class BaseApiTest {

    protected static final AuthApiService authApiService = new AuthApiService();
    protected static final AdminApiService adminApiService = new AdminApiService();
    protected static final TaskApiService taskApiService = new TaskApiService();

    @BeforeAll
    static void setupApi() {
        RestAssured.baseURI = System.getProperty("api.base.uri"); // Base URI for API tests, provided via system property
    }

    @Step("Create a new task '{title}' with description '{description'}")
    protected TaskDto createTask(String token, String title, String description) {
        TaskCreateDto dto = new TaskCreateDto();
        dto.setTitle(title);
        dto.setDescription(description);
        return taskApiService.createTask(dto, token);
    }
} 