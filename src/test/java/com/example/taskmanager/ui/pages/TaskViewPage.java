package com.example.taskmanager.ui.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class TaskViewPage {

    private final SelenideElement pageTitle = $("h1");
    private final SelenideElement descriptionText = $x("//div/p[1]"); // Let's assume the description has a 'lead' class
    private final SelenideElement editButton = $("a.btn-primary");
    private final SelenideElement deleteButton = $(byText("Delete"));
    private final SelenideElement successAlert = $(".alert-success");

    @Step("Verify that the task title is: '{expectedTitle}'")
    public TaskViewPage verifyTitle(String expectedTitle) {
        pageTitle.shouldHave(Condition.text(expectedTitle));
        return this;
    }

    @Step("Verify that the task description is: '{expectedDescription}'")
    public TaskViewPage verifyDescription(String expectedDescription) {
        descriptionText.shouldHave(Condition.text(expectedDescription));
        return this;
    }

    @Step("Click the 'Edit' button")
    public TaskFormPage clickEditButton() {
        editButton.click();
        return new TaskFormPage();
    }

    @Step("Click the 'Delete' button")
    public DashboardPage clickDeleteButton() {
        deleteButton.click();
        return new DashboardPage();
    }

    @Step("Verify the presence of the success message '{expectedText}'")
    public TaskViewPage verifySuccessMessage(String expectedText) {
        successAlert.shouldHave(Condition.text(expectedText));
        return this;
    }
}