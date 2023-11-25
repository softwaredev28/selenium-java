package com.selenium.openbrowser;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;


public class FirstTestCase {
    WebDriver webDriver;
    ChromeDriver chromeDriver;

    @Test
    public void testOpenBrowser() {
        System.setProperty("webdriver.chrome.driver", "C:\\Program Files\\ChromeDriver\\chromedriver-win64\\chromedriver.exe");
        webDriver = new ChromeDriver();
        webDriver.navigate().to("https://start.spring.io/");

        // scroll down
        JavascriptExecutor js = (JavascriptExecutor) webDriver;
        js.executeScript("window.scrollTo(0, 500)");

        // maximize
        webDriver.manage().window().maximize();

        webDriver.get("https://geeksforgeeks.org/how-to-open-chrome-browser-using-selenium-in-java/");
        js.executeScript("window.scrollTo(0, 900)");

        // get title
        System.out.println("title : " + webDriver.getTitle());

        // get current url
        System.out.println("currentUrl : " + webDriver.getCurrentUrl());

        // get page source
        System.out.println("pageSource : " + webDriver.getPageSource());
        webDriver.quit();
    }
}
