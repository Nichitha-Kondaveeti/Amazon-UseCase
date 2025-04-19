package com.amazon.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.JavascriptExecutor;

public class ConfigReader {
    private static Properties properties;
    private static final String CONFIG_FILE_PATH = "src/main/resources/config.properties";
    

    private ConfigReader() {
    	
        // Private constructor to prevent instantiation
    }

    public static void initialize() {
        properties = new Properties();
        try (FileInputStream fis = new FileInputStream(CONFIG_FILE_PATH)) {
            properties.load(fis);
        } catch (IOException e) {
            LogManager.getInstance().error("Failed to load config.properties: " + e.getMessage());
            throw new RuntimeException("Failed to load config.properties", e);
        }
    }

    public static String getProperty(String key) {
        if (properties == null) {
            initialize();
        }
        
        String value = properties.getProperty(key);
        if (value == null) {
            LogManager.getInstance().warn("Property not found: " + key);
        }
        return value;
    }
}
