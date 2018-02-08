package hometask;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class Application {

    private WebDriver driver;
    private YandexHelper yaHelper;

    public void init() {
        System.setProperty("webdriver.chrome.driver", "lib/drivers/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        yaHelper = new YandexHelper(this);
    }

    public void stop() {
        driver.quit();
    }

    public WebDriver getDriver() {
        return driver;
    }

    public YandexHelper getYaHelper() {
        return yaHelper;
    }
}
