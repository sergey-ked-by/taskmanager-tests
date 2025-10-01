package com.example.taskmanager.ui;

import com.example.taskmanager.ui.pages.DashboardPage;
import com.example.taskmanager.ui.pages.LoginPage;
import com.example.taskmanager.ui.pages.RegisterPage;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.UUID;

@Epic("UI Authentication")
@Feature("Registration and Login")
@Tag("UI")
@DisplayName("UI Tests for Registration/Authorization")
class RegistrationAndLoginTest extends BaseUiTest {

    private final RegisterPage registerPage = new RegisterPage();
    private final LoginPage loginPage = new LoginPage();
    private final DashboardPage dashboardPage = new DashboardPage();

    @Test
    @DisplayName("User can successfully register and log in")
    @Description("Scenario: open registration page -> enter data -> " +
            "be redirected to the login page -> log in -> see the dashboard")
    void shouldRegisterAndLoginSuccessfully() {
        String username = "ui_user_" + UUID.randomUUID().toString().substring(0, 8);
        String email = username + "@test.com";
        String password = "password123";

        registerPage.openPage()
                .registerAs(username, email, password);

        loginPage.loginAs(username, password);

        dashboardPage.verifyDashboardLoaded(username);
    }

    @Test
    @DisplayName("Login with invalid credentials should show an error")
    void shouldShowErrorOnInvalidLogin() {
        loginPage.openPage()
                .loginAs("invalid_user", "wrong_password");

        loginPage.verifyErrorMessage("Invalid username or password");
    }
}