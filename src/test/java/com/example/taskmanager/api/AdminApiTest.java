package com.example.taskmanager.api;

import com.example.taskmanager.api.extension.GeneratedAdmin;
import com.example.taskmanager.api.extension.InjectToken;
import com.example.taskmanager.dto.UserDto;
import com.example.taskmanager.dto.UserRegistrationDto;
import com.example.taskmanager.dto.StatisticsDto;
import com.example.taskmanager.model.Role;
import io.qameta.allure.*;
import org.junit.jupiter.api.*;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@Epic("Admin API")
@Feature("Admin REST API")
@Tag("Api")
@DisplayName("API Tests for Admin")
class AdminApiTest extends BaseApiTest {

    @Test
    @Story("Get All Users")
    @Description("Get all users via API")
    @GeneratedAdmin
    void getAllUsers(@InjectToken String adminJwt) {
        List<UserDto> users = adminApiService.getAllUsers(adminJwt);
        assertThat(users).isNotEmpty();
    }

    @Test
    @Disabled
    @Story("Update User Role")
    @Description("Update user role via API")
    @GeneratedAdmin
    void updateUserRole(@InjectToken String adminJwt) {
        // Find a non-admin user to update
        UserDto userToUpdate = adminApiService.getAllUsers(adminJwt).stream()
                .filter(u -> u.getRole() == Role.USER)
                .findFirst()
                .orElseThrow(() -> new AssertionError("No user with USER role found to update"));

        // Update the role
        adminApiService.updateUserRole(userToUpdate.getId(), "ADMIN", adminJwt);

        // Re-fetch the user to verify the change was persisted
        UserDto updatedUser = adminApiService.getAllUsers(adminJwt).stream()
                .filter(u -> u.getId().equals(userToUpdate.getId()))
                .findFirst()
                .orElseThrow(() -> new AssertionError("Updated user not found"));

        assertThat(updatedUser.getRole()).isEqualTo(Role.ADMIN);
    }

    @Test
    @Story("Delete User")
    @Description("Delete user via API")
    @GeneratedAdmin
    void deleteUser(@InjectToken String adminJwt) {
        UserRegistrationDto reg = getRandomUser();

        UserDto user = authApiService.register(reg);
        adminApiService.deleteUser(user.getId(), adminJwt);

        List<UserDto> users = adminApiService.getAllUsers(adminJwt);
        assertThat(users.stream().noneMatch(u -> u.getUsername().equals(reg.getUsername()))).isTrue();
    }

    @Test
    @Story("Get Statistics")
    @Description("Get system statistics via API")
    @GeneratedAdmin
    void getStatistics(@InjectToken String adminJwt) {
        StatisticsDto stats = adminApiService.getStatistics(adminJwt);
        assertThat(stats.getTotalUsers()).isGreaterThanOrEqualTo(1);
        assertThat(stats.getTotalTasks()).isGreaterThanOrEqualTo(0);
    }

    private UserRegistrationDto getRandomUser(){
        String username = "testuser_" + UUID.randomUUID().toString().substring(0, 8);
        String userPassword = "pass1234";
        String userEmail = username + "@ma.uii";

        return new UserRegistrationDto(username, userPassword, userEmail);
    }
} 