package com.example.taskmanager.api.extension;

import com.example.taskmanager.api.client.AuthApiService;
import com.example.taskmanager.dto.UserRegistrationDto;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolver;

import java.util.UUID;

public class ApiTestExtension implements BeforeEachCallback, ParameterResolver {

    private static final AuthApiService authApiService = new AuthApiService();
    private static final ExtensionContext.Namespace NAMESPACE = ExtensionContext.Namespace.create(ApiTestExtension.class);

    @Override
    public void beforeEach(ExtensionContext context) {
        GeneratedUser userAnnotation = context.getElement().map(el -> el.getAnnotation(GeneratedUser.class)).orElse(null);
        GeneratedAdmin adminAnnotation = context.getElement().map(el -> el.getAnnotation(GeneratedAdmin.class)).orElse(null);

        if (userAnnotation != null) {
            // Create a regular user
            String username = "testuser_" + UUID.randomUUID().toString().substring(0, 8);
            String email = username + "@test.com";
            UserRegistrationDto regDto = new UserRegistrationDto(username, email, "password");

            authApiService.register(regDto);
            String token = authApiService.auth(username, "password").getToken();

            // Save the token and username to the test context
            context.getStore(NAMESPACE).put("token", token);
            context.getStore(NAMESPACE).put("username", username);
        }

        if (adminAnnotation != null) {
            String adminToken = authApiService.auth("admin", "admin").getToken();
            context.getStore(NAMESPACE).put("token", adminToken);
            context.getStore(NAMESPACE).put("username", "admin");
        }
    }

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) {
        // This Extension will inject String parameters with the @InjectToken annotation
        return parameterContext.isAnnotated(InjectToken.class) && parameterContext.getParameter().getType().equals(String.class);
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) {
        // Return the saved token from the context
        return extensionContext.getStore(NAMESPACE).get("token", String.class);
    }
}