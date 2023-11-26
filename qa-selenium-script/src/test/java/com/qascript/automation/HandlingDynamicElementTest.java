package com.qascript.automation;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.function.Function;

@Slf4j
public class HandlingDynamicElementTest {
    WebDriver driver;

    @BeforeTest
    public void setUp() throws InterruptedException {
        System.setProperty("webdriver.chrome.driver", "C:\\Program Files\\ChromeDriver\\chromedriver-win64\\chromedriver.exe");
        driver = new ChromeDriver();

        driver.get("https://www.google.com");
        driver.manage().window().maximize();

        Thread.sleep(2000);
        driver.findElement(By.cssSelector("textarea")).sendKeys("https://www.saucedemo.com/");
        Thread.sleep(2000);

        driver.navigate().to("https://www.saucedemo.com/");
    }

    @Test
    public void testCheckElementUsingXPathMethods() throws InterruptedException {
        driver.findElement(By.xpath("//h4[text()=\"Accepted usernames are:\"]")).isDisplayed();

        driver.findElement(By.xpath("//input[starts-with(@id, 'user')]")).sendKeys("standard_user");
        driver.findElement(By.xpath("//input[contains(@id, 'password')]")).sendKeys("secret_sauce");
        driver.findElement(By.xpath("//input[@id='login-button']")).click();

        // check current url
        log.info("current url: {} ", driver.getCurrentUrl());
        driver.findElement(By.xpath("(//button[text()=\"Add to cart\"])[2]")).click();

        Thread.sleep(3000);
        driver.findElement(By.className("shopping_cart_link")).click();
        Thread.sleep(3000);
    }

    @Test
    public void testCheckElementByXPathAxesMethods() {
        driver.findElement(By.xpath("//div[@class='login-box']//descendant::div")).isDisplayed();
        driver.findElement(By.xpath("//div[@class='login-box']//following::input[1]")).sendKeys("secret_sauce");
        driver.findElement(By.xpath("//div[@class='login-box']//parent::div")).isDisplayed();
        driver.findElement(By.xpath("//div[@class='login-box']//child::div")).sendKeys("standard_user");
        driver.findElement(By.xpath("//div[@class='login-box']//following-sibling::div")).sendKeys("errors");
    }


    @Test
    public void testCheckElementByWaitMethods() {
        // implicit wait
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(3000));

        // explicit wait
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(By.id("login-button")));

        // fluent wait
        Wait<WebDriver> fluentWait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(20))
                .pollingEvery(Duration.ofMillis(1000))
                .ignoring(NoSuchElementException.class);

        WebElement element = fluentWait.until(webDriver -> driver.findElement(By.id("login-button")));
    }

    @AfterTest
    public void afterTestClose() {
        driver.quit();
    }
}
