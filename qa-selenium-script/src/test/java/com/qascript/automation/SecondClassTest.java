package com.qascript.automation;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.List;

@Slf4j
public class SecondClassTest {
    WebDriver driver;

    @BeforeTest
    public void setUp() throws InterruptedException {
        System.setProperty("webdriver.chrome.driver", "C:\\Program Files\\ChromeDriver\\chromedriver-win64\\chromedriver.exe");
        driver = new ChromeDriver();

        driver.get("https://www.google.com");
        driver.manage().window().maximize();

        Thread.sleep(3000);
        driver.findElement(By.cssSelector("textarea")).sendKeys("https://www.saucedemo.com/");
        Thread.sleep(2000);

        driver.navigate().to("https://www.saucedemo.com/");
    }

    @Test
    public void testCaseLoginSuccess() throws InterruptedException {
        Assert.assertEquals(driver.getCurrentUrl(), "https://www.saucedemo.com/");

        String cssValue = driver.findElement(By.name("login-button")).getCssValue("background-color");
        log.info("background-color: {} ", cssValue);

        String attributeValue = driver.findElement(By.name("login-button")).getAttribute("value");
        log.info("attribute value in button login: {} ", attributeValue);

        driver.findElement(By.id("user-name")).sendKeys("problem_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();

        // check position of window
        Point position = driver.manage().window().getPosition();
        log.info("position x: {} ", position.x);
        log.info("position y: {} ", position.y);

        driver.manage().window().minimize();

        Thread.sleep(3000);

        // move position of window
        Point newPosition = new Point(90, 90);
        driver.manage().window().setPosition(newPosition);

        Thread.sleep(3000);
    }

    @Test (priority = 1, dependsOnMethods = "testCaseLoginSuccess")
    public void testCheckSingleDropdownMenu() throws InterruptedException {
        driver.manage().window().maximize();

        WebElement element = driver.findElement(By.className("product_sort_container"));
        Select select = new Select(element);
        select.selectByVisibleText("Name (A to Z)");
        select.selectByValue("lohi");
        select.selectByIndex(2);

        // single select dropdown
        if(select.isMultiple()) {
            log.info("[testCheckSingleDropdownMenu] -- dropdown is multiple");
        } else {
            log.info("[testCheckSingleDropdownMenu] -- dropdown is single");
        }

        Thread.sleep(3000);
        driver.manage().window().minimize();
    }

    @Test (priority = 2)
    public void testMultipleSelectDropdown() throws InterruptedException {
        driver.manage().window().maximize();
        Thread.sleep(3000);
        driver.get("https://mdbootstrap.com/docs/standard/extended/multiselect/");
        WebElement element = driver.findElement(By.xpath("//*[@id=\"section-select-with-label\"]/section[1]/div/section/div/select"));
        Select select = new Select(element);

        if(select.isMultiple()) {
            log.info("[testMultipleSelectDropdown] -- dropdown is multiple");
        } else {
            log.info("[testMultipleSelectDropdown] -- dropdown is single");
        }
    }

    @Test (priority = 3, dependsOnMethods = "testMultipleSelectDropdown")
    public void testSelectOption() {
        String currentUrl = driver.getCurrentUrl();
        log.info("current url : {} ", currentUrl);

        WebElement element = driver.findElement(By.xpath("//*[@id=\"section-select-with-label\"]/section[1]/div/section/div/select"));
        Select select = new Select(element);

        select.selectByVisibleText("One");
        select.selectByVisibleText("Two");
        select.selectByVisibleText("Three");

        WebElement firstSelectedOption = select.getFirstSelectedOption();

        log.info("first selected option: {} ", firstSelectedOption.getAttribute("value"));

        List<WebElement> options = select.getAllSelectedOptions();
        log.info("size options: {} ", options.size());
        for (WebElement option : options) {
            log.info("selected option: {} ", option.getAttribute("value"));
        }

    }

    @AfterTest
    public void testDown() {
        driver.quit();
    }
}
