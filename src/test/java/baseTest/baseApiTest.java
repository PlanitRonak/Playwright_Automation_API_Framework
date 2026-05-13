package baseTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.playwright.APIRequest;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.Playwright;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class baseApiTest {

    private static final Logger logger = LoggerFactory.getLogger(baseApiTest.class);
    protected Playwright playwright;
    protected APIRequestContext request;
    protected Properties prop;
    protected ObjectMapper mapper = new ObjectMapper();

    @BeforeClass(alwaysRun = true)
    public void setup() {
        playwright = Playwright.create();
        initProp();
        request = playwright.request().newContext(new APIRequest.NewContextOptions()
                .setBaseURL(prop.getProperty("url")));
    }

    @AfterClass(alwaysRun = true)
    public void teardown() {
        logger.info("Closing Playwright and disposing request context...");
        request.dispose();
        playwright.close();
    }

    public void initProp() {
        try {
            FileInputStream ip = new FileInputStream("./src/test/java/config/config.properties");
            prop = new Properties();
            prop.load(ip);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
