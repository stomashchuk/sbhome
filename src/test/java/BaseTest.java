import hometask.Application;
import hometask.watcher.MyWatcher;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.openqa.selenium.By;

public class BaseTest {

    @Rule
    public MyWatcher rule = new MyWatcher(app.getDriver());

    static final Application app = new Application();

    @BeforeClass
    public static void setUp() {
        app.init();
    }

    @AfterClass
    public static void tearDown() {
        app.stop();
    }

}


