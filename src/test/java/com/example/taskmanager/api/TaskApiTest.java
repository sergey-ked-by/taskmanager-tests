package com.example.taskmanager.api;

import com.example.taskmanager.api.extension.InjectToken;
import com.example.taskmanager.api.extension.GeneratedUser;
import com.example.taskmanager.dto.*;
import com.example.taskmanager.model.TaskStatus;
import io.qameta.allure.*;
import org.junit.jupiter.api.*;

import static org.assertj.core.api.Assertions.assertThat;

@Epic("Task API")
@Feature("Task REST API")
@Tag("Api")
@DisplayName("API Tests for Tasks")
class TaskApiTest extends BaseApiTest {

    @Test
    @Story("Create Task")
    @Description("Create a new task via API")
    @GeneratedUser
    void createTask(@InjectToken String jwtToken) {
        TaskDto created = createTask(jwtToken, "API Test Task", "Created from API test");

        assertThat(created.getTitle()).isEqualTo("API Test Task");
        assertThat(created.getDescription()).isEqualTo("Created from API test");
        assertThat(created.getId()).isNotNull();
    }

    @Test
    @Story("Get All Tasks")
    @Description("Get all user tasks via API")
    @GeneratedUser
    void getAllTasks(@InjectToken String jwtToken) {
        createTask(jwtToken, "Task for getAll", "desc");
        assertThat(taskApiService.getUserTasks(jwtToken)).isNotEmpty();
    }

    @Test
    @Story("Get Task By Id")
    @Description("Get a task by id via API")
    @GeneratedUser
    void getTaskById(@InjectToken String jwtToken) {
        TaskDto created = createTask(jwtToken, "Task for getById", "desc");
        TaskDto found = taskApiService.getTask(created.getId(), jwtToken);
        assertThat(found.getId()).isEqualTo(created.getId());
        assertThat(found.getTitle()).isEqualTo("Task for getById");
    }

    @Test
    @Story("Update Task")
    @Description("Update a task via API")
    @GeneratedUser
    void updateTask(@InjectToken String jwtToken) {
        TaskDto created = createTask(jwtToken, "Task for update", "desc");

        TaskUpdateDto updateDto = new TaskUpdateDto();
        updateDto.setTitle("Updated title");
        updateDto.setDescription("Updated desc");
        updateDto.setStatus(TaskStatus.COMPLETED);

        TaskDto updated = taskApiService.updateTask(created.getId(), updateDto, jwtToken);
        assertThat(updated.getTitle()).isEqualTo("Updated title");
        assertThat(updated.getStatus()).isEqualTo(TaskStatus.COMPLETED);
    }

    @Test
    @Story("Delete Task")
    @Description("Delete a task via API")
    @GeneratedUser
    void deleteTask(@InjectToken String jwtToken) {
        TaskDto created = createTask(jwtToken, "Task for delete", "desc");
        taskApiService.deleteTask(created.getId(), jwtToken);
        Assertions.assertThrows(Exception.class, () -> taskApiService.getTask(created.getId(), jwtToken));
    }
} 