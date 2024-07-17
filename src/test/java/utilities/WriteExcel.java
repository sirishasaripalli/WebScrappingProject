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
        String path ="C:\\Users\\galis\\git\\WebScrappingProject\\Scraping.xlsx";
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
        String path ="C:\\Users\\galis\\git\\WebScrappingProject\\Allergy.xlsx";
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
    	
    	String[] headers = {
    			"RECIPE-ID", "RECIPE-NAME", "RECIPE-CATEGORY", "FOOD-CATEGORY", "INGREDIENTS",
    			"PREPARATION-TIME", "COOKING-TIME", "TAG", "NO-OF-SERVINGS", "CUISINE-CATEGORY",
    			"RECIPE-DESCRIPTION", "PREPARATION-METHOD", "NUTRIENT-VALUE", "RECIPE-URL"
    		};
    		XSSFRow headerRow = sheet.createRow(0); // Assuming ‘sheet’ is your Sheet object
    		for (int i = 0; i < headers.length; i++) {
    		    headerRow.createCell(i).setCellValue(headers[i]);
    		}
    
    
}
    
    
//    	private void createHeaderRow(XSSFSheet sheet) {
//		
//	    XSSFRow headerRow = sheet.createRow(0);
//	    headerRow.createCell(0).setCellValue("RECIPE-ID");
//	    headerRow.createCell(1).setCellValue("RECIPE-NAME");
//	    headerRow.createCell(2).setCellValue("RECIPE-CATEGORY");
//	    headerRow.createCell(3).setCellValue("FOOD-CATEGORY");
//	    headerRow.createCell(4).setCellValue("INGREDIENTS");
//	    headerRow.createCell(5).setCellValue("PREPARATION-TIME");
//	    headerRow.createCell(6).setCellValue("COOKING-TIME");
//	    headerRow.createCell(7).setCellValue("TAG");
//	    headerRow.createCell(8).setCellValue("NO-OF-SERVINGS");
//	    headerRow.createCell(9).setCellValue("CUISINE-CATEGORY");
//	    headerRow.createCell(10).setCellValue("RECIPE-DESCRIPTION");
//	    headerRow.createCell(11).setCellValue("PREPARATION-METHOD");
//	    headerRow.createCell(12).setCellValue("NUTRIENT-VALUE");
//	    headerRow.createCell(13).setCellValue("RECIPE-URL");
//	    
//	}
    
    
}

