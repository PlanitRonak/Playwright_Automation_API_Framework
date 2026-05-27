package testCases.authentication;

import baseTest.baseApiTest;
import com.microsoft.playwright.APIResponse;
import org.testng.Assert;
import org.testng.annotations.Test;

public class basicAuth extends baseApiTest {

    @Test
    public void basicAuthorization() {
        APIResponse response = request.get("/basic-auth");
        Assert.assertEquals(response.status(), 200, "Authorization failed.");
        logger.info("Basic Auth Success.");
    }
}
