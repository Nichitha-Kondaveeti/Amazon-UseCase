package com.amazon.utils;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ScreenshotManager {
    private static final LogManager logger = LogManager.getInstance();

    private ScreenshotManager() {
        // Private constructor to prevent instantiation
    }

    /**
     * Take screenshot of entire page
     */
    public static String takeScreenshot(WebDriver driver, String screenshotName) {
        // If screenshotName is null, use a default name
        if (screenshotName == null || screenshotName.isEmpty()) {
            screenshotName = "screenshot";
        }
        
        String screenshotDirectory = ConfigReader.getProperty("screenshot.path");
        
        // Ensure directory exists
        File directory = new File(screenshotDirectory);
        if (!directory.exists()) {
            directory.mkdirs();
        }
        
        String destination = screenshotDirectory + 
                             screenshotName + "_" + getTimestamp() + ".png";
        
        try {
            TakesScreenshot ts = (TakesScreenshot) driver;
            File source = ts.getScreenshotAs(OutputType.FILE);
            File destFile = new File(destination);
            FileUtils.copyFile(source, destFile);
            logger.info("Screenshot taken: " + destination);
            return destination;
        } catch (IOException e) {
            logger.error("Failed to take screenshot: " + e.getMessage());
            return null;
        }
    }

    

    /**
     * Get timestamp for unique screenshot names
     */
    private static String getTimestamp() {
        return new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
    }
}
