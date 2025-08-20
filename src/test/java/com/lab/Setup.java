package com.lab;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Duration;

import static TestData.ConfigData.APK_PATH;

public class Setup {

    protected AndroidDriver mobileDriver;

    protected WebDriverWait mobileWait;

    AppiumDriverLocalService service;

    @BeforeClass
    public void startAppiumServer() {
        service = AppiumDriverLocalService.buildDefaultService();
        service.start();
    }

    @BeforeMethod
    public void setUpEnvironment() throws MalformedURLException, URISyntaxException {
        // Initialize Mobile Driver with capabilities for fingerprint testing
        UiAutomator2Options options = new UiAutomator2Options();
        options.setDeviceName("Reach_Emu"); // Use the AVD ID, not display name
        options.setPlatformVersion("9.0"); // Explicitly set from your emulator config
        options.setApp(APK_PATH);
        options.setAppPackage("ai.fingerprint.lock.app.lock");
        options.setAppActivity("com.example.newdemoactivity.ui.overlay.activity.SettingOverLay");
        options.setAvd("Reach_Emu"); // Launch the specific emulator
        options.setAvdLaunchTimeout(Duration.ofSeconds(120)); // Longer timeout for emulator boot
        options.setAvdReadyTimeout(Duration.ofSeconds(120));
        options.setAutoGrantPermissions(true); // Automatically grant app permissions
        options.setIsHeadless(false); // Set to true if you don't need to see the UI

        // Use the local service URL instead of hardcoded IP
        mobileDriver = new AndroidDriver(service.getUrl(), options);

        mobileDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(7));
        mobileWait = new WebDriverWait(mobileDriver, Duration.ofSeconds(10));
    }

    @AfterMethod
    public void cleanUpTest() {
        // Reset app state after each test if needed
        if (mobileDriver != null) {
            mobileDriver.terminateApp("ai.fingerprint.lock.app.lock");
        }
    }

    @AfterClass
    public void tearDown() {
        if (mobileDriver != null) {
            mobileDriver.quit();
        }

        if (webDriver != null) {
            webDriver.quit();
        }

        if (service != null && service.isRunning()) {
            service.stop();
        }
    }
}