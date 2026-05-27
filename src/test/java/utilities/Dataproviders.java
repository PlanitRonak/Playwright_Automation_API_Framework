package utilities;

import baseTest.baseApiTest;
import org.testng.annotations.DataProvider;
import pojo.User;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Dataproviders {
    private static Properties prop = new Properties();
    private static String fileName;
    private static String excelFilePath;
    private static String sheetName;
    // static block loads config once
    static {
        try {
            FileInputStream ip = new FileInputStream(System.getProperty("user.dir") + "/src/test/java/config/config.properties");
            prop.load(ip);
            fileName = prop.getProperty("excelFileName");
            excelFilePath = System.getProperty("user.dir")+"\\src\\test\\resources\\testData\\"+fileName;
            sheetName = prop.getProperty("excelSheetName");
        } catch (IOException e) {
            throw new RuntimeException("Failed to load config.properties file.", e);
        }
    }

    @DataProvider(name = "createUsers")
    public Object[] users() throws IOException {
        excelUtility excel = new excelUtility(excelFilePath, sheetName);
        int rowCount = excel.getRowCount();
        User[] users = new User[rowCount - 1];
        for (int i = 0 ; i < rowCount - 1 ; i++) {
            User user = new User();
            user.setName(excel.getCellValue(i + 1, 0));
            user.setEmail(excel.getCellValue(i + 1, 1));
            user.setGender(excel.getCellValue(i + 1, 2));
            user.setStatus(excel.getCellValue(i + 1, 3));
            users[i] = user;
        }
        return users;
    }
}