package utils;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import java.mailing;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.sql.Statement;
import java.util.ArrayList;


/**
 * This class is a Base class for all tests, which contains commonly used methods
 *
 * @author codeexpert3
 */
public class BaseClass extends DataReader {


    public static File directory = new File(".");
    public static Logger log;
    public static Statement rdsStmt = null;
    public static ExtentTest testReport;
    public static String reportPath;
    public static String reportName;

    public static ExtentReports extent;

    public static ITestContext mContext;
    public static ArrayList<String> testResultCount = new ArrayList<>();
    public static StringBuffer reportMessage = new StringBuffer();


    @BeforeSuite
    public static void setup(ITestContext context) throws IOException {
        PropertyConfigurator
                .configure("./log4j.properties");
       // FileUtils.deleteDirectory(new File(directory.getCanonicalPath() + File.separator + "report_logs"));

        reportName = "Test Report - " + context.getCurrentXmlTest().getSuite().getName();
        reportPath = directory.getCanonicalPath() + File.separator + "report_logs" + File.separator
                + reportName + ".html";

    /*    String reportPath = directory.getCanonicalPath() + File.separator + "report_logs" + File.separator
                + context.getCurrentXmlTest().getSuite().getName() + "-TestReport.html";
*/
        log = Logger.getLogger(BaseClass.class);
        log.info("*****************************Creating Session***************************************************");
        mContext = context;
        rdsStmt = ConnectionHandler.connectToRds();
        extent = new ExtentReports(reportPath, true);
        try {

            String extentConfigPath = directory.getCanonicalPath() + File.separator + "extent-config.xml";
            initiateData();
            extent.loadConfig(new File(extentConfigPath));
            extent.config().reportName("Testing report name");

        } catch (IOException e) {
            e.printStackTrace();
            log.error(e);
            testReport.log(LogStatus.ERROR,e.getMessage());
        }

    }


    @BeforeMethod(alwaysRun = true)
    public void beforeMethod(Method method, Object[] testArgs,ITestContext iTestContext) {

        if (this.getClass().getSimpleName().equalsIgnoreCase("TestDumpIngestion")
                || this.getClass().getSimpleName().equalsIgnoreCase("TestIncrementalIngestion")
                || this.getClass().getSimpleName().equalsIgnoreCase("TestIngestion")) {
            String tableName = testArgs[0].toString();

            testReport = extent.startTest((method.getName()) + " :: " + tableName, method.getName());

        } else {

            testReport = extent.startTest((iTestContext.getName() + " :: " + method.getName()), method.getName());
            //testReport = extent.startTest((mContext.getCurrentXmlTest().getSuite() +" : " +"\n"+ method.getName()));
        }


    }

    @AfterMethod(alwaysRun = true)
    protected void afterMethod(ITestResult result, Method method) throws IOException {

        if (result.getStatus() == ITestResult.FAILURE) {
            testReport.log(LogStatus.FAIL, result.getThrowable().getClass().getSimpleName());
            log.error(result.getThrowable());

        } else if (result.getStatus() == ITestResult.SKIP) {

            testReport.log(LogStatus.SKIP, "Test skipped " + result.getThrowable());
        } else {
            testReport.log(LogStatus.INFO, "Test Completed");

        }

        testResultCount.add(testReport.getRunStatus().toString());

        extent.endTest(testReport);
        extent.flush();
    }

    @AfterSuite
    public static void teardown() throws MessagingException {
        ConnectionHandler.closeConnection();
        SendMailSSLWithAttachment.sendEmail();
        log.info("**********************************Ending Session**********************************************\n");


    }
}
