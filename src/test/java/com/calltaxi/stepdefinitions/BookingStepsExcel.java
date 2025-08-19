package com.calltaxi.stepdefinitions;

import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.openqa.selenium.WebDriver;

import com.calltaxi.base.DriverFactory;
import com.calltaxi.pages.BookingPage;
import com.calltaxi.utils.ExcelUtil;
import static com.calltaxi.hooks.Hooks.test;

import io.cucumber.java.en.*;

public class BookingStepsExcel {

	WebDriver driver;
	BookingPage bookingPage;
	List<Map<String, String>> data;
	Map<String, String> testRow;

	@Given("User launches the browser and opens the cab booking page")
	public void user_opens_booking_page() {
	    driver = DriverFactory.getDriver();
	    driver.get("https://webapps.tekstac.com/SeleniumApp2/CallTaxiService/booking.html");
	    bookingPage = new BookingPage(driver, test);
	}
	@When("User fills the form with valid details:")
	public void user_fills_form(io.cucumber.datatable.DataTable dataTable) {
	    Map<String, String> formData = dataTable.asMaps().get(0);
	    bookingPage.enterFullName(formData.get("Name"));
	    bookingPage.enterPhone(formData.get("Phone"));
	    bookingPage.enterEmail(formData.get("Email"));
	    bookingPage.selectTrip(formData.get("Trip"));
	    bookingPage.selectCab(formData.get("Cab"));
	    bookingPage.selectCabType(formData.get("CabType"));
	    bookingPage.enterPickupDate(formData.get("Date"));
	    bookingPage.enterPickupTime(formData.get("Time"));
	    bookingPage.selectPassengerCount(formData.get("Passenger"));
	    bookingPage.chooseTripType(formData.get("TripType"));
	}

	@When("User reads booking data from Excel sheet {string} and row {int}")
	public void user_reads_excel_data(String sheet, Integer rowNum) {
	    driver = DriverFactory.getDriver();
	    driver.get("https://webapps.tekstac.com/SeleniumApp2/CallTaxiService/booking.html");
	    bookingPage = new BookingPage(driver);

	    data = ExcelUtil.getData(sheet);
	    testRow = data.get(rowNum);
	}

	@When("User fills the form using Excel data")
	public void user_fills_form_excel() {
	    bookingPage.enterFullName(testRow.get("Name"));
	    bookingPage.enterPhone(testRow.get("Phone"));
	    bookingPage.enterEmail(testRow.get("Email"));
	    bookingPage.selectTrip(testRow.get("Trip"));
	    bookingPage.selectCab(testRow.get("Cab"));
	    bookingPage.selectCabType(testRow.get("CabType"));
	    bookingPage.enterPickupDate(testRow.get("Date"));
	    bookingPage.enterPickupTime(testRow.get("Time"));
	    bookingPage.selectPassengerCount(testRow.get("Passenger"));
	    bookingPage.chooseTripType(testRow.get("TripType"));
	}

	@And("User clicks on Book Now button")
	public void user_clicks_book_now() {
	    bookingPage.clickBookNow();
	}

	@Then("Booking confirmation message {string} should be displayed")
	public void booking_confirmation_should_be_displayed(String expected) {
	    Assert.assertTrue(bookingPage.getConfirmationMessage().contains(expected));
	}
	
	@Then("Email error message {string} should be displayed")
	public void email_error_should_be_displayed(String expected) {
	    Assert.assertTrue(bookingPage.getEmailMissing().contains(expected));
	}

	@Then("Error message {string} should be shown under Name field")
	public void name_error_displayed(String expected) {
	    Assert.assertTrue(bookingPage.getNameError().contains(expected));
	}

	@Then("Error message {string} should be shown under Trip selection")
	public void trip_error_displayed(String expected) {
	    Assert.assertTrue(bookingPage.getTripError().contains(expected));
	}

	@Then("Error message {string} should be shown under Passenger count")
	public void passenger_error_displayed(String expected) {
	    Assert.assertTrue(bookingPage.getPassengerError().contains(expected));
	}
	
	@Then("Error message {string} should be shown under {string} field")
	public void validate_error_under_field(String expectedMessage, String fieldName) {
		boolean result = false;
		switch (fieldName.toLowerCase()) {
	    case "name":
	        result = bookingPage.validateErrorMessage(expectedMessage, "Name");
	        break;
	    case "phone":
	        result = bookingPage.validateErrorMessage(expectedMessage, "Phone");
	        break;
	    case "email":
	        result = bookingPage.validateErrorMessage(expectedMessage, "Email");
	        break;
	    case "pickup time":
	        result = bookingPage.validateErrorMessage(expectedMessage, "Pickup Time");
	        break;
	    // add more as needed
	    default:
	    	 throw new IllegalArgumentException("Unknown field name passed: " + fieldName);
		}

	Assert.assertTrue("Validation failed for field: " + fieldName, result);

	}

}
