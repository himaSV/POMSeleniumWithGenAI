package genai.app.Utils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class reporter {
    private static ExtentReports extent;

    public static ExtentReports getExtent() {
        if (extent == null) {
            String ts = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String reportDir = System.getProperty("user.dir") + File.separator + "reports" + File.separator + ts;
            new File(reportDir).mkdirs();
            ExtentSparkReporter spark = new ExtentSparkReporter(reportDir + File.separator + "extent.html");
            spark.config().setTheme(Theme.STANDARD);
            spark.config().setDocumentTitle("Automation Report");

            extent = new ExtentReports();
            extent.attachReporter(spark);
            extent.setSystemInfo("OS", System.getProperty("os.name"));
            extent.setSystemInfo("User", System.getProperty("user.name"));
        }
        return extent;
    }

    public static void flush() {
        if (extent != null) extent.flush();
    }
}