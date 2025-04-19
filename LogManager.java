package com.amazon.utils;

import org.apache.logging.log4j.Logger;
//import org.apache.logging.log4j.LogManager;

public class LogManager {
	private static LogManager instance;
	private Logger logger;

	private LogManager() {
		System.setProperty("Log4j.ConfigurationFile", "src/main/resources/log4j.xml");
		logger = org.apache.logging.log4j.LogManager.getLogger(LogManager.class);
	}

	public static synchronized LogManager getInstance() {
		if (instance == null) {
			instance = new LogManager();
		}
		return instance;
	}

	public void info(String message) {
		logger.info(message);
	}

	public void warn(String message) {
		logger.warn(message);
	}

	public void error(String message) {
		logger.error(message);
	}

	public void debug(String message) {
		logger.debug(message);
	}

	public void fatal(String message) {
		logger.fatal(message);
	}

	public Logger getLogger(Class<?> clazz) {
		return org.apache.logging.log4j.LogManager.getLogger(clazz);
	}
}
