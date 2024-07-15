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
        String path ="/Users/gowthami/git/WebScrappingProject/test-output/Webscraping.xlsx";
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
        createHeaderRow(sheet);

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
        String path ="/Users/gowthami/git/WebScrappingProject/test-output/AllergyData.xlsx";
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
        createHeaderRow(sheet);

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
    	private void createHeaderRow(XSSFSheet sheet) {
		
	    XSSFRow headerRow = sheet.createRow(0);
	    headerRow.createCell(0).setCellValue("RecipeID");
	    headerRow.createCell(1).setCellValue("Recipe Name");
	    headerRow.createCell(2).setCellValue("RecipeCategory");
	    headerRow.createCell(3).setCellValue("foodCategory");
	    headerRow.createCell(4).setCellValue("ingredients");
	    headerRow.createCell(5).setCellValue("preparationTime");
	    headerRow.createCell(6).setCellValue("cookingTime");
	    headerRow.createCell(7).setCellValue("TAG");
	    headerRow.createCell(8).setCellValue("NoOfServings");
	    headerRow.createCell(9).setCellValue("Cuisine Category");
	    headerRow.createCell(10).setCellValue("RecipeDescription");
	    headerRow.createCell(11).setCellValue("PreparationMethod");
	    headerRow.createCell(12).setCellValue("NutrientValue");
	    headerRow.createCell(13).setCellValue("Recipe Url");
	    
	}
    
    
}

