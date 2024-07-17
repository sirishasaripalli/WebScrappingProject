package utilities;

import org.apache.poi.xssf.usermodel.*;
import java.io.*;

public class WriteExcel {
	public XSSFWorkbook workbook;
	public FileOutputStream fo;
	public FileInputStream fi;
	public XSSFSheet sheet;
	public XSSFRow row;
	public XSSFCell cell;

	public void setCellData(String sheetName, int rownum, int column, String data) throws IOException {
		String path = "C:\\Users\\galis\\git\\WebScrappingProject\\Scraping.xlsx";
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
		String path = "C:\\Users\\galis\\git\\WebScrappingProject\\Allergy.xlsx";
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

		String[] headers = { "RECIPE-ID", "RECIPE-NAME", "RECIPE-CATEGORY", "FOOD-CATEGORY", "INGREDIENTS",
				"PREPARATION-TIME", "COOKING-TIME", "TAG", "NO-OF-SERVINGS", "CUISINE-CATEGORY", "RECIPE-DESCRIPTION",
				"PREPARATION-METHOD", "NUTRIENT-VALUE", "RECIPE-URL" };
		XSSFRow headerRow = sheet.createRow(0);
		for (int i = 0; i < headers.length; i++) {
			headerRow.createCell(i).setCellValue(headers[i]);
		}

	}

}
