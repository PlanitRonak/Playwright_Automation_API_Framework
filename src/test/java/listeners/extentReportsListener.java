package listeners;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;

public class extentReportsListener implements ITestListener {
    public ExtentSparkReporter sparkReporter;
    public ExtentReports extent;
    public ExtentTest test;

    String FileName;

    public void onStart(ITestContext context) {
        Properties prop = null;
        try {
            prop = new Properties();
            FileInputStream inputStream = new FileInputStream(System.getProperty("user.dir") + "/src/test/java/config/config.properties");
            prop.load(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }

        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
        FileName = prop.getProperty("ProjectName") + timeStamp + ".html";

        sparkReporter = new ExtentSparkReporter(".\\ExtentReports\\" + prop.getProperty("ProjectName") + "\\" + FileName);

        sparkReporter.config().setDocumentTitle(prop.getProperty("ProjectName") + " Automation Report");
        sparkReporter.config().setReportName(prop.getProperty("ProjectName") + " API Testing");
        sparkReporter.config().setTheme(Theme.STANDARD);

        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);

        extent.setSystemInfo("API Name", prop.getProperty("ProjectName"));
        extent.setSystemInfo("Module", "Admin");
        extent.setSystemInfo("Sub Module", "Customers");
        extent.setSystemInfo("User Name", prop.getProperty("TesterName"));
        extent.setSystemInfo("Environment", "QA");

        List<String> groupsList = context.getCurrentXmlTest().getIncludedGroups();
        if (!groupsList.isEmpty()) {
            extent.setSystemInfo("Groups", groupsList.toString());
        }
    }

    @Override
    public void onTestStart(ITestResult result) {

    }

    public void onTestSuccess(ITestResult result) {
        test = extent.createTest(result.getTestClass().getName());
        test.assignCategory(result.getMethod().getGroups());// Displaying Group Name
        test.log(Status.PASS, "Test Case Passes is : "+result.getName());
    }

    public void onTestFailure(ITestResult result) {
        test = extent.createTest(result.getTestClass().getName());
        test.assignCategory(result.getMethod().getGroups());

        test.log(Status.FAIL, "Test Case Failed is : "+result.getName());
        test.log(Status.INFO, result.getThrowable().getMessage());

        System.out.println("FAILED TEST CASE : "
                + result.getName());

        System.out.println("FAILED CLASS : "
                + result.getTestClass().getName());

        System.out.println("EXCEPTION : "
                + result.getThrowable());
    }

    public void onTestSkipped(ITestResult result) {
        test = extent.createTest(result.getTestClass().getName());
        test.assignCategory(result.getMethod().getGroups());
        test.log(Status.SKIP, "Test Case Skipped is : "+result.getName());
        test.log(Status.INFO, result.getThrowable().getMessage());
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {

    }

    public void onFinish(ITestContext context) {
        System.out.println("FAILED TEST COUNT : " + context.getFailedTests().size());
        extent.flush();
    }
}
