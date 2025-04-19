package com.amazon.elementspage;

import org.openqa.selenium.WebDriver;

import org.openqa.selenium.WebElement;

import org.openqa.selenium.support.FindBy;

import com.amazon.base.BaseLogic;

public class SearchResultsPage extends BaseLogic {

	@FindBy(xpath = "//i[contains(@class, 'a-icon-checkbox')]/ancestor::label[contains(@for, 'apb-browse-refinements-checkbox_11')]")
	private static WebElement dellCheckBox;

	@FindBy(xpath = "//input[@placeholder='Min' or @name='low-price']")
	private static WebElement lowPrice;

	@FindBy(xpath = "//input[@placeholder='Max' or @name='high-price']")
	private static WebElement highPrice;

	@FindBy(xpath = "//input[@class='a-button-input']")
	private static WebElement goButton;

	public SearchResultsPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	public MobilesPage clickOnDellCheckBox() {
		logger.info("Click on DellCheck Box");
		if (dellCheckBox.isSelected() != true)
			jsClick(dellCheckBox);

		return new MobilesPage(driver);
	}

	public MobilesPage lowPrice() {
		logger.info("Set low Price");
		js.executeScript("arguments[0].value = '';", lowPrice);
		System.out.println(js.executeScript(" return arguments[0].value = '4000';", lowPrice));
		return new MobilesPage(driver);
	}

	public MobilesPage highPrice() {
		logger.info("Set high Price");
		js.executeScript("arguments[0].value = '';", highPrice);
		System.out.println(js.executeScript(" return arguments[0].value = '20000';", highPrice));
		return new MobilesPage(driver);
	}

	public MobilesPage clickonGo() {
		logger.info("Clicking on Go Button");
		jsClick(goButton);
		return new MobilesPage(driver);
	}

	public MobilesPage scrollToDellCheckBox() {
		logger.info("Scroll to element dellCheckbox");
		scrollToElement(dellCheckBox);
		return new MobilesPage(driver);
	}

	public MobilesPage scrollToGo() {
		logger.info("Scroll to element Go Button");
		scrollToElement(goButton);
		return new MobilesPage(driver);
	}

}
