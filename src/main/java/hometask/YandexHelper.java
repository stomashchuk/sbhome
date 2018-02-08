package hometask;

import org.openqa.selenium.By;
import ru.yandex.qatools.allure.annotations.Step;

public class YandexHelper extends BaseHelper {

    private By marketLink = By.linkText("Маркет");
    private By additionalFiltersLink = By.linkText("Перейти ко всем фильтрам");
    private By priceFromField = By.id("glf-pricefrom-var");
    private By priceToField = By.id("glf-priceto-var");
    private By showAllManufacturersButton = By.cssSelector("[data-bem*='filterId=7893318'] button");
    private By manufacturerNameField = By.cssSelector("[data-bem*='filterId=7893318'] .input__control");
    private By collapseManufacturersButton = By.cssSelector("[data-bem*='filterId=7893318'] button");
    private By showFilteredButton = By.cssSelector(".button_action_show-filtered");
    private By elementsInMarketResult = By.cssSelector(".n-snippet-card2");
    private By nameOfFirstElementInMarketResult = By.cssSelector(".n-snippet-card2:first-child .n-snippet-card2__title a");
    private By marketSearchField = By.cssSelector("#header-search");
    private By findButton = By.cssSelector("[type='submit']");
    private By itemTitle = By.cssSelector(".n-product-title__text-container h1[class*=title]");

    YandexHelper(Application app) {
        super(app);
    }

    @Step("Открыть страницу яндекса")
    public YandexHelper openYandex() {
        goToUrl("https://www.yandex.ru/");
        return this;
    }

    @Step("Перейти в яндекс маркет")
    public YandexHelper gotoYaMarket() {
        click(marketLink);
        return this;
    }

    @Step("Выбрать раздел Компьютеры")
    public YandexHelper selectFromTopMenu(String topMenuSection) {
        By topMenuSectionLocator = By.xpath("//*[@class='topmenu__list']//li[@data-department='" + topMenuSection + "']");
        click(topMenuSectionLocator);
        return this;
    }

    @Step("Выбрать раздел согласно тестовым параметрам")
    public YandexHelper selectFromComputers(String computersSection) {
        By computersSectionLocator = By.xpath("//*[@class='catalog-menu__item'][1]//*[text()='" + computersSection + "']");
        click(computersSectionLocator);
        return this;
    }

    @Step("Зайти в расширенный поиск")
    public YandexHelper gotoAdditionalFilters() {
        click(additionalFiltersLink);
        return this;
    }

    @Step("Задать параметр поиска по цене")
    public YandexHelper applyPriceParameters(String priceFrom, String priceTo) {
        sendKeys(priceFromField, priceFrom);
        sendKeys(priceToField, priceTo);
        return this;
    }

    @Step("Выбрать производителя")
    public YandexHelper applyManufacturerParameters(String[] manufacturers) {
        for (String manufacturer : manufacturers) {
            By manufacturerLocator = By.xpath("//label[text()='" + manufacturer + "']");
            // Если производитель есть на главном экране, то выбираем
            if (elementIsPresent(manufacturerLocator)) {
                click(manufacturerLocator);
                //Иначе раскрываем список всех производителей и ищем нужного
            } else {
                click(showAllManufacturersButton);
                sendKeys(manufacturerNameField, manufacturer);
                waitForSomeSeconds(2);
                click(manufacturerLocator);
                click(collapseManufacturersButton);
                waitForSomeSeconds(1);
            }
        }
        return this;
    }

    @Step("Применить фильтр")
    public YandexHelper showFiltered() {
        click(showFilteredButton);
        return this;
    }

    @Step("Запомнить первый элемент в списке")
    public String takeFirstNameFromMarketResults() {
        return element(nameOfFirstElementInMarketResult).getText();
    }

    @Step("Найти запомненное значение")
    public YandexHelper marketSearch(String text) {
        sendKeys(marketSearchField, text);
        click(findButton);
        return this;
    }

    @Step("Запомнить найденный элемент")
    public String takeItemTitle() {
        return element(itemTitle).getText();
    }

    public int takeCountOfElementsFromMarketResult() {
        return elements(elementsInMarketResult).size();
    }
}
