package com.calltaxi.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aventstack.extentreports.Status;
import com.calltaxi.hooks.Hooks;
import com.calltaxi.utils.Reports;
import com.aventstack.extentreports.ExtentTest;

import java.time.Duration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BookingPage {

	WebDriver driver;
	private WebDriverWait wait;
	ExtentTest test;
	private static final Logger logger = LogManager.getLogger(BookingPage.class);

	// Page Object Locators (POM)
	By fullName = By.id("fullname");
	By phoneNumber = By.id("phonenumber");
	By emailField = By.id("email");
	By tripLong = By.id("long");
	By tripLocal = By.id("local");
	By cabSelect = By.id("cabselect");
	By cabType = By.id("cabType");
	By pickupDate = By.id("pickupdate");
	By pickupTime = By.id("pickuptime");
	By passengerCount = By.id("passenger");
	By tripTypeOneway = By.id("oneway");
	By tripTypeRoundtrip = By.id("roundtrip");
	By bookNowButton = By.id("submitted");

	// Error & Confirmation Elements
	By emailError = By.id("confirm");
	By nameError = By.id("invalidname");
	By phoneError = By.id("invalidphno");
	By emailMissingError = By.id("invalidemail");
	By tripError = By.id("invalidtrip");
	By cabError = By.id("invalidcab");
	By passengerError = By.id("invalidcount");
	By confirmationMessage = By.id("confirm");

	public BookingPage(WebDriver driver, ExtentTest test) {
	    this.driver = driver;
	    this.test = test;
	    this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	}

	public BookingPage(WebDriver driver) {
	    this.driver = driver;
	    this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	}

	private String normalizeOption(String input) {
	    if (input == null) return "";
	    return input.trim();
	}

	public void enterFullName(String name) {
	    WebElement el = driver.findElement(fullName);
	    el.clear();
	    if (name != null && !name.trim().isEmpty()) {
	        el.sendKeys(name);
	        logger.info("Entered Full Name: {}", name);
	    } else {
	        logger.warn("Full Name is empty or null");
	    }
	}

	public void enterPhone(String phone) {
	    WebElement el = driver.findElement(phoneNumber);
	    el.clear();
	    if (phone != null && !phone.trim().isEmpty()) {
	        el.sendKeys(phone);
	        logger.info("Entered Phone Number: {}", phone);
	    } else {
	        logger.warn("Phone Number is empty or null");
	    }
	}

	public void enterEmail(String email) {
	    WebElement el = driver.findElement(emailField);
	    el.clear();
	    if (email != null && !email.trim().isEmpty()) {
	        el.sendKeys(email);
	        logger.info("Entered Email: {}", email);
	    } else {
	        logger.warn("Email is empty or null");
	    }
	}

	public void selectTrip(String type) {
	    if (type == null) return;
	    if (type.equalsIgnoreCase("long")) {
	        driver.findElement(tripLong).click();
	        logger.info("Selected Trip: Long");
	    } else if (type.equalsIgnoreCase("local")) {
	        driver.findElement(tripLocal).click();
	        logger.info("Selected Trip: Local");
	    }
	}

	public void selectCab(String cab) {
	    if (cab != null && !cab.trim().isEmpty()) {
	        String formatted = normalizeOption(cab).substring(0, 1) + normalizeOption(cab).substring(1).toLowerCase();
	        new Select(driver.findElement(cabSelect)).selectByVisibleText(formatted);
	        logger.info("Selected Cab: {}", formatted);
	    }
	}

	public void selectCabType(String cabTypeStr) {
	    if (cabTypeStr != null && !cabTypeStr.trim().isEmpty()) {
	        new Select(driver.findElement(cabType)).selectByVisibleText(normalizeOption(cabTypeStr));
	        logger.info("Selected Cab Type: {}", cabTypeStr);
	    }
	}

	public void enterPickupDate(String date) {
	    WebElement el = driver.findElement(pickupDate);
	    el.clear();
	    if (date != null) {
	        el.sendKeys(date);
	        logger.info("Entered Pickup Date: {}", date);
	    }
	}

	public void enterPickupTime(String time) {
	    WebElement el = driver.findElement(pickupTime);
	    el.clear();
	    if (time != null) {
	        el.sendKeys(time);
	        logger.info("Entered Pickup Time: {}", time);
	    }
	}

	public void selectPassengerCount(String count) {
	    if (count != null && !count.trim().isEmpty()) {
	        new Select(driver.findElement(passengerCount)).selectByVisibleText(count);
	        logger.info("Selected Passenger Count: {}", count);
	    }
	}

	public void chooseTripType(String tripType) {
	    if (tripType == null) return;
	    if (tripType.equalsIgnoreCase("oneway")) {
	        driver.findElement(tripTypeOneway).click();
	        logger.info("Trip Type: Oneway");
	    } else if (tripType.equalsIgnoreCase("roundtrip")) {
	        driver.findElement(tripTypeRoundtrip).click();
	        logger.info("Trip Type: Roundtrip");
	    }
	}

	public void clickBookNow() {
	    driver.findElement(bookNowButton).click();
	    logger.info("Clicked Book Now button");
	}

	public String getConfirmationMessage() {
	    String msg = driver.findElement(confirmationMessage).getText();
	    logger.info("Confirmation Message: {}", msg);
	    return msg;
	}

	public String getNameError() {
	    return driver.findElement(nameError).getText();
	}

	public String getPhoneError() {
	    return driver.findElement(phoneError).getText();
	}

	public boolean validateErrorMessage(String expectedMessage, String fieldLabel) {
	    boolean status = false;
	    try {
	        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
	        WebElement errorElement = wait.until(ExpectedConditions.visibilityOfElementLocated(confirmationMessage));
	        String actualText = errorElement.getText().trim();

	        if (actualText.equalsIgnoreCase(expectedMessage)) {
	            Reports.generateReport(driver, Hooks.test, Status.PASS,
	                    "Correct message shown under " + fieldLabel + ": '" + actualText + "'");
	            logger.info("Validation PASSED for field {}: {}", fieldLabel, actualText);
	            status = true;
	        } else {
	            Reports.generateReport(driver, Hooks.test, Status.FAIL,
	                    "Mismatch under " + fieldLabel + ". Expected: '" + expectedMessage + "', Found: '" + actualText + "'");
	            logger.error("Validation FAILED for field {}: expected '{}', but got '{}'", fieldLabel, expectedMessage, actualText);
	        }

	    } catch (TimeoutException e) {
	        Reports.generateReport(driver, Hooks.test, Status.FAIL,
	                "Timeout: Message not visible under " + fieldLabel);
	        logger.error("Timeout waiting for validation message under {}", fieldLabel);
	    }

	    if (!status) {
	        String screenshotPath = Reports.captureScreenshot(driver, "Error_" + fieldLabel.replaceAll(" ", "_"));
	        Hooks.test.info("Screenshot on failure").addScreenCaptureFromPath(screenshotPath);
	    }

	    return status;
	}

	public String getEmailMissing() {
	    WebElement errorElement = driver.findElement(emailMissingError);
	    String text = errorElement.getText().trim();

	    if (text.isEmpty()) {
	        text = errorElement.getAttribute("innerText");
	        if (text == null || text.isEmpty()) {
	            text = errorElement.getAttribute("textContent");
	        }
	    }

	    logger.info("Email missing error text: {}", text);
	    return text;
	}

	public String getTripError() {
	    return driver.findElement(tripError).getText();
	}

	public String getCabError() {
	    return driver.findElement(cabError).getText();
	}

	public String getPassengerError() {
	    return driver.findElement(passengerError).getText();
	}

}
