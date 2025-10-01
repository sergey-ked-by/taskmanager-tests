package com.example.taskmanager.ui.pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class LoginPage {

    private final SelenideElement usernameInput = $("#username");
    private final SelenideElement passwordInput = $("#password");
    private final SelenideElement submitButton = $("button[type='submit']");
    private final SelenideElement errorMessage = $(".alert-danger");

    @Step("Open the login page")
    public LoginPage openPage() {
        open("/login");
        return this;
    }

    @Step("Login as user '{username}'")
    public DashboardPage loginAs(String username, String password) {
        usernameInput.setValue(username);
        passwordInput.setValue(password);
        submitButton.click();
        return new DashboardPage();
    }

    @Step("Verify the presence of the error message '{expectedText}'")
    public LoginPage verifyErrorMessage(String expectedText) {
        errorMessage.shouldHave(text(expectedText));
        return this;
    }
}