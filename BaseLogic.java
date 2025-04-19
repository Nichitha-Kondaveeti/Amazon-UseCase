package com.amazon.base;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import com.amazon.utils.ConfigReader;
import com.amazon.utils.LogManager;

public class BaseLogic {
	protected WebDriver driver;
	protected WebDriverWait wait;
	protected Actions actions;
	protected JavascriptExecutor js;
	protected LogManager logger;

	public BaseLogic(WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver,
				Duration.ofSeconds(Long.parseLong(ConfigReader.getProperty("explicit.wait"))));
		this.actions = new Actions(driver);
		this.js = (JavascriptExecutor) driver;
		this.logger = LogManager.getInstance();

		// Initialize elements using PageFactory
		PageFactory.initElements(driver, this);
	}
	
	


	/**
	 * Wait for element to be clickable and click
	 */
	protected void click(WebElement element) {
		wait.until(ExpectedConditions.elementToBeClickable(element));
		element.click();
	}

	/**
	 * JavaScript click for elements 
	 */
	protected void jsClick(WebElement element) {
		wait.until(ExpectedConditions.visibilityOf(element));
		js.executeScript("arguments[0].click();", element);
	}
	
	

	/**
	 * Type text in element
	 */
	protected void type(WebElement element, String text) {
		wait.until(ExpectedConditions.visibilityOf(element));
		element.clear();
		element.sendKeys(text);
	}

	/**
	 * Hover over an element
	 */
	protected void hover(WebElement element) {
		wait.until(ExpectedConditions.visibilityOf(element));
		actions.moveToElement(element).perform();
	}

	/**
	 * Hover over an element and click on another element
	 */
	protected void hoverAndClick(WebElement hoverElement) {
		wait.until(ExpectedConditions.visibilityOf(hoverElement));
		actions.moveToElement(hoverElement).perform();
		wait.until(ExpectedConditions.elementToBeClickable(hoverElement));
		hoverElement.click();
	}

	/**
	 * Get text from element
	 */
	protected String getText(WebElement element) {
		wait.until(ExpectedConditions.visibilityOf(element));
		return element.getText();
	}
	
	protected String getAttribute(WebElement element) {
		wait.until(ExpectedConditions.visibilityOf(element));
		return element.getAttribute("class");
	}
	

	/**
	 * Get text from multiple elements
	 */
	protected List<String> getTextsFromElements(List<WebElement> elements) {
		wait.until(ExpectedConditions.visibilityOfAllElements(elements));
		return elements.stream().map(WebElement::getText).toList();
	}

	/**
	 * Wait for page to load completely
	 */
	protected void waitForPageLoad() {
		wait.until(driver -> js.executeScript("return document.readyState").equals("complete"));
	}

	/**
	 * Scroll to element
	 */
	protected void scrollToElement(WebElement element) {
		js.executeScript("arguments[0].scrollIntoView(true);", element);
	}
	
	
	

	/**
	 * Check if element is displayed
	 */
	protected boolean isElementDisplayed(WebElement element) {
		try {
			return element.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Wait for element to be visible
	 */
	protected void waitForElementVisible(WebElement element) {
		wait.until(ExpectedConditions.visibilityOf(element));
	}

	/**
	 * Press Enter key on element
	 */
	protected void pressEnter(WebElement element) {
		element.sendKeys(Keys.ENTER);
	}
}
