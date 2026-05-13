package testCases;

import baseTest.baseApiTest;
import com.microsoft.playwright.APIResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

public class employeeApiTest extends baseApiTest {
    private static final Logger logger = LoggerFactory.getLogger(employeeApiTest.class);
    private final String testEnvironment = System.getProperty("env", "dev");
    private final String filePath = String.format("src/test/resources/testdata/%s.json", testEnvironment);

    @Test(enabled = true)
    public void testGetUsers() {
        logger.info("Initiating get request to /employees");
        APIResponse response = request.get("/api/v1/employees");
//          To validate specific thing in a response body
//        String responseBody = response.text();
//        try {
//            // Convert String to JSON
//            ObjectMapper mapper = new ObjectMapper();
//            System.out.println("Response URL: " + response.url());
//            System.out.println("Status Code: " + response.status());
//            System.out.println("Response Body:");
//            System.out.println(responseBody);
//            JsonNode jsonResponse = mapper.readTree(responseBody);
//            // Access JSON fields
//            System.out.println("Status: " +
//                    jsonResponse.get("status").asText());
//            System.out.println("First Employee Name: " +
//                    jsonResponse.get("data").get(0)
//                            .get("employee_name").asText());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        Assert.assertEquals(response.status(), 200, "Status code mismatch");
        logger.info("List of employees : {}", response.text());
    }

//    response.as(AmplifyPayload.class); Response in POJO class formate.
//        ObjectMapper mapper = new ObjectMapper();
//        User user = mapper.readValue(jsonResponse, AmplifyPayload.class);
//        System.out.println(AmplifyPayload.getName());
//        System.out.println(AmplifyPayload.getEmail());
}