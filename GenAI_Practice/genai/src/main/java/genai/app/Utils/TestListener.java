package genai.app.Utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.ExtentReports;

import genai.app.Base.TestBase;

public class TestListener implements ITestListener {
    private static ExtentReports extent = reporter.getExtent();
    private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();

    @Override
    public void onTestStart(ITestResult result) {
        ExtentTest t = extent.createTest(result.getMethod().getMethodName());
        test.set(t);
        t.log(Status.INFO, "Test started: " + result.getMethod().getMethodName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        test.get().log(Status.PASS, "Test passed");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        test.get().log(Status.FAIL, result.getThrowable());
        // screenshot
        try {
            TakesScreenshot ts = (TakesScreenshot) TestBase.getDriver();
            File src = ts.getScreenshotAs(OutputType.FILE);
            String name = result.getMethod().getMethodName() + "_" +
                    new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()) + ".png";
            File destDir = new File(System.getProperty("user.dir") + File.separator + "reports" + File.separator + "screenshots");
            destDir.mkdirs();
            File dest = new File(destDir, name);
            Files.copy(src.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);
            test.get().addScreenCaptureFromPath(dest.getAbsolutePath());
        } catch (IOException e) {
            // ignore
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        test.get().log(Status.SKIP, "Test skipped");
    }

    @Override
    public void onFinish(ITestContext context) {
        reporter.flush();
    }

    // other methods left empty
}
