package utilities;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;

public class WriteExcel {
    public XSSFWorkbook workbook;
    public FileOutputStream fo;
    public FileInputStream fi;
    public XSSFSheet sheet;
    public XSSFRow row;
    public XSSFCell cell;

    public void setCellData(String sheetName, int rownum, int column, String data) throws IOException {
        String path ="C:\\Users\\kollu\\eclipse-workspace\\WebScrappingProject\\src\\test\\resources\\Data\\outPutData.xlsx";
        File xlfile = new File(path);
        if (!xlfile.exists()) {
            workbook = new XSSFWorkbook();
            fo = new FileOutputStream(path);
            workbook.write(fo);
        }
        fi = new FileInputStream(path);
        workbook = new XSSFWorkbook(fi);

        if (workbook.getSheetIndex(sheetName) == -1)
            workbook.createSheet(sheetName);
        sheet = workbook.getSheet(sheetName);

        if (sheet.getRow(rownum) == null)
            sheet.createRow(rownum);
        row = sheet.getRow(rownum);

        cell = row.createCell(column);
        cell.setCellValue(data);
        fo = new FileOutputStream(path);
        workbook.write(fo);
        workbook.close();
        fi.close();
        fo.close();
    }
    
    public void setCellAllergyData(String sheetName, int rownum, int column, String data) throws IOException {
        String path ="C:\\\\Users\\valli\\Git\\Recipe_Scraping_Hackathon_April2023_Data_Scrapers_T15\\src\\test\\resources\\AllergyData.xlsx";
        File xlfile = new File(path);
        if (!xlfile.exists()) {
            workbook = new XSSFWorkbook();
            fo = new FileOutputStream(path);
            workbook.write(fo);
        }
        fi = new FileInputStream(path);
        workbook = new XSSFWorkbook(fi);

        if (workbook.getSheetIndex(sheetName) == -1)
            workbook.createSheet(sheetName);
        sheet = workbook.getSheet(sheetName);

        if (sheet.getRow(rownum) == null)
            sheet.createRow(rownum);
        row = sheet.getRow(rownum);

        cell = row.createCell(column);
        cell.setCellValue(data);
        fo = new FileOutputStream(path);
        workbook.write(fo);
        workbook.close();
        fi.close();
        fo.close();
    }
}

