package com.example.taskmanager.ui.pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class RegisterPage {

    private final SelenideElement usernameInput = $("#username");
    private final SelenideElement emailInput = $("#email");
    private final SelenideElement passwordInput = $("#password");
    private final SelenideElement submitButton = $("button[type='submit']");

    @Step("Open the registration page")
    public RegisterPage openPage() {
        open("/register");
        return this;
    }

    @Step("Register a new user '{username}'")
    public LoginPage registerAs(String username, String email, String password) {
        usernameInput.setValue(username);
        emailInput.setValue(email);
        passwordInput.setValue(password);
        submitButton.click();
        return new LoginPage();
    }
}