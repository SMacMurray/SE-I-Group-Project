package stay_and_shop_system.user;

import stay_and_shop_system.DatabaseConnection;

import java.io.*;
import java.sql.*; // Imports SQLException

public class AccountSystem {
	// Use this to indicate what user is currently logged in, or null for logged out.
	public static User SessionAccount = null;
	static final String DatabaseURL = "jdbc:sqlite:./data.db";
	static final String DB_NOT_FOUND = "Critical error: a connection could not be establish with the database.";


	// Used to create the account table
	static void initAccountTable(){
		String sql = "CREATE TABLE IF NOT EXISTS Accounts ("
				+ "	name text PRIMARY KEY,"
				+ "	password int NOT NULL,"
				+ "	email text NOT NULL,"
				+ "	phoneNumber text NOT NULL,"
				+ " priveleges int NOT NULL" // 0: guest, 1: clerk, 2: admin
				+ ");";
		try (var connection = DriverManager.getConnection(DatabaseURL)) {
			System.out.println("Connection to SQLite has been established.");
			connection.createStatement().execute(sql);
			System.out.println("Account table successfully created.");
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	// Creates a DB connection and initializes an "accounts" table if it doesn't exist.
	static java.sql.Connection connectToDatabase() throws SQLException {
		String sql = "SELECT Accounts FROM sqlite_master WHERE type='table' AND name='Accounts';";
		boolean res = false;
		java.sql.Connection connection = DriverManager.getConnection(DatabaseURL);
		System.out.println("Connection to SQLite has been established.");
		res = connection.createStatement().execute(sql);
		if (!res) {
			AccountSystem.initAccountTable();
			System.out.println("WARNING: a new account table has been initialized.");
		}
		return connection;
	}

	// Confirms whether a given user exists in the system.
	static boolean findAccount(String username){
		String sql = "SELECT exists(SELECT 1 FROM users WHERE username = '"+username+"') AS row_exists;";
		boolean res = true;
		try (var connection = connectToDatabase()){
			res = connection.createStatement().execute(sql);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return res;
	}

	// Handles account creation for guests alone.
	static boolean createAccount(String username, int passwordHash, String email, String phoneNumber){
		String sql = "INSERT INTO Accounts (username, password, email, phoneNumber, priveleges)"
				+" VALUES ('"+username+"',"+passwordHash+",'"+email+"','"+phoneNumber+"',0);";
		boolean res = false;
		try (var connection = connectToDatabase()) {
			res = connection.createStatement().execute(sql);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return res;
	}

	static boolean authenticate(String username, int passwordHash){
		String sql = "SELECT exists(SELECT passwordHash FROM Accounts WHERE username = '"+username+"' and WHERE password = "+passwordHash+") AS row_exists;";
		boolean res = false;
		try (var connection = connectToDatabase()){
			res = connection.createStatement().execute(sql);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return res;
	}







	// defunct code
	static boolean searchDatabase(String user, String pass) throws SQLException {
		boolean found;
		
		Connection conn = DatabaseConnection.connect(); // Connecting to Database
		Statement stat = conn.createStatement(); // Creating SQL statement
		// Format: String query = "SELECT (column) FROM (table) WHERE (condition)";
		String query = "SELECT * FROM Users WHERE Username = \'" + user + 
						"\' AND Password = \'" + pass + "\'";
		ResultSet rs = stat.executeQuery(query); // Making a Query using the string above.
		
		found = rs.next(); // Going to the first row. Returns false if there is no data.
		
		return found;
	}
}
