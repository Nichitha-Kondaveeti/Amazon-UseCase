package com.amazon.elementspage;

import java.io.FileWriter;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.amazon.base.BaseLogic;

import java.util.logging.Logger;
import junit.framework.Assert;

public class LaptopsPage extends BaseLogic {

	@FindBy(xpath = "(//a[@class='a-link-normal s-line-clamp-4 s-link-style a-text-normal'])[1]")
	private static WebElement firstLaptopLink;

	@FindBy(xpath = "/html/body/div[2]/div/div/div[5]/div[1]/div[4]/div/div[1]/div/div[1]/div/div/div[2]/div/div[2]/div/form/div/div/div[36]/div[1]/span/span/span/input")
	private static WebElement addToCartButton;

	@FindBy(xpath = "//div[@id='reviewsMedley' or @id='customer-reviews-content' or contains(@class, 'review-views')]")
	private static WebElement reviewSection;
	
	@FindBy(partialLinkText = "See more reviews")
	//@FindBy(xpath = "//a[contains(@data-hook, 'see-all-reviews-link-foot') or contains(text(), 'See more reviews')]")
	private static WebElement seeAllReviews;
	
	
	

	@FindBy(xpath = "//div[@id='cm_cr-review_list']")
	private static WebElement customerReviews;

	@FindBy(xpath =
	".//span[@class='a-profile-name']")
	 private static WebElement nameElement;

	 @FindBy(xpath = ".//i[@data-hook='review-star-rating']")
	 private static WebElement ratingElement;

	@FindBy(xpath = "(.//span[@role='heading'])[1]")
	private static WebElement titleElement;

	@FindBy(xpath = "(.//span[@data-hook='review-body'])")
	private static WebElement bodyElement;

	@FindBy(xpath = "(.//span[@data-hook='review-date'])")
	 private static WebElement dateElement;

	public LaptopsPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	public void scrollToFirstLaptopLink() {
		logger.info("Successfully clicked on First laptop link");
		scrollToElement(firstLaptopLink);
	}

	public void clickOnFirstLaptopLink() {
		logger.info("Successfully clicked on First laptop link");
		click(firstLaptopLink);
	}

	public void scrollToAddToCartButton() {
		logger.info("Successfully clicked on AddToCart Button");
		scrollToElement(addToCartButton);
	}

	public void clickOnAddToCartButton() {
		logger.info("Successfully clicked on AddToCart Button");
		click(addToCartButton);
	}

	public void scrollToTopReviews() {
		logger.info("successfully Navigated to TopReviews section");
		scrollToElement(reviewSection);
	}

	public void scrollToSeeAllReviews() {
		logger.info("Successfully clicked on SeeAllReviews");
		scrollToElement(seeAllReviews);
	}

	public void clickOnSeeAllReviews() {
		logger.info("Successfully clicked on SeeAllReviews");
		click(seeAllReviews);
	}

	public void scrollToCustomerReviews() {
		logger.info("successfully Navigated to CustomerReview ");
		scrollToElement(customerReviews);
	}

	public void getTextFromReviews() throws IOException {
        logger.info("Starting to extract review data");
        FileWriter fileWriter = new FileWriter("amazon_india_reviews.txt");
		fileWriter.write("Top reviews from India for HP Gaming Laptop\n\n");

		// Find all review elements (try different selectors as Amazon's structure
		// varies)
		List<WebElement> reviewElements = driver
				.findElements(By.xpath("//div[contains(@data-hook, 'review') or contains(@class, 'review')]"));

		if (reviewElements.isEmpty()) {
			System.out.println("No reviews found with primary selector, trying alternative...");
			reviewElements = driver.findElements(By.cssSelector("[data-hook='review']"));
			
			
		}

		if (reviewElements.isEmpty()) {
			System.out.println("Still no reviews found, trying another alternative...");
			reviewElements = driver.findElements(By.cssSelector(".review"));
		}

		System.out.println("Found " + reviewElements.size() + " reviews");
		
		

		// Extract and save review information
		int count = 0;
		for (WebElement review : reviewElements) {
			String reviewerName = "";
			String rating = "";
			String title = "";
			String body = "";
			String date = "";

			//WebElement nameElement = review.findElement(
					//By.xpath("//span[@class='a-profile-name' or text()='Richa Sharma']"));
			reviewerName = nameElement.getText();

			//WebElement ratingElement = review.findElement(By.xpath(
					//"//i[@data-hook='review-star-rating']"));
			rating = ratingElement.getAttribute("class");
			// Extract the numerical rating (e.g., "a-star-5" -> "5 stars")
			rating = rating.replaceAll(".*a-star-(\\d).*", "$1 stars");

			
			  //WebElement titleElement = review.findElement(
			  //By.xpath("(//span[@role='heading'])[1]")); title =
			  titleElement.getText();
			

			//WebElement bodyElement = review.findElement(
					//By.xpath("//span[@data-hook='review-body']"));
			body = bodyElement.getText();
			//WebElement dateElement = review.findElement(
					//By.xpath("//span[@data-hook='review-date']"));
			date = dateElement.getText();
			
			
			fileWriter.write("Reviewer: " + reviewerName + "\n");
            fileWriter.write("Rating: " + rating + "\n");
            fileWriter.write("Date: " + date + "\n");
            fileWriter.write("Title: " + title + "\n");
            fileWriter.write("Review: " + body + "\n");
            fileWriter.write("\n-------------------------------------------\n\n");
            
            count++;
            System.out.println("Extracted review #" + count);
            
            
            if (count == 0) {
                fileWriter.write("No reviews found for this product. The page structure may have changed or there might be no reviews yet.");
                System.out.println("No reviews were found.");
                
                
            }
            
           
		}
		
		 fileWriter.close();
         System.out.println("Reviews saved to amazon_india_reviews.txt");
         
	
		
	}
}

