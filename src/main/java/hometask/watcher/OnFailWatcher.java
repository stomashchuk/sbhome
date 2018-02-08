package hometask.watcher;

import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import ru.yandex.qatools.allure.annotations.Attachment;
import ru.yandex.qatools.allure.annotations.Step;

public class OnFailWatcher extends TestWatcher {
    public WebDriver driver;

    public OnFailWatcher(WebDriver driver) {
        this.driver = driver;
    }

    @Override
    protected void failed(Throwable e, Description description) {
        makeScreenshotOnFailure();
    }

    @Step("Скриншот ошибки")
    @Attachment(value = "{0}", type = "image/png")
    public byte[] makeScreenshotOnFailure() {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }
}
