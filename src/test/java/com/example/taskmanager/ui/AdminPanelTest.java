package com.example.taskmanager.ui;

import com.example.taskmanager.api.client.AuthApiService;
import com.example.taskmanager.dto.UserRegistrationDto;
import com.example.taskmanager.ui.pages.AdminUsersPage;
import com.example.taskmanager.ui.pages.LoginPage;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.*;

import java.util.UUID;

import static com.codeborne.selenide.Selenide.open;

@Epic("UI Admin Panel")
@Feature("User Management")
@Tag("UI")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("UI Tests for Admin Panel")
class AdminPanelTest extends BaseUiTest {

    private final LoginPage loginPage = new LoginPage();
    private final AdminUsersPage adminUsersPage = new AdminUsersPage();
    private AuthApiService authApiService;

    @BeforeAll
    void setupClass() {
        authApiService = new AuthApiService();
    }

    @BeforeEach
    void setupTest() {
        // Assuming a default 'user' user exists in the system
        loginPage.openPage().loginAs("admin", "admin");
        open("/admin/users"); // Navigate directly to the users page
    }


    @Test
    @Disabled
    @DisplayName("Administrator can change user role")
    @Description("Admin finds a user in the list and changes their role from USER to ADMIN.")
    void adminCanChangeUserRole() {
        String usernameToModify = "user"; // Use a known non-admin user
        adminUsersPage.changeUserRole(usernameToModify, "ADMIN");

        adminUsersPage.verifyUserRole(usernameToModify, "ADMIN");
    }

    @Test
    @Disabled
    @DisplayName("Administrator can delete a regular user")
    void adminCanDeleteRegularUser() {
        String usernameToDelete = "user"; // Use a known non-admin user
        adminUsersPage.deleteUser(usernameToDelete);

        adminUsersPage.verifyUserIsNotExist(usernameToDelete);
    }
}