package utils;

import com.relevantcodes.extentreports.LogStatus;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.mortbay.log.Log;

import java.sql.ResultSet;
import java.sql.SQLException;

public class IngestionUtil extends BaseClass {

    private static boolean isEqual = true;
    private static ResultSet rdsResultSet = null;

    private static Logger log = LogManager.getLogger(IngestionUtil.class);


    public static void testTableRowCount( String RDSSchemaName, String tableName) {
        log.info("---- TEST CASE :  COUNT for TABLE: " + tableName + "----");
        testReport.log(LogStatus.INFO, "**** TEST CASE  COUNT for TABLE: " + tableName + "****");

        int RDSTableRowCount = 0;

        try {

            String rdsQuery = "Select count(*) from "+ RDSSchemaName + "." + tableName + "";

            Log.info("RDS Query for Row Data Count : " + rdsQuery);
            testReport.log(LogStatus.INFO, "RDS Query: " + rdsQuery);
            rdsResultSet = rdsStmt.executeQuery(rdsQuery);

            while (rdsResultSet.next()) {

                RDSTableRowCount = Integer.parseInt(rdsResultSet.getString(1));

                log.info("Row Count for rds table: " + tableName + " :" + rdsResultSet.getString(1));
            }

        } catch (SQLException e) {
            log.error(e);
            testReport.log(LogStatus.ERROR, "Table Name: " + tableName, e.getMessage());
            e.printStackTrace();
        } catch (Exception ex) {
            log.error(ex);
            testReport.log(LogStatus.ERROR, "Table Name: " + tableName, ex.getMessage());
            ex.printStackTrace();
        }
    }


}