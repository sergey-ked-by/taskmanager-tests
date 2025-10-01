package com.example.taskmanager.ui.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class DashboardPage {

    private final SelenideElement header = $("h1");
    private final SelenideElement createTaskButton = $("a[href='/tasks/create']");
    private final SelenideElement adminButton = $("a[href='/admin']");
    private final SelenideElement successAlert = $(".alert-success");

    @Step("Verify that the dashboard is loaded for user '{expectedUsername}'")
    public DashboardPage verifyDashboardLoaded(String expectedUsername) {
        header.shouldHave(Condition.text("Hello, " + expectedUsername));
        createTaskButton.shouldBe(Condition.visible);
        return this;
    }

    @Step("Click the 'Create Task' button")
    public TaskFormPage clickCreateTask() {
        createTaskButton.click();
        return new TaskFormPage();
    }

    @Step("Click the 'Admin' button")
    public AdminDashboardPage clickAdmin() {
        open("/admin/dashboard");
        return new AdminDashboardPage();
    }

    @Step("Find the task with the title '{taskTitle}' and click on it")
    public TaskViewPage clickTaskByTitle(String taskTitle) {
        $(byText(taskTitle)).click();
        return new TaskViewPage();
    }

    @Step("Verify that the task with the title '{taskTitle}' is not in the list")
    public DashboardPage verifyTaskIsNotExist(String taskTitle) {
        $(byText(taskTitle)).shouldNotHave(Condition.exist);
        return this;
    }

    @Step("Verify the presence of the success message '{expectedText}'")
    public DashboardPage verifySuccessMessage(String expectedText) {
        successAlert.shouldHave(Condition.text(expectedText));
        return this;
    }
}