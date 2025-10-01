package com.example.taskmanager.ui;

import com.example.taskmanager.ui.pages.DashboardPage;
import com.example.taskmanager.ui.pages.LoginPage;
import com.example.taskmanager.ui.pages.RegisterPage;
import com.example.taskmanager.ui.pages.TaskViewPage;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.UUID;


@Epic("UI Tasks")
@Feature("Task Management (CRUD)")
@Tag("UI")
@DisplayName("UI Tests for Tasks")
class TaskManagementTest extends BaseUiTest {

    private final RegisterPage registerPage = new RegisterPage();
    private final LoginPage loginPage = new LoginPage();
    private final DashboardPage dashboardPage = new DashboardPage();

    @BeforeEach
    void setupUser() {
        String username = "task_user_" + UUID.randomUUID().toString().substring(0, 8);
        String email = username + "@test.com";
        String password = "password123";

        registerPage.openPage().registerAs(username, email, password);
        loginPage.loginAs(username, password)
                .verifyDashboardLoaded(username);
    }

    @Test
    @DisplayName("User can create a new task")
    void shouldCreateNewTask() {
        String taskTitle = "Read a book on Selenide";
        String taskDescription = "A very important book for automation.";

        dashboardPage.clickCreateTask()
                .createTask(taskTitle, taskDescription)
                .verifySuccessMessage(" Task created successfully!")
                .clickTaskByTitle(taskTitle)
                .verifyTitle(taskTitle);
    }

    @Test
    @DisplayName("User can view the details of a created task")
    void shouldViewTaskDetails() {
        String taskTitle = "Go to the store" + UUID.randomUUID().toString().substring(0, 8);
        String taskDescription = "Buy milk, bread and eggs" + UUID.randomUUID().toString().substring(0, 8);
        dashboardPage.clickCreateTask().createTask(taskTitle, taskDescription);

        dashboardPage.clickTaskByTitle(taskTitle)
                .verifyTitle(taskTitle)
                .verifyDescription(taskDescription);
    }

    @Test
    @DisplayName("User can edit a task")
    void shouldEditTask() {
        String originalTitle = "Write a report";
        String updatedTitle = "Write an ANNUAL report";
        String updatedDescription = "The report must be ready by Friday.";
        dashboardPage.clickCreateTask().createTask(originalTitle, "Draft report");

        dashboardPage.clickTaskByTitle(originalTitle)
                .clickEditButton()
                .editTask(updatedTitle, updatedDescription);

        TaskViewPage taskViewPage = new TaskViewPage();
        taskViewPage.verifySuccessMessage(" Task updated successfully!")
                .verifyTitle(updatedTitle)
                .verifyDescription(updatedDescription);
    }

    @Test
    @DisplayName("User can delete a task")
    void shouldDeleteTask() {
        String taskTitleToDelete = "Delete this task";
        dashboardPage.clickCreateTask().createTask(taskTitleToDelete, "Description for deletion");

        dashboardPage.clickTaskByTitle(taskTitleToDelete)
                .clickDeleteButton();

        dashboardPage.verifySuccessMessage(" Task deleted successfully!")
                .verifyTaskIsNotExist(taskTitleToDelete);
    }
}