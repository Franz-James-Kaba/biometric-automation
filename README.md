# AI-Enhanced Test Automation Suite for BioAuth Login System

[![Appium](https://img.shields.io/badge/Appium-2.0-%2343B02A?logo=appium)](https://appium.io/)
[![Java](https://img.shields.io/badge/Java-11-%23ED8B00?logo=openjdk)](https://openjdk.org/)
[![TestNG](https://img.shields.io/badge/TestNG-Framework-%23FF6A33)](https://testng.org/)
[![Applitools](https://img.shields.io/badge/Applitools-Eyes-%2325D7FD?logo=applitools)](https://applitools.com/)
[![License](https://img.shields.io/badge/License-MIT-green.svg)](LICENSE)

## 📖 Overview

This project implements a sophisticated AI-enhanced test automation suite for a simulated mobile login application that supports both traditional username/password and biometric authentication (Fingerprint & Face ID). The suite is designed to rigorously test the application's functionality, reliability, and user experience under various conditions, leveraging AI to elevate the testing strategy beyond traditional methods.

The System Under Test (SUT) is a demo Android application featuring:
*   **Username/Password Login**
*   **Simulated Fingerprint Authentication**
*   **Simulated Face ID Authentication** (via the same Android biometric prompt)
*   A backend that simulates authentication logic via REST endpoints.

## 🧠 AI Integration & Strategy

Our approach integrates AI across the testing lifecycle to improve efficiency, coverage, and insight.

| AI Application | Tool Used | Implementation Details |
| :--- | :--- | :--- |
| **Test Case Generation** | **ChatGPT (OpenAI API)** | Used a structured prompt to generate a comprehensive set of Gherkin-style scenarios covering success, failure, fallback, lockout, and edge cases. |
| **Test Script Writing** | **ChatGPT & GitHub Copilot** | Accelerated the creation of boilerplate code (Page Object Models, helper functions) and complex Appium interactions. |
| **Visual Anomaly Detection** | **Applitools Eyes** | Integrated into the Appium framework. Each test performs a visual AI checkpoint to detect UI regressions that traditional locators would miss. |
| **Predictive Failure Analysis** | **Custom Heuristic Analysis** | A post-test script analyzes logs and test results, flagging tests that show increasing execution time or flakiness. |

## 🏗️ Framework Architecture
ai-bioauth-test-suite/
│
├── src/test/java/com/bioauth/tests/
│ ├── core/ # Core framework classes
│ ├── pages/ # Page Object Model (POM) Classes
│ ├── ai/ # AI Integration Classes
│ └── testsuites/ # Test classes grouped by functionality
│
├── src/test/resources/ # Config files, APK, API keys
├── test-output/ # Generated reports and logs
├── docker-compose.yml # To spin up Appium server in Docker
└── README.md

**Tech Stack:** TestNG, Appium, Applitools, Maven, Android Emulator

**Technology Stack:**
*   **Test Runner:** TestNG
*   **Mobile Automation:** Appium (UiAutomator2)
*   **Visual AI:** Applitools Eyes SDK
*   **AI Assistants:** ChatGPT, GitHub Copilot
*   **Build Tool:** Maven
*   **Device:** Android Emulator (Google APIs)

## ✅ Test Strategy & Coverage Matrix

**Objective:** To ensure the BioAuth login system is functional, secure, and user-friendly under all expected and edge-case conditions.

| Category | Scenario ID | Scenario Description | Automated |
| :--- | :--- | :--- | :--- |
| **Bio Success** | `BIO-001` | Successful login via Fingerprint | ✅ |
| | `BIO-002` | Successful login via Face ID | ✅ |
| **Bio Failure** | `BIO-003` | Failed login via unrecognized fingerprint | ✅ |
| | `BIO-004` | Failed login via unrecognized face | ✅ |
| **Fallback** | `FALL-001` | Fallback to password after bio failure | ✅ |
| | `FALL-002` | Choose password login directly | ✅ |
| **Lockout** | `LOCK-001` | Account lockout after 3 failed bio attempts | ✅ |
| | `LOCK-002` | Lockout message is displayed correctly | ✅ |
| **Permissions** | `PERM-001` | Login flow if biometric permission is denied | ✅ |
| **Network** | `NET-001` | Network drop during bio auth challenge | ✅ |
| | `NET-002` | Slow network during auth | ✅ |

## 🚀 Getting Started

### Prerequisites

1.  **Java JDK 11+**
2.  **Android SDK** with `adb` in your system PATH.
3.  **Appium Server 2.0+**
4.  **Maven**

### Installation

1.  Clone the repo:
    ```bash
    git clone https://github.com/Franz-James-Kaba/biometric-automation.git
    cd ai-bioauth-test-suite
    ```

2.  **Configure Android Emulator:**
    *   Create an AVD (Android Virtual Device) with a **Fingerprint sensor**.
    *   Start the emulator.
    *   Enroll a fingerprint by running this command 10-15 times (with the emulator Settings > Security > Fingerprint menu open):
        ```bash
        adb -e emu finger touch 1
        ```

3.  **Configure Applitools (Optional for Visual AI):**
    *   Create a free account at [applitools.com](https://applitools.com/).
    *   Find your API key in the dashboard.
    *   Create a file `src/test/resources/applitools-api-key.txt` and paste your API key into it.

### Running the Tests

**Option 1: Using Docker (Recommended)**
1.  Start the Appium server:
    ```bash
    docker-compose up -d appium
    ```
2.  Run the tests:
    ```bash
    mvn clean test
    ```

**Option 2: Using a Local Appium Server**
1.  Ensure Appium is installed (`npm install -g appium`) and running on `http://127.0.0.1:4723`.
2.  Run `mvn clean test`.

### Viewing Results
*   **Test Reports:** HTML reports are generated in the `test-output/` directory. Open `emailable-report.html` or `index.html`.
*   **Visual AI Dashboard:** If using Applitools, test results with screenshots and comparisons are available on your personal Applitools dashboard.

## 🧪 Example Test Scenario

**`LOCK-001` - Account Lockout after Multiple Failed Attempts**
1.  App taps "Login with Fingerprint".
2.  **AI Action:** Appium sends 3 `adb emu finger touch 999` commands.
3.  **Verify:** The UI displays a lockout message.
4.  **Visual AI:** Applitools verifies the lockout screen.

## 📋 Future Enhancements

*   **Self-Healing Tests:** Integrate a tool like Healenium.
*   **AI-Driven Flakiness Detection:** Use ML models to analyze historical test run data.
*   **Accessibility Testing:** Integrate AI-powered accessibility checks.

## 👥 Contributors
- Abdul Rauf Mustapha (https://github.com/arMustapha5)
- Benedicta Asare (https://github.com/Benedicta-Asare)
- Emily Quarshie (https://github.com/ladyemilyy)
- Etornam Koko (https://github.com/K0K0-cod3s)
- Franz-James Wefagi Kaba (https://github.com/Franz-James-Kaba)


## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

---

**Built with ❤️ by the FREE B Team.**
