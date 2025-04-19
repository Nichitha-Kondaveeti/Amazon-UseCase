package com.amazon.elementspage;

import org.openqa.selenium.WebDriver;

import org.openqa.selenium.WebElement;

import org.openqa.selenium.support.FindBy;

import com.amazon.base.BaseLogic;


public class HomePage extends BaseLogic{
 

@FindBy(xpath = "//input[@id='twotabsearchtextbox']")
 private static WebElement searchBox;

 
 @FindBy(linkText = "Mobiles")
 private WebElement mobileOption;
 
 
 
 
 public HomePage(WebDriver driver) {
		super(driver);
		
	}
 
 public void searchProduct(String searchText) {
     logger.info("Searching for product: " + searchText);
     type(searchBox, searchText);
     click(searchBox);
     type(searchBox,searchText);
     pressEnter(searchBox);
     
 }
 
 public void navigateToMobiles() {
     logger.info("Navigating to Mobiles page");
     click(mobileOption);
     
 }
 
 
 public void hoverOverMobiles() {
     logger.info("Hover Over on Mobiles");
     hover(mobileOption);
    
 }
 public boolean isOnHomePage() {
     return driver.getCurrentUrl().contains("amazon.in");
 }
 
 
 
}
