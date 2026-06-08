package listeners;

import org.apache.log4j.PropertyConfigurator;
import org.testng.IExecutionListener;

import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;


public class loggerListener implements IExecutionListener {
    @Override
    public void onExecutionStart() {
        try {
            Properties prop = new Properties();
            prop.load(new FileInputStream(System.getProperty("user.dir")+"\\src\\test\\java\\config\\config.properties"));

            String timestamp = new SimpleDateFormat("yyyy_MM_dd_HH-mm-ss").format(new Date());
            System.setProperty("current.date", timestamp);
            System.setProperty("projectName", prop.getProperty("ProjectName"));


            Properties props = new Properties();
            props.load(new FileInputStream(System.getProperty("user.dir")+"\\src\\test\\java\\config\\log4j.properties"));
            PropertyConfigurator.configure(props);

            System.out.println("Loaded log.filename = " + System.getProperty("projectName"));

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Could not load config.properties!");
        }
    }

    @Override
    public void onExecutionFinish() {}
}
