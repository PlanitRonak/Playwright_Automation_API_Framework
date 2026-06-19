package testCases.goRestAPI;

import baseTest.baseApiTest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.options.RequestOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;
import pojo.Update;
import pojo.User;
import utilities.jsonReader;

public class goRestApiTest extends baseApiTest {
    private static int id;

    @Test(priority = 1, enabled = true)
    public void getAllUsers () throws JsonProcessingException {
        logger.info("Initiating get request to getAllUsers");
        APIResponse res = request.get("/public/v2/users");
        logger.info("Sent the Get Request");
        System.out.println("Response body : "+res.text());
        JsonNode user = mapper.readTree(res.text());
        id = Integer.parseInt(String.valueOf(user.get(0).get("id")));
        logger.info("Collected Id for next Test");
        Assert.assertEquals(res.status(), 200, "Something went wrong.");
        logger.info("Request send Successfully");
    }

    @Test(priority = 2, enabled = true)
    public void getSpecificUser() throws JsonProcessingException {
        logger.info("Initiating get request to getSpecificUser");
            APIResponse res = request.get("/public/v2/users/"+id);
            System.out.println("User : "+res.text());
            System.out.println("URL : "+res.url());
            jsonReader jsonHelper = new jsonReader(res.text());
//            JsonNode jsonResponse = mapper.readTree(res.text());
            System.out.println(jsonHelper.getStringValue("id"));
            int resId = jsonHelper.getIntValue("id");
            Assert.assertEquals(resId, id);
    }

    @Test(priority = 3, enabled = false)
    public void creatingUser() throws InterruptedException, JsonProcessingException {
            User employee = new User();
            employee.setName("Demo Ronak");
            employee.setEmail("DemoRonakYadav@example.com");
            employee.setGender("male");
            employee.setStatus("active");
            APIResponse res = request.post("/public/v2/users",RequestOptions.create()
                            .setData(employee));
            System.out.println("Response body : "+res.text());
            JsonNode obj = mapper.readTree(res.text());
            id = Integer.parseInt(String.valueOf(obj.get("id")));
            Assert.assertEquals(res.status(), 201, "User not created.");
        Thread.sleep(2000);
    }

    @Test(priority = 4, enabled = false)
    public void replaceUser() {
            Update update = new Update();
            update.setName("Updated Name");
            update.setStatus("Inactive");
            APIResponse res = request.put("/public/v2/users/"+id, RequestOptions.create()
                    .setData(update));
            System.out.println("Updated request body : "+res.text());
    }

    @Test(priority = 5, enabled = false)
    public void deleteUser() {
        APIResponse res = request.delete("/public/v2/users/"+id);
        Assert.assertEquals(res.status(), 204, "User not found");
        System.out.println("User deleted Successfully.");
    }
}