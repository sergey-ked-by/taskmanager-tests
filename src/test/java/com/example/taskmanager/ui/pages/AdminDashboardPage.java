package com.example.taskmanager.ui.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$;

public class AdminDashboardPage {

    private final SelenideElement header = $("h1");
    private final SelenideElement manageUsersLink = $("a[href='/admin/users']");

    @Step("Verify that the admin dashboard is loaded")
    public AdminDashboardPage verifyDashboardLoaded() {
        header.shouldHave(Condition.text("Admin Dashboard"));
        manageUsersLink.shouldBe(Condition.visible);
        return this;
    }

    @Step("Go to the user management page")
    public AdminUsersPage goToUsersManagement() {
        manageUsersLink.click();
        return new AdminUsersPage();
    }
}