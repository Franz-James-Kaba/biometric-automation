package com.lab;

import io.appium.java_client.android.AndroidDriver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class AdbMethods {

    private final AndroidDriver driver;
    private final String deviceId;

    public AdbMethods(AndroidDriver driver) {
        this.driver = driver;
        this.deviceId = getDeviceIdFromDriver();
    }

    public AdbMethods(AndroidDriver driver, String deviceId) {
        this.driver = driver;
        this.deviceId = deviceId;
    }

    /**
     * Get device ID from the AndroidDriver instance
     */
    private String getDeviceIdFromDriver() {
        try {
            // Try to get device ID from driver capabilities
            return (String) driver.getCapabilities().getCapability("deviceName");
        } catch (Exception e) {
            return "emulator-5554"; // default fallback
        }
    }

    /**
     * Execute ADB command and return the output
     */
    public String executeAdbCommand(String command) {
        StringBuilder output = new StringBuilder();
        try {
            Process process = Runtime.getRuntime().exec(command);
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }

            process.waitFor();
        } catch (IOException | InterruptedException e) {
            System.err.println("Failed to execute ADB command: " + e.getMessage());
            Thread.currentThread().interrupt();
        }
        return output.toString();
    }

    /**
     * Execute ADB command with device-specific targeting
     */
    public String executeDeviceAdbCommand(String adbCommand) {
        String command = "adb";
        if (deviceId != null && !deviceId.isEmpty()) {
            command += " -s " + deviceId;
        }
        command += " " + adbCommand;
        return executeAdbCommand(command);
    }

    /**
     * Emulate fingerprint authentication using Appium's mobile command
     */
    public boolean emulateFingerprint(int fingerprintId) {
        try {
            Map<String, Object> args = new HashMap<>();
            args.put("fingerprintId", fingerprintId);

            driver.executeScript("mobile: fingerprint", args);
            System.out.println("Successfully emulated fingerprint with ID: " + fingerprintId);
            return true;
        } catch (Exception e) {
            System.err.println("Failed to emulate fingerprint: " + e.getMessage());
            return false;
        }
    }

    public void sendValidFingerPrint(){
        try {
            driver.execute("adb -e emu fingerprint touch 1");
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * Emulate successful fingerprint authentication (convenience method)
     */
    public boolean emulateSuccessfulFingerprint() {
        return emulateFingerprint(1); // 1 typically represents successful auth
    }

    /**
     * Emulate failed fingerprint authentication (convenience method)
     */
    public boolean emulateFailedFingerprint() {
        return emulateFingerprint(0); // 0 typically represents failed auth
    }

    /**
     * Emulate fingerprint using ADB command (fallback method)
     */
    public boolean emulateFingerprintViaAdb() {
        String result = executeDeviceAdbCommand("shell input keyevent KEYCODE_FINGERPRINT");
        System.out.println("ADB fingerprint command executed");
        return !result.contains("error") && !result.contains("not found");
    }


    /**
     * Input PIN code via ADB
     */
    public boolean inputPinViaAdb(String pin) {
        if (pin == null || !pin.matches("\\d+")) {
            throw new IllegalArgumentException("PIN must contain only digits");
        }

        for (char digit : pin.toCharArray()) {
            String command = "adb shell input keyevent KEYCODE_" + digit;
            executeDeviceAdbCommand(command);

            // Small delay between key presses
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return false;
            }
        }
        return true;
    }

    /**
     * Press Enter key via ADB
     */
    public boolean pressEnterViaAdb() {
        String result = executeDeviceAdbCommand("shell input keyevent KEYCODE_ENTER");
        return !result.contains("error");
    }

    /**
     * Press Back key via ADB
     */
    public boolean pressBackViaAdb() {
        String result = executeDeviceAdbCommand("shell input keyevent KEYCODE_BACK");
        return !result.contains("error");
    }

    /**
     * Get current activity and package name
     */
    public String getCurrentActivity() {
        return executeDeviceAdbCommand("shell dumpsys window | grep -E 'mCurrentFocus|mFocusedApp'");
    }

    /**
     * Check if device is connected and responsive
     */
    public boolean isDeviceConnected() {
        String result = executeDeviceAdbCommand("devices");
        return result.contains(deviceId) && result.contains("device");
    }

    /**
     * Take screenshot via ADB and save to specified path
     */
    public boolean takeScreenshotViaAdb(String filePath) {
        String result = executeDeviceAdbCommand("shell screencap -p > " + filePath);
        return !result.contains("error");
    }

    /**
     * Get device properties
     */
    public String getDeviceProperty(String property) {
        return executeDeviceAdbCommand("shell getprop " + property);
    }


}