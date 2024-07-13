package utilities;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ReadExcel {
    public Map<String, String> readUserDetails () throws IOException {
        //creating an object named path where providing the path of excel sheet to get properties of the file
        String path = System.getProperty("C:\\Users\\valli\\Git\\Recipe_Scraping_Hackathon_April2023_Data_Scrapers_T15\\src\\test\\resources\\IngredientsAndComorbidities-ScrapperHackathon.xlsx");
// creating the object excelFile of the file with earlier created object(path) as a parameter
        File excelFile = new File(path);
        // FileInputStream obtains input bytes from a file in a file system here excelFile and stores in the object fileInputStream
        FileInputStream fileInputStream = new FileInputStream(excelFile);
//creates object workbook to read or write the excelsheet
        XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);
        //create an object to get the data from sheet1 of workbook
        XSSFSheet datasheet = workbook.getSheet("Sheet1");
        //Iterate the rows of sheet
        Iterator<Row> row = datasheet.rowIterator();
        //gets the details from row 0 & row 1 as K,V pair and store them as an object userdetails
        Map<String, String> userDetails = new HashMap<String, String>();
        while (row.hasNext()){
            Row currRow =row.next();
            if(currRow.getRowNum() > 0) {
                userDetails.put(currRow.getCell(0).getStringCellValue(), currRow.getCell(1).getStringCellValue());

            }
        }
        workbook.close();
        return userDetails;

    }
}
