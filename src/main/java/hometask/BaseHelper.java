package hometask;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.function.Function;

public class BaseHelper {

    private WebDriver driver;
    private WebDriverWait wait;
    Logger log;

    BaseHelper(ApplicationManager app) {
        this.driver = app.getDriver();
        wait = new WebDriverWait(app.getDriver(), 20);
        log = Logger.getLogger(BaseHelper.class);
    }

    public void goToUrl(String url) {
        log.info("Getting url " + url);
        driver.get(url);
        waitForPageLoaded();
        checkThatPageWithoutErrors();
    }

    void click(By locator) {
        log.info("Clicking element with locator " + locator);
        waitUntilClickable(locator).click();
        waitForPageLoaded();
        checkThatPageWithoutErrors(locator);
    }

    void sendKeys(By field, String text) {
        log.info("Sending keys " + text + " to element " + field);
        waitUntilClickable(field).sendKeys(text);
    }

    WebElement element(By locator) {
        log.info("Getting WebElement from locator " + locator);
        return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    List<WebElement> elements(By locator) {
        log.info("Getting List of WebElements from locator " + locator);
        return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
    }

    boolean elementIsPresent(By locator) {
        log.info("Checking that element " + locator + " is present");
        return driver.findElements(locator).size() > 0;
    }

    void waitForSomeSeconds(int secondsCount) {
        try {
            Thread.sleep(secondsCount * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private WebElement waitUntilClickable(By locator) {
        log.info("Waiting until element " + locator + " will be clickable");
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    private void waitForPageLoaded() {
        log.info("Waiting until page will be loaded");
        Function<WebDriver, Boolean> pageLoaded = new Function<WebDriver, Boolean>() {
            @Override
            public Boolean apply(WebDriver input) {
                return ((JavascriptExecutor) input).executeScript("return document.readyState").equals("complete");
            }
        };
        wait.until(pageLoaded);
    }

    // Если 504 при переходе на страницу по адресу, то обновляем 10 раз, если не помогло, то эксепшен с page source
    private void checkThatPageWithoutErrors() {
        log.info("Checking that page loaded without errors");
        Element header = getHeader();
        if (header != null) {
            if (header.attr("class").equals("n-error__header")) {
                for (int i = 0; i < 10; i++) {
                    driver.navigate().refresh();
                    waitForPageLoaded();
                    header = getHeader();
                    if (header == null) {
                        break;
                    } else {
                        if (!header.attr("class").equals("n-error__header")) {
                            break;
                        } else {
                            if (i == 9) {
                                Document document = Jsoup.parse(driver.getPageSource());
                                throw new RuntimeException("ERROR WHILE LOADING PAGE\n" + document.toString());
                            }
                        }
                    }
                }
            }
        }
    }

    // Если 504 при клике куда-то, то возвращаемся назад и снова кликаем туда же. Если после 10-ти попыток не прошло, то эксепшен с page source
    private void checkThatPageWithoutErrors(By locator) {
        log.info("Checking that page loaded without errors");
        Element header = getHeader();
        if (header != null) {
            if (header.attr("class").equals("n-error__header")) {
                for (int i = 0; i < 10; i++) {
                    driver.navigate().back();
                    recoveryClick(locator);
                    header = getHeader();
                    if (header == null) {
                        break;
                    } else {
                        if (!header.attr("class").equals("n-error__header")) {
                            break;
                        } else {
                            if (i == 9) {
                                Document document = Jsoup.parse(driver.getPageSource());
                                throw new RuntimeException("ERROR WHILE LOADING PAGE\n" + document.toString());
                            }
                        }
                    }
                }
            }
        }
    }

    private Element getHeader() {
        Document document = Jsoup.parse(driver.getPageSource());
        Elements header = document.getElementsByTag("header");
        return header.first();
    }

    private void recoveryClick(By locator) {
        waitUntilClickable(locator).click();
        waitForPageLoaded();
    }
}
