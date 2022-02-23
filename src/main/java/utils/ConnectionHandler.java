package utils;

import com.relevantcodes.extentreports.LogStatus;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


public class ConnectionHandler extends BaseClass {
    public static Connection rdsConn;
    private static Statement rdsStmt;
    private static Logger log;
    public static File directory = new File(".");

    public ConnectionHandler() {
    }


    public static Statement connectToRds() {
        try {
            PropertyConfigurator
                    .configure(directory.getCanonicalPath() + File.separator + "log4j.properties");
            log = Logger.getLogger(ConnectionHandler.class.getSimpleName());
            log.info("Making connection with RDS");

            Class.forName(Util.getConstantProperty("mysql.class.name"));
            String url = Util.getConstantProperty("mysql.db.connection.url");

            String userName = CredentialConstant.get_mysql_user_name().trim();
            String password = CredentialConstant.get_mysql_password().trim();

           rdsConn = DriverManager.getConnection(url, userName, password);

            rdsStmt = rdsConn.createStatement();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            log.error("Error connecting to MySql  :\n" + e);

        } catch (SQLException se) {
            se.printStackTrace();
            log.error("Error connecting to MySql  :\n" + se);
            testReport.log(LogStatus.ERROR,se.getMessage());

        } catch (IOException e) {
            e.printStackTrace();
        }
        return rdsStmt;

    }

    public static void closeConnection() {
        log.info("Closing Db connections");
        if (null != rdsStmt || null != rdsConn) {
            try {
                rdsStmt.close();
                rdsConn.close();
                log.info("My SQL connection closed");
            } catch (SQLException e) {
                e.printStackTrace();
                log.error(e);
                testReport.log(LogStatus.ERROR,e.getMessage());
            }
        }
    }

}
