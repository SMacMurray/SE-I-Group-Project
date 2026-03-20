import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    // private static final String DB_URL = "jdbc:mysql://localhost:3306/yourdb";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/yummers";
    private static final String USER = "root";
    private static final String PASS = "ch@rl1eK1rk#GoD";
    // public static void main( String[] args ) {



        public static Connection connect() {
                Connection conn = null;
                try {
                    // Register the driver (optional for modern JDBC 4.0+)
                    //Class.forName("com.mysql.cj.jdbc.Driver");

                    conn = DriverManager.getConnection(DB_URL, USER, PASS);
                    System.out.println("Connected to the database!");
                } catch (SQLException e) {
                    System.out.println("Connection failed!");
                    e.printStackTrace();
                }
                return conn;



    }
}