package com.example.taskmanager.ui.pages;

import com.codeborne.selenide.*;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$$;

public class AdminUsersPage {

    private final ElementsCollection userRows = $$("table tbody tr");

    @Step("Find the user row '{username}' and change their role to '{newRole}'")
    public AdminUsersPage changeUserRole(String username, String newRole) {
        findRowByUsername(username).$("select[name='role']").selectOption(newRole);
        return this;
    }

    @Step("Find the user row '{username}' and click 'Delete'")
    public AdminUsersPage deleteUser(String username) {
        findRowByUsername(username).$x(".//following::button[1]").click(ClickOptions.usingJavaScript());
        Selenide.switchTo().alert().accept();
        return this;
    }

    @Step("Verify that user '{username}' now has the role '{expectedRole}'")
    public AdminUsersPage verifyUserRole(String username, String expectedRole) {
        findRowByUsername(username).$("select[name='role']").shouldHave(Condition.value(expectedRole));
        return this;
    }

    @Step("Verify that user '{username}' is not in the list")
    public AdminUsersPage verifyUserIsNotExist(String username) {
        userRows.findBy(Condition.text(username)).shouldNot(Condition.exist);
        return this;
    }

    private SelenideElement findRowByUsername(String username) {
        return userRows.findBy(Condition.text(username));
    }
}