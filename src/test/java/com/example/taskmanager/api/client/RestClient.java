package com.example.taskmanager.api.client;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.List;

import static io.restassured.RestAssured.given;

// Extensible client: methods, filters, configs can be added
public class RestClient {

    private final RequestSpecification baseSpec;

    public RestClient() {
        RestAssured.baseURI = System.getProperty("api.base.uri"); // Base URI for API tests, provided via system property
        baseSpec = new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .addFilter(new AllureRestAssured())
                .build();
    }

    // Basic GET (extensible: add overloads)
    public <T> T get(String path, String token, Class<T> responseType) {
        return buildSpec().auth().oauth2(token).get(path).then().extract().as(responseType);
    }

    public <T> List<T> getListPaginated(String path, String token, Class<T> responseType) {
        return buildSpec().auth().oauth2(token).get(path).then().extract().jsonPath().getList("content", responseType);
    }

    public <T> List<T> getList(String path, String token, Class<T> responseType) {
        return buildSpec().auth().oauth2(token).get(path).then().extract().jsonPath().getList("", responseType);
    }

    public Response get(String path) {
        return buildSpec().get(path);
    }

    // Basic POST
    public <T> T post(String path, Object body, String token, Class<T> responseType) {
        return buildSpec().auth().oauth2(token).body(body).post(path).then().extract().as(responseType);
    }

    public <T> T post(String path, Object body, Class<T> responseType) {
        return buildSpec().body(body).post(path).then().extract().as(responseType);
    }

    // Basic PUT
    public <T> T put(String path, Object body, Class<T> responseType) {
        return buildSpec().body(body).put(path).then().extract().as(responseType);
    }

    public <T> T put(String path, Object body, String token, Class<T> responseType) {
        return buildSpec().auth().oauth2(token).body(body).put(path).then().extract().as(responseType);
    }

    public <T> T put(String path, String token, Class<T> responseType) {
        return buildSpec().auth().oauth2(token).put(path).then().extract().as(responseType);
    }

    // Basic DELETE
    public Response delete(String path, String authToken) {
        return buildSpec().auth().oauth2(authToken).delete(path);
    }

    // Basic PATCH
    public <T> T patch(String path, Object body, String token, Class<T> responseType) {
        return buildSpec().auth().oauth2(token).body(body).patch(path).then().extract().as(responseType);
    }

    public Response patch(String path, String token) {
        return buildSpec().auth().oauth2(token).patch(path);
    }

    // Specification builder (extensible: add custom headers, query params, etc.)
    protected RequestSpecification buildSpec() {
        return given().spec(baseSpec);
    }
}