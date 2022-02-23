package utils;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import java.util.TimeZone;


public class Util extends BaseClass {

    private static Logger log = LogManager.getLogger(Util.class);


    public static String getConstantProperty(String key) {
        Properties properties = new Properties();

        try {
            File file = null;
            file = new File("src/main/resources/psc-constants.properties");
            setCredentials();
            FileInputStream fileInput = new FileInputStream(file);
            properties.load(fileInput);
            fileInput.close();
        }
         catch (FileNotFoundException var4) {
            var4.printStackTrace();
        } catch (IOException var5) {
            var5.printStackTrace();
        }

        return properties.getProperty(key);
    }


    public static String getCurrentDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yy");
        Calendar.getInstance().getTimeZone().getID();
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+3"));
        Date date = new Date();
        return sdf.format(date);
    }

    public static String[][] checkRunStatus(String testType, String sheetName) {

        String[][] arrayExcelData = null;
        int i = 0;
        for (String phase : testData.keySet()) {
            if (phase.equalsIgnoreCase(sheetName)) {
                arrayExcelData = new String[testData.get(phase).keySet().size()][2];
                for (String tableName : testData.get(phase).keySet()) {

                    for (int j = 0; j < 2; j++) {
                        if (j == 0) {
                            arrayExcelData[i][j] = tableName;
                        } else {
                            arrayExcelData[i][j] = testData.get(phase).get(tableName).get(testType);
                        }
                    }
                    i++;
                }
            }
        }
        return arrayExcelData;
    }



    private static void setCredentials() {



        CredentialConstant.set_mysql_user_name(System.getenv("MYSQL_USER_NAME_PROD"));
        CredentialConstant.set_mysql_password(System.getenv("MYSQL_PASSWORD_PROD"));
        CredentialConstant.set_test_report_username(System.getenv("TEST_REPORT_SENDER_USERNAME"));
        CredentialConstant.set_test_report_password(System.getenv("TEST_REPORT_SENDER_PASSWORD"));

    }
}
