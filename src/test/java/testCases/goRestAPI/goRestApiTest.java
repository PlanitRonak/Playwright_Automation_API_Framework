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

public class goRestApiTest extends baseApiTest {
    private static final Logger logger = LoggerFactory.getLogger(goRestApiTest.class);
    private static int id;

    @Test(priority = 1, enabled = true)
    public void getAllUsers () throws JsonProcessingException {
        logger.info("Initiating get request to /public/v2/users");
        APIResponse res = request.get("/public/v2/users");
        System.out.println("Response body : "+res.text());
        JsonNode user = mapper.readTree(res.text());
        id = Integer.parseInt(String.valueOf(user.get(0).get("id")));
        Assert.assertEquals(res.status(), 200, "Something went wrong.");
        logger.info("Request send Successfully");
    }

    @Test(priority = 2, enabled = true)
    public void getSpecificUser() throws JsonProcessingException {
        logger.info("Initiating get request to /public/v2/id");
            APIResponse res = request.get("/public/v2/users/"+id);
            System.out.println("User : "+res.text());
            System.out.println("URL : "+res.url());
            JsonNode jsonResponse = mapper.readTree(res.text());
            System.out.println(jsonResponse.get("id"));
            int resId = Integer.parseInt(String.valueOf(jsonResponse.get("id")));
            Assert.assertEquals(resId, id);
    }

    @Test(priority = 3, enabled = true)
    public void creatingUser() throws InterruptedException, JsonProcessingException {
            User employee = new User();
            employee.setName("Demo Ronak");
            employee.setEmail("DemoRonakYadav@example.com");
            employee.setGender("male");
            employee.setStatus("active");
            APIResponse res = request.post("/public/v2/users",RequestOptions.create()
                            .setHeader("Authorization","Bearer "+prop.getProperty("token"))
                            .setData(employee));
            System.out.println("Response body : "+res.text());
            JsonNode obj = mapper.readTree(res.text());
            id = Integer.parseInt(String.valueOf(obj.get("id")));
            Assert.assertEquals(res.status(), 201, "User not created.");
        Thread.sleep(2000);
    }

    @Test(priority = 4, enabled = true)
    public void replaceUser() {
            Update update = new Update();
            update.setName("Updated Name");
            update.setStatus("Inactive");
            APIResponse res = request.put("/public/v2/users/"+id, RequestOptions.create()
                    .setHeader("Authorization","Bearer "+prop.getProperty("token"))
                    .setData(update));
            System.out.println("Updated request body : "+res.text());
    }

    @Test(priority = 5, enabled = true)
    public void deleteUser() {
        APIResponse res = request.delete("/public/v2/users/"+id, RequestOptions.create()
                .setHeader("Authorization","Bearer "+prop.getProperty("token"))
        );
        Assert.assertEquals(res.status(), 204, "User not found");
        System.out.println("User deleted Successfully.");
    }
}