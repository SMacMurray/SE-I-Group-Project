import java.sql.*; // Imports SQLException

public class AccountSystem {
	static boolean searchDatabase(String user, String pass) throws SQLException {
		boolean found;
		
		Connection conn = DatabaseConnection.connect(); // Connecting to Database
		Statement stat = conn.createStatement(); // Creating SQL statement
		// Format: String query = "SELECT (column) FROM (table) WHERE (condition)";
		String query = "SELECT * FROM Users WHERE Username = \'" + user + 
						"\' AND Password = \'" + pass + "\'";
		ResultSet rs = stat.executeQuery(query); // Making a Query using the string above.
		
		found = rs.next(); // Going to the first row. Returns false if there is no data."
		
		return found;
	}
}
