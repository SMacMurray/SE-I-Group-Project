package stay_and_shop_system;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    // private static final String DB_URL = "jdbc:mysql://localhost:3306/yourdb";
//    private static final String DB_URL = "jdbc:mysql://localhost:3306/yummers";
    private static final String DB_URL = "jdbc:sqlite:hotelSystem";
//    private static final String USER = "root";
//    private static final String PASS = "ch@rl1eK1rk#GoD";
    // New user: newUser
    // New password: p0p-lock3@d
    // public static void main( String[] args ) {



        public static Connection connect() {
                Connection conn = null;
                try {
                    // Register the driver (optional for modern JDBC 4.0+)
                    //Class.forName("com.mysql.cj.jdbc.Driver");

                    conn = DriverManager.getConnection(DB_URL);
                    System.out.println("Connected to the database!");
                } catch (SQLException e) {
                    System.out.println("Connection failed! Make sure to include the my-sql-connector jar.");
                    e.printStackTrace();
                }
                return conn;



    }
}