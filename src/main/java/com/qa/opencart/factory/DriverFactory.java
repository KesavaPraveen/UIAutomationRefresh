package com.qa.opencart.factory;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

public class DriverFactory {

    public Properties prop;
    public WebDriver driver;
    public OptionsManager optionsManager;
    public static String highlight;
    public static ThreadLocal<WebDriver> threadLocal=new ThreadLocal<>();


    public WebDriver initiateDriver(Properties prop)
    {
        String browserName= prop.getProperty("browser").trim();
        optionsManager=new OptionsManager(prop);
        if(browserName.equalsIgnoreCase("chrome"))
        {
            if(Boolean.parseBoolean(prop.getProperty("remote")))
            {
                // Remote Execution
                initiateRemoteDriver("chrome");
            }
            else {
                // Local Execution
                threadLocal.set(new ChromeDriver(optionsManager.getChromeOptions()));
            }
            //driver=new ChromeDriver(optionsManager.getChromeOptions());
        }
        else if(browserName.equalsIgnoreCase("firefox"))
        {
            if(Boolean.parseBoolean(prop.getProperty("remote")))
            {
                // Remote Execution
                initiateRemoteDriver("firefox");
            }
            else {
                // Local Execution
                threadLocal.set(new FirefoxDriver(optionsManager.getFireFoxOptions()));
            }
            //driver=new FirefoxDriver(optionsManager.getFireFoxOptions());
        }
        else if(browserName.equalsIgnoreCase("edge"))
        {
            if(Boolean.parseBoolean(prop.getProperty("remote")))
            {
                // Remote Execution
                initiateRemoteDriver("edge");
            }
            else {
                // Local Execution
                threadLocal.set(new EdgeDriver(optionsManager.getEdgeOptions()));
            }
            //driver=new EdgeDriver(optionsManager.getEdgeOptions());
        }
        /*else if (browserName.equalsIgnoreCase("safari"))
        {
            if(Boolean.parseBoolean(prop.getProperty("remote")))
            {
                // Remote Execution
            }
            else {
                // Local Execution
            }
        }*/
        getDriver().manage().deleteAllCookies();
        getDriver().manage().window().maximize();
        getDriver().get(initiateProp().getProperty("url"));
        highlight=prop.getProperty("highlight").trim();
        return getDriver();
    }

    private void initiateRemoteDriver(String browserName) {

        System.out.println("Running Test Cases on Remote Server using Selenium Grid in " +browserName);

        if(browserName.equalsIgnoreCase("chrome"))
        {
            try {
                threadLocal.set(new RemoteWebDriver(new URL(prop.getProperty("hubUrl"))
                        ,optionsManager.getChromeOptions()));
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            }
        }

        else if(browserName.equalsIgnoreCase("firefox"))
        {
            try {
                threadLocal.set(new RemoteWebDriver(new URL(prop.getProperty("hubUrl"))
                        ,optionsManager.getFireFoxOptions()));
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            }
        }

        else if(browserName.equalsIgnoreCase("edge"))
        {
            try {
                threadLocal.set(new RemoteWebDriver(new URL(prop.getProperty("hubUrl"))
                        ,optionsManager.getEdgeOptions()));
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static WebDriver getDriver()
    {
        return threadLocal.get();
    }


    public Properties initiateProp()
    {
        prop=new Properties();
        try {
            FileInputStream fileInputStream = new FileInputStream("./src/test/resources/Config/config.properties");
            prop.load(fileInputStream);
        }
        catch (FileNotFoundException fileNotFoundException)
        {
            fileNotFoundException.printStackTrace();
            throw new RuntimeException("failed to find the config properties file....");
        }
        catch (IOException ioException)
        {
            ioException.printStackTrace();
            throw new RuntimeException("failed to read properties in the file....");
        }
        return prop;
    }

    /**
     * take screenshot
     */
    public static String getScreenshot() {
        File srcFile = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);
        String path = System.getProperty("user.dir") + "/screenshot/" + System.currentTimeMillis() + ".png";
        File destination = new File(path);
        try {
            FileUtils.copyFile(srcFile, destination);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return path;
    }

}
