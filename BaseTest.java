package com.amazon.base;

import java.io.File;
import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
//import com.aventstack.extentreports.ExtentReports;

import com.amazon.utils.ConfigReader;
import com.amazon.utils.ExtentReportManager;
import com.amazon.utils.LogManager;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseTest {
	public WebDriver driver;
    protected LogManager logger;

    @BeforeSuite
    public void setupSuite() {
        // Initialize Config Reader
        ConfigReader.initialize();
        
        //Create necessary directories
        createRequiredDirectories();
        // Initialize ExtentReports
        ExtentReportManager.initializeReport();
        
        // Initialize Logger
        logger = LogManager.getInstance();
        logger.info("Test Suite Setup - Initialized ConfigReader, ExtentReports, and Logger");
    }
    
    private void createRequiredDirectories() {
        // Create directories for logs, reports, and screenshots
        String logPath = ConfigReader.getProperty("log.path");
        String reportsPath = ConfigReader.getProperty("reports.path");
        String screenshotPath = ConfigReader.getProperty("screenshot.path");
        
        createDirectoryIfNotExists(logPath);
        createDirectoryIfNotExists(reportsPath);
        createDirectoryIfNotExists(screenshotPath);
    }
    
    private void createDirectoryIfNotExists(String path) {
        File directory = new File(path);
        if (!directory.exists()) {
            boolean created = directory.mkdirs();
            if (created) {
                System.out.println("Created directory: " + path);
            } else {
                System.out.println("Failed to create directory: " + path);
            }
        }
    }

    @Parameters({"browser"})
    @BeforeClass
    public void setupClass(@Optional("chrome") String browser) {
        // If browser parameter is not provided from testng.xml, use the one from config.properties
        if (browser == null || browser.isEmpty()) {
            browser = ConfigReader.getProperty("browser");
        }
        
        // Set up WebDriver based on browser
        setupDriver(browser);
        
        // Configure browser window and timeouts
        driver.manage().window().maximize();
        String implicitWait = ConfigReader.getProperty("implicit.wait");
        if (implicitWait == null || implicitWait.isEmpty()) {
            implicitWait = "10"; // Default value
        }
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(Long.parseLong(implicitWait)));
        
        // Get page load timeout with default value if not found
        String pageLoadTimeout = ConfigReader.getProperty("page.load.timeout");
        if (pageLoadTimeout == null || pageLoadTimeout.isEmpty()) {
            pageLoadTimeout = "30"; // Default value
        }
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(Long.parseLong(pageLoadTimeout)));
        
        logger.info("WebDriver initialized for browser: " + browser);
    }

    @BeforeMethod
    public void setupMethod() {
        // Navigate to base URL
        driver.get(ConfigReader.getProperty("url"));
        logger.info("Navigated to base URL: " + ConfigReader.getProperty("url"));
    }

    @AfterMethod
    public void teardownMethod() {
        // Add any post-test cleanup if needed
        logger.info("Test Method Completed");
    }

    @AfterClass
    public void teardownClass() {
        // Close the browser
        if (driver != null) {
            driver.quit();
            logger.info("WebDriver closed");
        }
    }

    @AfterSuite
    public void teardownSuite() {
        // Flush and close ExtentReports
        ExtentReportManager.flushReport();
        logger.info("Test Suite Completed - ExtentReports finalized");
    }

    private void setupDriver(String browser) {
        switch (browser.toLowerCase()) {
            case "chrome":
                WebDriverManager.chromedriver().setup();
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("--disable-notifications");
                driver = new ChromeDriver(chromeOptions);
                break;
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                firefoxOptions.addPreference("dom.webnotifications.enabled", false);
                driver = new FirefoxDriver(firefoxOptions);
                break;
            case "edge":
                WebDriverManager.edgedriver().setup();
                EdgeOptions edgeOptions = new EdgeOptions();
                edgeOptions.addArguments("--disable-notifications");
                driver = new EdgeDriver(edgeOptions);
                break;
            default:
                throw new IllegalArgumentException("Unsupported browser: " + browser);
        }
    }
}

	

