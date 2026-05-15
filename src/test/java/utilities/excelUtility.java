package utilities;

import baseTest.baseApiTest;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class excelUtility {
    private static FileInputStream fi;
    private static XSSFWorkbook wb;
    private static XSSFSheet sh;
    private static XSSFRow ro;
    private static XSSFCell ce;
    String fileName;
    String sheetName;

    public excelUtility(String fileName, String sheetName) {
        this.fileName = fileName;
        this.sheetName = sheetName;
    }

    public int getRowCount() throws IOException {
        fi = new FileInputStream(fileName);
        wb = new XSSFWorkbook(fi);
        sh = wb.getSheet(sheetName);
        int rowCount = sh.getLastRowNum();
        wb.close();
        fi.close();
        return rowCount + 1;
    }

    public int getCellCount(int rowNum) throws IOException {
        fi = new FileInputStream(fileName);
        wb = new XSSFWorkbook(fi);
        sh = wb.getSheet(sheetName);
        ro = sh.getRow(rowNum);
        int cellCount = ro.getLastCellNum();
        wb.close();
        fi.close();
        return cellCount;
    }

    public String getCellValue(int rowNum, int cellNum) throws IOException {
        fi = new FileInputStream(fileName);
        wb = new XSSFWorkbook(fi);
        sh = wb.getSheet(sheetName);
        ro = sh.getRow(rowNum);
        ce = ro.getCell(cellNum);
        String value;
        try {
            DataFormatter formate = new DataFormatter();
            value = formate.formatCellValue(ce);
        } catch (Exception e) {
            value = " ";
        }
        wb.close();
        fi.close();
        return value;
    }
}
