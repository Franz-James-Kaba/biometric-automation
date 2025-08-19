package com.lab;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Duration;

import static TestData.ConfigData.APK_PATH;

public class Setup {

    protected AndroidDriver mobileDriver;
    protected WebDriver webDriver;
    protected WebDriverWait mobileWait;
    protected WebDriverWait webWait;

    AppiumDriverLocalService service;

    // Page object

    @BeforeClass
    public void startAppiumServer() {
        service = AppiumDriverLocalService.buildDefaultService();
        service.start();
    }

    @BeforeMethod
    public void setUpEnvironment() throws MalformedURLException {
        // Initialize Mobile Driver
        UiAutomator2Options options = new UiAutomator2Options();
        options.setDeviceName("Reach Emu");
        options.setApp(APK_PATH);

        try {
            URI uri = new URI("http://192.168.16.1:4723/");
            mobileDriver = new AndroidDriver(uri.toURL(), options);
        } catch (URISyntaxException e) {
            throw new RuntimeException("Invalid URI syntax for Appium server URL", e);
        }

        mobileDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(7));
        mobileWait = new WebDriverWait(mobileDriver, Duration.ofSeconds(10));


    }

    @AfterTest
    public void tearDown() {
        if (mobileDriver != null) {
            mobileDriver.quit();
        }

        if (webDriver != null) {
            webDriver.quit();
        }

        if (service != null) {
            service.stop();
        }
    }
}

