package com.psc.dmg.ingestion;

import com.relevantcodes.extentreports.LogStatus;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import utils.BaseClass;
import utils.Constants;
import utils.IngestionUtil;
import utils.Util;

import java.util.Map;

@Test
public class TestIngestion extends BaseClass {
    private static Logger log = LogManager.getLogger(TestIngestion.class);
    static Map<String, String> suiteParams;

    @Test(dataProvider = "getTableList")
    private void testIngestion(String tableName, String runStatus) {

        String sTableName = tableName;
        if (runStatus.equalsIgnoreCase(Constants.TRUE)) {
            suiteParams = mContext.getCurrentXmlTest().getSuite().getParameters();
            String schemaName =testData.get(suiteParams.get(Constants.SHEET_NAME)).get(tableName).get(Constants.RDSSchemaName);
            String TableName= testData.get(suiteParams.get(Constants.SHEET_NAME)).get(tableName).get(Constants.TABLE_NAME);
            IngestionUtil.testTableRowCount(schemaName,TableName);
            }

         else {
        testReport.log(LogStatus.SKIP, "Skipping test for table: " + sTableName);
        }
    }


    @DataProvider
    private Object[][] getTableList() {
        Object[][] arrayObject = null;
        log.info(mContext.getCurrentXmlTest());
        arrayObject = Util.checkRunStatus("RecordCount",
                mContext.getCurrentXmlTest().getSuite().getParameter("sheet_name"));
        return arrayObject;
    }


}
