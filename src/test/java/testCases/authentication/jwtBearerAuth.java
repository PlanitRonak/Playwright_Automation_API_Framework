package testCases.authentication;

import baseTest.baseApiTest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.options.RequestOptions;
import org.testng.Assert;
import org.testng.annotations.Test;
import pojo.User;

public class jwtBearerAuth extends baseApiTest {
    private static int id;

    @Test(priority = 1,enabled = true)
    public void createUser() throws JsonProcessingException, InterruptedException {
        User employee = new User();
        employee.setName("Demo Ronak");
        employee.setEmail("DemoRonakYadav@example.com");
        employee.setGender("male");
        employee.setStatus("active");
        APIResponse res = request.post("/public/v2/users", RequestOptions.create()
                .setData(employee));
        System.out.println("Response body : "+res.text());
        JsonNode obj = mapper.readTree(res.text());
        id = Integer.parseInt(String.valueOf(obj.get("id")));
        Assert.assertEquals(res.status(), 201, "User not created.");
        Thread.sleep(2000);
    }

    @Test(priority = 2, enabled = true)
    public void deleteUser() {
        APIResponse res = request.delete("/public/v2/users/"+id);
        Assert.assertEquals(res.status(), 204, "User not found");
        System.out.println("User deleted Successfully.");
    }
}
