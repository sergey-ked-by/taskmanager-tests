package com.example.taskmanager.api.client;
import com.example.taskmanager.dto.AuthResponse;
import com.example.taskmanager.dto.AuthRequest;
import com.example.taskmanager.dto.UserDto;
import com.example.taskmanager.dto.UserRegistrationDto;
import io.qameta.allure.Step;

public class AuthApiService {
    private final RestClient restClient;

    public AuthApiService() {
        this.restClient = new RestClient();
    }

    @Step("Аутентификация пользователя {login}")
    public AuthResponse auth(String login, String password) {
        AuthRequest authRequest = new AuthRequest(login,password);
        return restClient.post("/api/auth/login", authRequest, AuthResponse.class);
    }

    @Step("Регистрация пользователя {request.username}")
    public UserDto register(UserRegistrationDto request) {
        return restClient.post("/api/auth/register", request, UserDto.class);
    }
}
