package com.calltaxi.base;

import java.time.Duration;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.calltaxi.config.ConfigReader;

import io.github.bonigarcia.wdm.WebDriverManager;

public class DriverFactory {

	private static WebDriver driver;
	private static Properties prop;

	public static WebDriver initDriver() {
	    prop = ConfigReader.initProperties();
	    String browser = prop.getProperty("browser").toLowerCase();

	    switch (browser) {
	        case "chrome":
	            WebDriverManager.chromedriver().setup();
	            driver = new ChromeDriver();
	            break;

	        case "firefox":
	            WebDriverManager.firefoxdriver().setup();
	            driver = new FirefoxDriver();
	            break;

	        default:
	            System.out.println("❌ Unsupported browser in config.properties: " + browser);
	            throw new RuntimeException("Unsupported browser: " + browser);
	    }

	    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(Long.parseLong(prop.getProperty("implicitWait"))));
	    driver.manage().window().maximize();
	    return driver;
	}

	public static WebDriver getDriver() {
	    return driver;
	}

	public static void quitDriver() {
	    if (driver != null) {
	        driver.quit();
	    }
	}

}