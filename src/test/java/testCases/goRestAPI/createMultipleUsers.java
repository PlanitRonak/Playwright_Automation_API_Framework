package testCases.goRestAPI;

import baseTest.baseApiTest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.options.RequestOptions;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pojo.User;
import utilities.Dataproviders;

import java.util.ArrayList;
import java.util.Arrays;

public class createMultipleUsers extends baseApiTest {
    private static ArrayList<Integer> ids = new ArrayList<>();

    @Test(priority = 0, dataProviderClass = Dataproviders.class, dataProvider = "createUsers")
    public void multipleUsers(User users) throws JsonProcessingException, InterruptedException {
        System.out.println("Name : "+users.getName());
        System.out.println("Email : "+users.getEmail());
        System.out.println("Gender : "+users.getGender());
        System.out.println("Status : "+users.getStatus());
        Thread.sleep(4000);
        APIResponse res = request.post("/public/v2/users", RequestOptions.create()
                .setHeader("Authorization","Bearer "+prop.getProperty("token"))
                .setData(users));
        System.out.println("Response body : "+res.text());
        JsonNode obj = mapper.readTree(res.text());
        ids.add(Integer.parseInt(String.valueOf(obj.get("id"))));
        Assert.assertEquals(res.status(), 201, "User Not Created.");
    }

    @Test(priority = 1, dependsOnMethods = "multipleUsers")
    public void deleteUsers() throws InterruptedException {
        for (int i = 0 ; i < ids.size() ; i++) {
            APIResponse res = request.delete("/public/v2/users/"+ids.get(i), RequestOptions.create()
                    .setHeader("Authorization","Bearer "+prop.getProperty("token")));
            Assert.assertEquals(res.status(), 204, "User not found");
            System.out.println("User deleted Successfully.");
            Thread.sleep(4000);
        }
    }
}
