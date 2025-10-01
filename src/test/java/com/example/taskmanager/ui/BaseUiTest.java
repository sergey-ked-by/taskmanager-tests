package com.example.taskmanager.ui;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import java.util.Map;

public abstract class BaseUiTest {

    @AfterEach
    public void tearDown(){
        Selenide.closeWebDriver();
    }


    @BeforeAll
    static void setupUi() {
        Configuration.baseUrl = System.getProperty("selenide.base.url"); // Base URL for UI tests, provided via system property
        Configuration.reportsFolder = "build/reports/tests/selenide";
        Configuration.pageLoadStrategy = "eager";

        if (System.getProperty("selenide.remote") != null) {
            Configuration.remote = System.getProperty("selenide.remote");

            Configuration.browserCapabilities.setCapability("selenoid:options", Map.of(
                    "enableVNC", true
                  //  "enableVideo", true
            ));
        } else {
             Configuration.headless = Boolean.parseBoolean(System.getProperty("selenide.headless", "true"));
        }

        SelenideLogger.addListener("AllureSelenide", new AllureSelenide()
                .screenshots(true)
                .savePageSource(false));
    }
}