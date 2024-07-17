package utilities;


import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellUtil;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.*;

public class writeToDatabase {

    private final static String url = "jdbc:postgresql://localhost:5432/SCRAPPING";
    private final static String user = "postgres";
    private final static String password = "postgres";
    
	public static void insertData(int inputDataSheet, String inputTableName) throws EncryptedDocumentException, IOException {
		//System.out.println("test");
		//Read Excel file and insert data to the table
		
		System.out.println("This is DB");
		String excelFilePath = "C:\\Users\\kollu\\git\\WebScrappingProject\\src\\test\\resources\\Data\\outPutData.xlsx";
		//String sqlInsert = "INSERT INTO public.lfv(recipe_id, recipe_name, recipe_category, food_category, ingredients, preparation_time, cooking_time, tag, no_of_servings, cuisine_category, recipe_description, preparation_method, nutrient_values, recipe_url) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
        int rowsInserted = 0;
        
		  try {
			  Connection conn = DriverManager.getConnection(url, user, password);
        		System.out.println("DB connected");
        		
        		FileInputStream excelFile = new FileInputStream(excelFilePath);
                Workbook workbook = WorkbookFactory.create(excelFile);

                // Assuming first sheet is the one to be read
                Sheet sheet = workbook.getSheetAt(inputDataSheet);
                
                	String sqlInsert = "INSERT INTO public." + inputTableName + "(recipe_id, recipe_name, recipe_category, food_category, ingredients, preparation_time, cooking_time, tag, no_of_servings, cuisine_category, recipe_description, preparation_method, nutrient_values, recipe_url) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
            	
                	//String sqlInsert = "INSERT INTO public.lfv(recipe_id, recipe_name, recipe_category, food_category, ingredients, preparation_time, cooking_time, tag, no_of_servings, cuisine_category, recipe_description, preparation_method, nutrient_values, recipe_url) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
                	PreparedStatement statement = conn.prepareStatement(sqlInsert);

                	for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
                		//Read Excel sheet row by row
                        Row row = sheet.getRow(rowIndex);
                        DataFormatter dd = new DataFormatter();
                        String column1Value = dd.formatCellValue(CellUtil.getCell(row, 0));
                        String column2Value = dd.formatCellValue(CellUtil.getCell(row, 1));
                        String column3Value = dd.formatCellValue(CellUtil.getCell(row, 2));
                        String column4Value = dd.formatCellValue(CellUtil.getCell(row, 3));
                        String column5Value = dd.formatCellValue(CellUtil.getCell(row, 4));
                        String column6Value = dd.formatCellValue(CellUtil.getCell(row, 5));
                        String column7Value = dd.formatCellValue(CellUtil.getCell(row, 6));
                        String column8Value = dd.formatCellValue(CellUtil.getCell(row, 7));
                        String column9Value = dd.formatCellValue(CellUtil.getCell(row, 8));
                        String column10Value = dd.formatCellValue(CellUtil.getCell(row, 9));
                        String column11Value = dd.formatCellValue(CellUtil.getCell(row, 10));
                        String column12Value = dd.formatCellValue(CellUtil.getCell(row, 11));
                        String column13Value = dd.formatCellValue(CellUtil.getCell(row, 12));
                        String column14Value = dd.formatCellValue(CellUtil.getCell(row, 13));
                       
                        // Set the column values for database insert
                        statement.setString(1, column1Value);
                        statement.setString(2, column2Value);
                        statement.setString(3, column3Value);
                        statement.setString(4, column4Value);
                        statement.setString(5, column5Value);
                        statement.setString(6, column6Value);
                        statement.setString(7, column7Value);
                        statement.setString(8, column8Value);
                        statement.setString(9, column9Value);
                        statement.setString(10, column10Value);
                        statement.setString(11, column11Value);
                        statement.setString(12, column12Value);
                        statement.setString(13, column13Value);
                        statement.setString(14, column14Value);
                        
                        statement.executeUpdate();
                        rowsInserted = rowsInserted + 1;
                    }
                    System.out.println("Rows inserted: " + rowsInserted);  
            }
		  catch (SQLException e) {
                e.printStackTrace();
		  }
		}
	}