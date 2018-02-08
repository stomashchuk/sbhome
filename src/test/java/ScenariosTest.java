import hometask.model.TestParameters;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import ru.yandex.qatools.allure.annotations.Title;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class ScenariosTest extends BaseTest {

    private TestParameters testParam;

    public ScenariosTest(TestParameters testParam) {
        this.testParam = testParam;
    }

    @Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                {new TestParameters("Ноутбуки", "0", "30000", new String[] {"HP", "Lenovo"})},
                {new TestParameters("Планшеты", "20000", "25000", new String[]
                        {"Acer", "DELL", "ASUS", "Huawei", "Lenovo", "Samsung"})},
        });
    }

    @Test
    @Title("Наименование товара соответствует взятому значению")
    public void yaTest() {
        app.getYaHelper()
                .openYandex()
                .gotoYaMarket()
                .selectFromTopMenu("Компьютеры")
                .selectFromComputers(testParam.getSection())
                .gotoAdditionalFilters()
                .applyPriceParameters(testParam.getPriceFrom(), testParam.getPriceTo())
                .applyManufacturerParameters(testParam.getManufacturer())
                .showFiltered();

        int countOfElementsFromMarketResult = app.getYaHelper().takeCountOfElementsFromMarketResult();
        assertEquals(12, countOfElementsFromMarketResult);

        String itemNameExpected = app.getYaHelper().takeFirstNameFromMarketResults();
        app.getYaHelper()
                .marketSearch(itemNameExpected);
        String itemNameActual = app.getYaHelper().takeItemTitle();
        assertEquals(itemNameExpected, itemNameActual);

    }
}
