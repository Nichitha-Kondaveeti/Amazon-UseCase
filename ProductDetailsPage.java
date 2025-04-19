package com.amazon.elementspage;

import java.util.logging.Logger;

import org.openqa.selenium.WebDriver;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.amazon.base.BaseLogic;

import jdk.internal.org.jline.utils.Log;
import junit.framework.Assert;

public class ProductDetailsPage extends BaseLogic{
	
		

	@FindBy(id="quantity")
	 private static WebElement quantityDropdown;
	 
	  
	 @FindBy(css="#attach-close_sideSheet-link")
	 private static WebElement closeIcon;
	 
	 
	 public ProductDetailsPage(WebDriver driver) {
			super(driver);
			// TODO Auto-generated constructor stub
		}
	 public void clickOnCloseIcon() {
	     logger.info("Click on Close Icon");
	     click(closeIcon);
	     
	 }
		
		  public void quantity(){
			  logger.info("Retreive quantity");
		  WebElement quantitydropdown= quantityDropdown;
		  String selectedValue= quantitydropdown.getAttribute("value"); 
		  int quantity =Integer.parseInt(selectedValue);
		  Assert.assertTrue("Product quantity should be greater than or equal to 1",
		  quantity >= 1);
		  
		  System.out.
		  println("Test passed: Successfully navigated to gaming laptop product page and verified quantity"
		  );
		
		  
		  
		  }
		 
	
	 
 
}
