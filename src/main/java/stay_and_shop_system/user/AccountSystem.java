package stay_and_shop_system.user;

import stay_and_shop_system.DatabaseConnection;

import java.io.*;
import java.sql.*; // Imports SQLException
import java.util.UUID;

public class AccountSystem {
	// Use this to indicate what user is currently logged in, or null for logged out.
	public static User SessionAccount = null; // Change this to private when we've fixed the legacy code.
	static final String DatabaseURL = "jdbc:sqlite:./data.db";
	static final String DB_NOT_FOUND = "Critical error: a connection could not be establish with the database.";

	public static User getSessionAccount(){ return SessionAccount; }


	// Used to create the account table
	public static void initAccountTable(){
		String sql = """
			CREATE TABLE IF NOT EXISTS Accounts (
			email text PRIMARY KEY,
			name text NOT NULL,
			password int NOT NULL,
			phoneNumber text NOT NULL,
			paymentId text NOT NULL,
			priveleges int NOT NULL 
			)
			"""; // 0: guest, 1: clerk, 2: admin
		try (var connection = DriverManager.getConnection(DatabaseURL)) {
			System.out.println("Connection to SQLite has been established.");
			connection.createStatement().execute(sql);
			System.out.println("Account table successfully created.");
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}

	// Creates a DB connection and initializes an Accounts table if it doesn't exist.
	static java.sql.Connection connectToDatabase() throws SQLException {
		String sql = """
			SELECT name FROM sqlite_master WHERE type='table' AND name='Accounts'
			""";
		java.sql.Connection connection = DriverManager.getConnection(DatabaseURL);
		System.out.println("Connection to SQLite has been established.");
		ResultSet res = connection.createStatement().executeQuery(sql);
		System.out.println(res);
		if (!res.next()) {
			AccountSystem.initAccountTable();
			System.out.println("WARNING: a new account table has been initialized.");
		}
		return connection;
	}

	// Confirms whether a given user exists in the system.
	static boolean findAccount(String email) {
		String sql = """
			SELECT EXISTS(SELECT 1 FROM Accounts WHERE email = ?)
			""";
		try (var connection = connectToDatabase();
			 var stmt = connection.prepareStatement(sql)) {
			stmt.setString(1, email);
			ResultSet res = stmt.executeQuery();
			if (res.next()) {
				return res.getBoolean(1);
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		return false;
	}

	// Deletes an account by email
	static void deleteAccount(String email) {
		String sql = """
			DELETE FROM Accounts where email = ?
			""";
		try (var connection = connectToDatabase();
		     var stmt = connection.prepareStatement(sql)) {
			stmt.setString(1, email);
			stmt.execute();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}

	// Handles account creation for guests alone.
	static boolean createAccount(String email, String username, int passwordHash, String phoneNumber){
		String sql = """
				INSERT INTO Accounts (email, name, password, phoneNumber, paymentId, priveleges)
				VALUES (?,?,?,?,?,0)
				RETURNING *
				""";
		try (var connection = connectToDatabase();
			 var stmt = connection.prepareStatement(sql)) {
			stmt.setString(1, email);
			stmt.setString(2, username);
			stmt.setInt(3, passwordHash);
			stmt.setString(4, phoneNumber);
			stmt.setString(5, UUID.randomUUID().toString());
			ResultSet res = stmt.executeQuery();
			return setSessionAccount(res);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		return false;
	}

	// Validates a sign in, and sets SessionAccount if successful.
	static boolean authenticate(String email, int passwordHash){
		String sql = """
			SELECT * FROM Accounts WHERE email = ? and password = ?
			""";
		try (var connection = connectToDatabase();
			 var stmt = connection.prepareStatement(sql)){
			stmt.setString(1, email);
			stmt.setInt(2, passwordHash);
			ResultSet res = stmt.executeQuery();
			return setSessionAccount(res);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		return false;
	}

	// Sets SessionAccount. Change the switch cases if you want to change what type of account gets assigned.
	static boolean setSessionAccount(ResultSet res) throws SQLException {
		if (res == null){
			AccountSystem.SessionAccount = null;
			return true;
		}
		if (res.next()) {
			switch (res.getInt("priveleges")){
				case 1:
					AccountSystem.SessionAccount = new GuestClerk(
							res.getString(1),
							res.getString(2),
							res.getInt(3),
							res.getString(4),
							res.getString(5)
					);
					break;
				case 2:
					AccountSystem.SessionAccount = new GuestAdmin(
							res.getString(1),
							res.getString(2),
							res.getInt(3),
							res.getString(4),
							res.getString(5)
					);
					break;
				default:
					AccountSystem.SessionAccount = new Guest(
							res.getString(1),
							res.getString(2),
							res.getInt(3),
							res.getString(4),
							res.getString(5)
					);
					break;
			}
			return true;
		}
		return false;
	}







	// Defunct
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
