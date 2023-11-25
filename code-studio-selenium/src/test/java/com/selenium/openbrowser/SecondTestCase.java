package com.selenium.openbrowser;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class SecondTestCase {
    WebDriver webDriver;

    @BeforeTest
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "C:\\Program Files\\ChromeDriver\\chromedriver-win64\\chromedriver.exe");
        webDriver = new ChromeDriver();

        webDriver.get("https://github.com/ketsar28?tab=repositories");
        webDriver.manage().window().maximize();

        webDriver.navigate().to("https://accounts.saucelabs.com/am/XUI/#login/");

        webDriver.manage().timeouts().implicitlyWait(Duration.ofMillis(5000));

    }

    @Test
    public void testInputFormWithIncorrectData() {
        webDriver.findElement(By.id("idToken1")).sendKeys("ketsarali");
        webDriver.findElement(By.id("idToken2")).sendKeys("root");

        webDriver.findElement(By.className("checkmark")).click();
        webDriver.manage().timeouts().implicitlyWait(Duration.ofMillis(3000));
        webDriver.findElement(By.id("loginButton_0")).click();
    }

    @Test
    public void testInputFormWithGithubAccount() throws InterruptedException {
        webDriver.findElement(By.xpath("//*[@id=\"content\"]/div/div/section[1]/div/div[1]/nav/ul/li[2]")).click();


        webDriver.findElement(By.name("login")).sendKeys("muhammadketsar1@gmail.com");
        webDriver.findElement(By.name("password")).sendKeys("telkomsel2005");
        webDriver.findElement(By.name("commit")).click();

        if (webDriver.getCurrentUrl().contains("https://github.com/login/oauth/authorize")) {
            Thread.sleep(7000);
            webDriver.findElement(By.id("js-oauth-authorize-btn")).click();
        }

        System.out.println("currentUrl : " + webDriver.getCurrentUrl());

        String windowHandle = webDriver.getWindowHandle();
        webDriver.switchTo().window(windowHandle);

        List<WebElement> javaText = webDriver.findElements(By.className("TagsBox__tags-container__xjL34"));
        Thread.sleep(8000);
        System.out.println("size : " + javaText.size());

    }

    @AfterTest
    public void afterTestClose() {
        webDriver.manage().window().minimize();
        webDriver.close();
    }
}
