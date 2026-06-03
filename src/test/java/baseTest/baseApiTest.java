package baseTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.playwright.APIRequest;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.Request;

import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.Logger;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class baseApiTest {

    protected static final Logger logger = org.apache.log4j.Logger.getLogger(baseApiTest.class);
    protected Playwright playwright;
    protected APIRequestContext request;
    protected Properties prop;
    protected ObjectMapper mapper = new ObjectMapper();

    @BeforeMethod(alwaysRun = true)
    public void setup() {
        playwright = Playwright.create();
        initProp();
        request = setUpRequest(prop);
    }

    @AfterMethod(alwaysRun = true)
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

    public APIRequestContext setUpRequest(Properties prop) {
        Map<String, String> headersList = new HashMap<String, String>();
        switch(prop.getProperty("authType").toLowerCase()) {
            case "jwt" :
                headersList.put("Authorization", "Bearer "+prop.getProperty("token"));
                return playwright.request().newContext(new APIRequest.NewContextOptions()
                        .setBaseURL(prop.getProperty("url"))
                        .setExtraHTTPHeaders(headersList));
            case "basicauth" :
                return playwright.request().newContext(new APIRequest.NewContextOptions()
                        .setBaseURL(prop.getProperty("url"))
                        .setHttpCredentials(prop.getProperty("username"), prop.getProperty("password")));
            case "apiKey" :
                headersList.put("X-Api-Key", prop.getProperty("value"));
                return playwright.request().newContext(new APIRequest.NewContextOptions()
                        .setBaseURL(prop.getProperty("url"))
                        .setExtraHTTPHeaders(headersList));
            default :
                return playwright.request().newContext(new APIRequest.NewContextOptions()
                        .setBaseURL(prop.getProperty("url")));
        }
    }
}
