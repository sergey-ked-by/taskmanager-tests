package com.example.taskmanager.ui.pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

public class TaskFormPage {

    private final SelenideElement titleInput = $("#title");
    private final SelenideElement descriptionInput = $("#description");
    private final SelenideElement saveButton = $(byText("Save"));

    @Step("Create a new task with title '{title}'")
    public DashboardPage createTask(String title, String description) {
        titleInput.setValue(title);
        descriptionInput.setValue(description);
        saveButton.click();

        $(".alert-success").shouldHave(text("Task created successfully!"));
        return new DashboardPage();
    }

    @Step("Edit the task, setting a new title '{newTitle}'")
    public TaskViewPage editTask(String newTitle, String newDescription) {
        titleInput.setValue(newTitle);
        descriptionInput.setValue(newDescription);
        saveButton.click();
        return new TaskViewPage();
    }
}