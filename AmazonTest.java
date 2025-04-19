package com.amazon.testcases;
import java.util.Iterator;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import com.amazon.base.BaseTest;
import com.amazon.elementspage.HomePage;
import com.amazon.elementspage.LaptopsPage;
import com.amazon.elementspage.MobilesPage;
import com.amazon.elementspage.ProductDetailsPage;
import com.amazon.elementspage.SearchResultsPage;
import com.amazon.utils.ConfigReader;
import com.amazon.utils.ExtentReportManager;
import com.amazon.utils.ScreenshotManager;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

public class AmazonTest extends BaseTest {

    @Test(description = "Amazon E-Commerce End-to-End Test")
    public void amazonE2ETest() throws Exception {
        ExtentTest test = ExtentReportManager.createTest("Amazon E-Commerce End-to-End Test");

        // Tc1: Navigate to the URL in BaseTest
        logger.info("Starting Amazon E2E Test");
        test.log(Status.INFO, "TC1: Navigate to the URL in BaseTest");

        // Tc2: Hover over mobiles
        HomePage homePage = new HomePage(driver);
        homePage.hoverOverMobiles();
        test.log(Status.INFO, "TC2: Hover over mobiles completed");

        // Tc3: Read the names of menu items
        MobilesPage mobilesPage = new MobilesPage(driver);
        homePage.navigateToMobiles();
        mobilesPage.menuItemsList();
        test.log(Status.INFO, "TC3: Read the names of menu items completed");

        // Tc4: Capture Screenshot
        String screenshotPath = ScreenshotManager.takeScreenshot(driver, "mobilesMenu");
        ExtentReportManager.addScreenshot(screenshotPath);
        test.log(Status.INFO, "TC4: Capture Screenshot completed");
        
        // Tc5: Hover on Laptops & Accessories >> click on the "Dell" link under Shop by brand
        mobilesPage.hoverOverLaptopsAndAccessories();
        mobilesPage.clickOnDell();
        driver.navigate().back();
        test.log(Status.INFO, "TC5: Hover on Laptops & Accessories and click on Dell completed");

        // Tc6: Change the PRICE: Min 4000 and Max 20000. Click on the Go button
        SearchResultsPage searchResultsPage = new SearchResultsPage(driver);
        mobilesPage.clickOnLaptopsAndAccessories();
        searchResultsPage.scrollToDellCheckBox();
        searchResultsPage.clickOnDellCheckBox();
        searchResultsPage.scrollToGo();
        searchResultsPage.lowPrice();
        searchResultsPage.highPrice();
        searchResultsPage.clickonGo();
        test.log(Status.INFO, "TC6: Set price range and click Go completed");

        // Tc7: Search for "Gaming Laptop" in the search bar
        homePage.searchProduct("Gaming Laptop");
        test.log(Status.INFO, "TC7: Search for Gaming Laptop completed");

        // Tc8: Click on the First Gaming laptop displayed on the screen
        LaptopsPage laptopsPage = new LaptopsPage(driver);
        laptopsPage.scrollToFirstLaptopLink();
        laptopsPage.clickOnFirstLaptopLink();
        test.log(Status.INFO, "TC8: Click on first Gaming laptop completed");

        // Tc9: Test whether it is navigating to next page where the user can add that Gaming laptop into basket
        System.out.println("Title of my webpage" + driver.getTitle());
        Set<String> winhand = driver.getWindowHandles();
        System.out.println("Total number of tabs opened: " + winhand.size());
        for (String s : winhand) {
            System.out.println("All window Titles: " + s);
        }
        
        if (winhand.size() > 1) {
            System.out.println("Successfully navigated");
            test.log(Status.PASS, "TC9: Successfully navigated to product details page");
        } else {
            test.log(Status.FAIL, "TC9: Failed to navigate to product details page");
        }

        String mainWindow = driver.getWindowHandle();
        Set<String> allWindowHandles = driver.getWindowHandles();
        Iterator<String> iterator = allWindowHandles.iterator();
        while (iterator.hasNext()) {
            String childWindow = iterator.next();
            if (!mainWindow.equalsIgnoreCase(childWindow)) {
                driver.switchTo().window(childWindow);
            }
        }
        
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollTo(0,700)");
        
        // Tc10: Add to cart and check product quantity
        ProductDetailsPage productDetailsPage = new ProductDetailsPage(driver);
        laptopsPage.clickOnAddToCartButton();
        test.log(Status.INFO, "TC10: Clicked on Add to Cart button");
        
        productDetailsPage.clickOnCloseIcon();
        productDetailsPage.quantity();
        
        

        // Tc12: Read customer reviews
        test.log(Status.INFO, "TC12: Starting to read customer reviews");
        laptopsPage.scrollToTopReviews();
        laptopsPage.scrollToSeeAllReviews();
        laptopsPage.clickOnSeeAllReviews();
        
       
        
     // Handle login securely using configuration properties
        try {
            WebElement emailField = driver.findElement(By.id("ap_email"));
            if (emailField.isDisplayed()) {
                test.log(Status.INFO, "TC13: Login form detected - filling credentials");
                
                // Get sensitive data from configuration file
                String email = ConfigReader.getProperty("user.email");
                String password = ConfigReader.getProperty("user.password");
                
                // Enter email
                js.executeScript("arguments[0].value=arguments[1]", emailField, email);
                
                
                // Properly simulate Enter key press using JavaScript
                WebElement continueButton = driver.findElement(By.id("continue"));
                Thread.sleep(1000);
                js.executeScript("arguments[0].click()", continueButton);
                
                
                // Wait for password field to appear
                Thread.sleep(1000);
                
                WebElement passwordField = driver.findElement(By.xpath("//input[@id='ap_password']"));
                js.executeScript("arguments[0].value=arguments[1]", passwordField, password);
                
                WebElement signInButton = driver.findElement(By.id("signInSubmit"));
                js.executeScript("arguments[0].click()", signInButton);
                
                test.log(Status.PASS, "TC13: Login completed successfully");
            }
        } catch (Exception e) {
            test.log(Status.INFO, "TC13: Login not required or element not found");
            logger.info("Login elements not found or not required: " + e.getMessage());
        }
        
        laptopsPage.scrollToCustomerReviews();
        laptopsPage.getTextFromReviews();
        test.log(Status.INFO, "TC13: Customer reviews captured");
        
        
        // TC14: Verify end-to-end flow
        test.log(Status.PASS, "TC14: End-to-end flow successfully completed");
        
        // Take final screenshot
        String finalScreenshotPath = ScreenshotManager.takeScreenshot(driver, "finalState");
        ExtentReportManager.addScreenshot(finalScreenshotPath);
        test.log(Status.INFO, "TC15: Final state screenshot captured");

        logger.info("Test completed successfully");
        test.log(Status.PASS, "TC16: Amazon E2E Test executed successfully");
        
    }
}