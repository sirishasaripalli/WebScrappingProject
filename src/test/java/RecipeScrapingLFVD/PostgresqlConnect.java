package RecipeScrapingLFVD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PostgresqlConnect {
    
    private final static String url = "jdbc:postgresql://localhost:5432/postgres";
    private final static String user = "postgres";
    private final static String password = "postgres";
    
    public static void main(String[] args) {
        try{
            Connection connection = DriverManager.getConnection(url, user, password);
            if (connection!= null) {
                System.out.println("Connected successfully!");
            }
            else {
                System.out.println("Failed to connect.");
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
