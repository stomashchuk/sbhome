import hometask.Application;
import hometask.watcher.OnFailWatcher;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;

public class BaseTest {

    @Rule
    public OnFailWatcher rule = new OnFailWatcher(app.getDriver());

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


