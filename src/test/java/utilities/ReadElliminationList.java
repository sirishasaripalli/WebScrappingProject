package utilities;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import java.io.File;


public class ReadElliminationList {
	

		public  void main(String[] args) throws IOException {
			String filename = "/Users/parikshit/Downloads/LCHFEllimanator.xlsx";


			ArrayList<String> ingredients = readColumnFromExcel(filename, 0); // read column 1
			System.out.println(ingredients);
		}

		public static ArrayList<String> readColumnFromExcel(String filename, int columnNum) throws IOException {
			ArrayList<String> columnData = new ArrayList<>();
			FileInputStream inputStream = new FileInputStream(new File(filename));
			Workbook workbook = WorkbookFactory.create(inputStream);
			Sheet sheet = workbook.getSheetAt(0);

			int rowIndex = 0;
			for (Row row : sheet) {
				if (rowIndex >= 2)
				{
					Cell cell = row.getCell(columnNum);
					if (cell != null && cell.getCellType() == CellType.STRING) {
						columnData.add(cell.getStringCellValue());
					}
				}
				rowIndex++;
			}

			workbook.close();
			inputStream.close();

			return columnData;
		}

}

