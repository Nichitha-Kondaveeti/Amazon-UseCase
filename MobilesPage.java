package com.amazon.elementspage;

import java.util.List;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import java.lang.Iterable;

import com.amazon.base.BaseLogic;



public class MobilesPage extends BaseLogic {
	@FindBy(xpath = "//span[@class='nav-a-content']")
    private List<WebElement> menuItems;
  

  @FindBy (xpath = "(//span[@class='nav-a-content'])[3]")
  private static WebElement laptopsAndAccessories;
  
  @FindBy(linkText = "Dell")
  private static WebElement dell;
  
  public MobilesPage(WebDriver driver) {
      super(driver);
  }
  
  
  public void hoverOverLaptopsAndAccessories() {
	     logger.info("Hover Over on LaptosAnd accessories");
	     hover(laptopsAndAccessories);
	    
	 }
	 
  
  public void clickOnDell() {
	     logger.info("Click on Dell");
	     click(dell);
	     
	 }
  
  public void clickOnLaptopsAndAccessories() {
	     logger.info("Click on LaptosAnd accessories");
	     click(laptopsAndAccessories);
	     
	 }
  
  public void menuItemsList() {
	  for (WebElement item : menuItems) {
          System.out.println(item.getText());
      }
      System.out.println("Total number of menu items displayed is: " + menuItems.size());
  }
  
  
 
}
