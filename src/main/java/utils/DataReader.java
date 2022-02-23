package utils;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;

/**
 * This class reads the excel file containing test cases and their run status
 *
 * @author codeexpert3
 */
public class DataReader {

    public static HashMap<String, HashMap<String, String>> testSheetData = new HashMap<String, HashMap<String, String>>();
    public static HashMap<String, HashMap<String, HashMap<String, String>>> testData = new HashMap<String, HashMap<String, HashMap<String, String>>>();

    private static DataFormatter formatter = new DataFormatter();
    private static Logger log = Logger.getLogger(BaseClass.class.getSimpleName());
    public static int totalRowCount;

    public static void initiateData() throws IOException {
        log.info("**Reading Test Case excel Sheet**");
        int numberOfSheets;
        File file = new File("." + File.separator + "testdata" + File.separator + "datasheet.xlsx");
        FileInputStream inputStr = new FileInputStream(file);
        XSSFWorkbook workbook = new XSSFWorkbook(inputStr);
        numberOfSheets = workbook.getNumberOfSheets();
        log.debug("Number of sheets ---- "+numberOfSheets);
        for (int i = 0; i < numberOfSheets; i++) {
            XSSFSheet sheet = workbook.getSheetAt(i);
            testSheetData = readDataSheet(sheet);
            testData.put(workbook.getSheetName(i), testSheetData);
        }
      log.debug(testData);

    }

    public static HashMap<String, HashMap<String, String>> readDataSheet(Sheet sheet) {
        HashMap<String, HashMap<String, String>> data = new HashMap<String, HashMap<String, String>>();
        for (int rowNumber = 1; rowNumber <= sheet.getLastRowNum(); rowNumber++) {
            data.put(formatter.formatCellValue(sheet.getRow(rowNumber).getCell(0)),
                    getRowData(sheet, rowNumber));
        }
        totalRowCount += sheet.getLastRowNum();
                return data;
    }

    public static HashMap<String, String> getRowData(Sheet sheet, int rowNumber) {
        Row headerRow = sheet.getRow(0);
        HashMap<String, String> rowData = new HashMap<String, String>();
        for (int columnNumber = 0; columnNumber < sheet.getRow(rowNumber)
                .getLastCellNum(); columnNumber++) {

                rowData.put(formatter.formatCellValue(headerRow.getCell(columnNumber)), formatter.formatCellValue(sheet
                        .getRow(rowNumber).getCell(columnNumber)));

        }
        return rowData;
    }
}