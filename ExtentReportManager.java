package com.amazon.utils;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentReportManager {
    private static ExtentSparkReporter exspre;
    private static ExtentReports reporter;
    private static Map<Long, ExtentTest> testMap = new HashMap<>();
    private static final LogManager logger = LogManager.getInstance();
    
    private ExtentReportManager() {
        // Private constructor to prevent instantiation
    }
    
    public static synchronized void initializeReport() {
        if (reporter == null) {
            // Create report directory if it doesn't exist
            String reportPath = ConfigReader.getProperty("reports.path");
            File directory = new File(reportPath);
            if (!directory.exists()) {
                directory.mkdirs();
            }
            
            // Set up ExtentReports
            String reportFilePath = reportPath + ConfigReader.getProperty("extent.report.name") + ".html";
            exspre = new ExtentSparkReporter(reportFilePath);
            
            // Configure report appearance
            exspre.config().setTheme(Theme.STANDARD);
            exspre.config().setDocumentTitle("Amazon Automation Test Report");
            exspre.config().setReportName("Amazon E2E Test Execution Report");
            
            // Initialize ExtentReports
            reporter = new ExtentReports();
            reporter.attachReporter(exspre);
            
           
            
            logger.info("ExtentReports initialized at: " + reportFilePath);
        }
    }
    
    public static synchronized ExtentTest createTest(String testName, String description) {
        if (reporter == null) {
            initializeReport();
        }
        
        ExtentTest test = reporter.createTest(testName, description);
        testMap.put(Thread.currentThread().getId(), test);
        logger.info("Created test in ExtentReports: " + testName);
        return test;
    }
    
    public static synchronized ExtentTest createTest(String testName) {
        return createTest(testName, "");
    }
    
    public static synchronized ExtentTest getTest() {
        return testMap.get(Thread.currentThread().getId());
    }
    
    public static synchronized void flushReport() {
        if (reporter != null) {
            reporter.flush();
            logger.info("ExtentReports flushed and finalized");
        }
    }
    
    public static synchronized void addScreenshot(String screenshotPath) {
        try {
            ExtentTest test = getTest();
            if (test != null && screenshotPath != null) {
                test.addScreenCaptureFromPath(screenshotPath);
                logger.info("Screenshot added to ExtentReports: " + screenshotPath);
            }
        } catch (Exception e) {
            logger.error("Failed to add screenshot to report: " + e.getMessage());
        }
    }
}