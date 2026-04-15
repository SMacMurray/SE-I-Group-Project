package stay_and_shop_system.user;

import stay_and_shop_system.DatabaseConnection;
import stay_and_shop_system.occupancy.Reservation;

import javax.swing.*;
import java.sql.*; // Imports SQLException
import java.util.Objects;


public class UserRepository {
	// Use this to indicate what user is currently logged in, or null for logged out.
	public static User SessionAccount = null; // Change this to private when we've fixed the legacy code.
	static final String DatabaseURL = "jdbc:sqlite:./hotelSystem.db";
	static final String DB_NOT_FOUND = "Critical error: a connection could not be establish with the database.";

	public static User getSessionAccount(){ return SessionAccount; }



	// Used to create the account table
	// Joel: Made the initAccountTable public so I could access it.
	public static void initAccountTable(){
		String sql = "CREATE TABLE IF NOT EXISTS Users ("
				+ "	email text PRIMARY KEY,"
				+ "	name text NOT NULL,"
				+ "	password int,"
				+ "	phoneNumber text NOT NULL,"
				+ "	creditCardNumber text,"
				+ "	ccv text,"
				+ "	billingAddress text,"
				+ "	expirationDate text,"
				+ " priveleges text NOT NULL" // 0: Admin, 1: Clerk, 2: GuestAdmin, 3: GuestClerk, 4: Guest
				+ ");";
		try (var connection = DatabaseConnection.connect()) {
			System.out.println("Connection to SQLite has been established.");
			connection.createStatement().execute(sql);
			System.out.println("Users table successfully created.");
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}

	public static void dropTable() {
		String sql = "DROP TABLE IF EXISTS Users";
		try (Connection connection = DatabaseConnection.connect();
				Statement stmt = connection.createStatement()) {
			stmt.execute(sql);
		} catch (SQLException e) {
			throw new RuntimeException("Failed to drop table : ReservationRepository", e);
		}
	}

//	public static void deleteAccountTable(){
//		String sql = "CREATE TABLE IF NOT EXISTS Accounts ("
//				+ "	email text PRIMARY KEY,"
//				+ "	name text NOT NULL,"
//				+ "	password int NOT NULL,"
//				+ "	phoneNumber text NOT NULL,"
//				+ " paymentId text NOT NULL,"
//				+ " priveleges int NOT NULL" // 0: guest, 1: clerk, 2: admin
//				+ ");";
//		try (var connection = DatabaseConnection.connect()) {
//			System.out.println("Connection to SQLite has been established.");
//			connection.createStatement().execute(sql);
//			System.out.println("Account table successfully created.");
//		} catch (SQLException e) {
//			System.out.println(e.getMessage());
//			e.printStackTrace();
//		}
//	}

	// Creates a DB connection and initializes an Accounts table if it doesn't exist.
	static java.sql.Connection connectToDatabase() throws SQLException {
		String sql = "SELECT name FROM sqlite_master WHERE type='table' AND name='Users'";
		java.sql.Connection connection = DatabaseConnection.connect();
		System.out.println("Connection to SQLite has been established.");
		ResultSet res = connection.createStatement().executeQuery(sql);
		System.out.println(res);
		if (!res.next()) {
			UserRepository.initAccountTable();
			System.out.println("WARNING: a new account table has been initialized.");
		}
		return connection;
	}

	// Confirms whether a given user exists in the system.
	public static boolean findAccount(String email) {
		String sql = "SELECT EXISTS(SELECT 1 FROM Users WHERE email = ? AND password <> 0)";
		try (var connection = DatabaseConnection.connect();
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

	// Finds user regardless of passwor
	public static boolean findUser(String email) {
		String sql = "SELECT EXISTS(SELECT 1 FROM Users WHERE email = ?)";
		try (var connection = DatabaseConnection.connect();
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

	// Handles account creation for guests alone.
	static boolean createAccount(String email, String username, int passwordHash, String phoneNumber){
		String sql = "INSERT INTO Users (email, name, password, phoneNumber, creditCardNumber, ccv, billingAddress, expirationDate, priveleges)"
				+" VALUES (?,?,?,?,?,?,?,?,4)"
				+" RETURNING *";
		try (var connection = DatabaseConnection.connect();
			 var stmt = connection.prepareStatement(sql)) {
			if (findUserWithoutPassword(email)) {
				deleteUser(email);
			}

			stmt.setString(1, email);
			stmt.setString(2, username);
			stmt.setInt(3, passwordHash);
			stmt.setString(4, phoneNumber);
			stmt.setString(5, null);
			stmt.setString(6, null);
			stmt.setString(7, null);
			stmt.setString(8, null);
			ResultSet res = stmt.executeQuery();
			return setSessionAccount(res);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		return false;
	}
	// Finds user regardless of passwor
	public static boolean findUserWithoutPassword(String email) {
		String sql = "SELECT EXISTS(SELECT 1 FROM Users WHERE email = ? AND password = 0)";
		try (var connection = DatabaseConnection.connect();
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


	public static boolean addGuest(GuestInterface guest) {
		String sql = "INSERT INTO Users (email, name, password, phoneNumber, creditCardNumber, ccv, billingAddress, expirationDate, priveleges)"
				+" VALUES (?,?,?,?,?,?,?,?,?)"
				+" RETURNING *";
		try (var connection = DatabaseConnection.connect();
			 var stmt = connection.prepareStatement(sql)) {

			stmt.setString(1, guest.getEmail());
			stmt.setString(2, guest.getName());
			stmt.setInt(3, guest.getPassword());
			stmt.setString(4, guest.getPhoneNumber());
			stmt.setString(5, guest.getPaymentMethod().getCreditCardNumber());
			stmt.setString(6, guest.getPaymentMethod().getCcvNumber());
			stmt.setString(7, guest.getPaymentMethod().getBillingAddress());
			stmt.setString(8, guest.getPaymentMethod().getExpDateAsString());
			stmt.setInt(9, guest.getTypeId().ordinal());
			ResultSet res = stmt.executeQuery();
			return true;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		return false;
	}
	public static void deleteUser(String email) {
		// delete is SQL code.
		String delete = "DELETE FROM Users WHERE email = ?";
		try (var connection = DatabaseConnection.connect();
			 var ps = connection.prepareStatement(delete)) {
			//JOptionPane.showMessageDialog(null, "Successful Connection! : ReservationRepository");

			ps.setString(1, email);

			int rowAdded = ps.executeUpdate();
			if (rowAdded > 0) {
				System.out.println("A row has been deleted successfully! : ReservationRepository");
			} else {
				System.out.println("Deletion failed. : ReservationRepository");
			}


		}
		catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e + " : ReservationRepository");
		}
	}
	// The parameter guest has the altered version of the typeId
	public static boolean changeTypeId(GuestInterface guest) {
		String modifySQL = "UPDATE Users SET priveleges = ? WHERE email = ?";
		try (Connection connection = DatabaseConnection.connect();
			 PreparedStatement ps = connection.prepareStatement(modifySQL)) {

			ps.setInt(1, guest.getTypeId().ordinal());
			ps.setString(2, guest.getEmail());

			int rowAdded = ps.executeUpdate();
			if (rowAdded > 0) {
				System.out.println("A row's typeId has been modified successfully! : UserRepository");


			} else {
				System.out.println("Modification failed. : UserRepository");
			}

			SessionAccount = guest;
			return true;
		}
		catch(SQLException e) {
			JOptionPane.showMessageDialog(null, e + " : UserRepository");
		}
		return false;
	}
	// Validates a sign in, and sets SessionAccount if successful.
	static boolean authenticate(String email, int passwordHash){
		String sql = "SELECT * FROM Users WHERE email = ? and password = ?";
		try (var connection = DatabaseConnection.connect();
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
			UserRepository.SessionAccount = null;
			return true;
		}
		if (res.next()) {
			switch (res.getInt(9)){
				case 0:
					UserRepository.SessionAccount = new Admin(
							res.getString(1),
							res.getString(2),
							res.getInt(3),
							res.getString(4)
					);
					break;
				case 1:
					UserRepository.SessionAccount = new Clerk(
							res.getString(1),
							res.getString(2),
							res.getInt(3),
							res.getString(4)
					);
					break;
				case 2:
					if (res.getString(5) != null) {
						PaymentMethod pm = new PaymentMethod(res.getString(5), res.getString(6), res.getString(7), res.getString(8));
						UserRepository.SessionAccount = new GuestAdmin(
								res.getString(1),
								res.getString(2),
								res.getInt(3),
								res.getString(4),
								pm
						);
					}
					else {
						UserRepository.SessionAccount = new GuestAdmin(
								res.getString(1),
								res.getString(2),
								res.getInt(3),
								res.getString(4)
						);
					}
					break;
				case 3:
					if (res.getString(5) != null) {
						PaymentMethod pm = new PaymentMethod(res.getString(5), res.getString(6), res.getString(7), res.getString(8));
						UserRepository.SessionAccount = new GuestClerk(
								res.getString(1),
								res.getString(2),
								res.getInt(3),
								res.getString(4),
								pm
						);
					}
					else {
						UserRepository.SessionAccount = new GuestClerk(
								res.getString(1),
								res.getString(2),
								res.getInt(3),
								res.getString(4)
						);
					}
					break;

				default:
					// Assuming Guest is forced to put all their credit card information in when modifying creditCard or makeing a reservation
					if (res.getString(5) != null ) {
						PaymentMethod pm = new PaymentMethod(res.getString(5), res.getString(6), res.getString(7), res.getString(8));
						UserRepository.SessionAccount = new Guest(
								res.getString(1),
								res.getString(2),
								res.getInt(3),
								res.getString(4),
								pm
						);
					}
					else {
						UserRepository.SessionAccount = new Guest(
								res.getString(1),
								res.getString(2),
								res.getInt(3),
								res.getString(4)
						);
					}
					break;
			}
			return true;
		}
		return false;
	}





	// FOR TESTING
	// FOR TESTING
	public static void setUser(User user){ SessionAccount = user; }
	public static boolean getUser(String email){
		String sql = "SELECT * FROM Users WHERE email = ?";
		try (var connection = DatabaseConnection.connect();
			 var stmt = connection.prepareStatement(sql)){
			stmt.setString(1, email);
			ResultSet res = stmt.executeQuery();
			return setSessionAccount(res);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		return false;
	}
	public static boolean createEmployeeAccount(String email, String username, int passwordHash, String phoneNumber, int privilege){
		String sql = "INSERT INTO Users (email, name, password, phoneNumber, priveleges)"
				+" VALUES (?,?,?,?,?)"
				+" RETURNING *";
		try (var connection = DatabaseConnection.connect();
			 var stmt = connection.prepareStatement(sql)) {
			stmt.setString(1, email);
			stmt.setString(2, username);
			stmt.setInt(3, passwordHash);
			stmt.setString(4, phoneNumber);
			stmt.setInt(5, privilege);
			ResultSet res = stmt.executeQuery();
			return setSessionAccount(res);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
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

//package stay_and_shop_system.user;
//
//
//import stay_and_shop_system.DatabaseConnection;
//
//import java.io.*;
//import java.sql.*; // Imports SQLException
//import java.util.UUID;
//
//public class UserRepository {
//	// Use this to indicate what user is currently logged in, or null for logged out.
//	public static User SessionAccount = null; // Change this to private when we've fixed the legacy code.
//	static final String DatabaseURL = "jdbc:sqlite:./data.db";
//	static final String DB_NOT_FOUND = "Critical error: a connection could not be establish with the database.";
//
//	public static User getSessionAccount(){ return SessionAccount; }
//
//
//	// Used to create the account table
//	static void initAccountTable(){
//		String sql = "CREATE TABLE IF NOT EXISTS Accounts ("
//				+ "	email text PRIMARY KEY,"
//				+ "	name text NOT NULL,"
//				+ "	password int NOT NULL,"
//				+ "	phoneNumber text NOT NULL,"
//				+ " paymentId text NOT NULL,"
//				+ " priveleges int NOT NULL" // 0: guest, 1: clerk, 2: admin
//				+ ");";
//		try (var connection = DriverManager.getConnection(DatabaseURL)) {
//			System.out.println("Connection to SQLite has been established.");
//			connection.createStatement().execute(sql);
//			System.out.println("Account table successfully created.");
//		} catch (SQLException e) {
//			System.out.println(e.getMessage());
//			e.printStackTrace();
//		}
//	}
//
//	// Creates a DB connection and initializes an Accounts table if it doesn't exist.
//	static java.sql.Connection connectToDatabase() throws SQLException {
//		String sql = "SELECT name FROM sqlite_master WHERE type='table' AND name='Accounts'";
//		java.sql.Connection connection = DriverManager.getConnection(DatabaseURL);
//		System.out.println("Connection to SQLite has been established.");
//		ResultSet res = connection.createStatement().executeQuery(sql);
//		System.out.println(res);
//		if (!res.next()) {
//			UserRepository.initAccountTable();
//			System.out.println("WARNING: a new account table has been initialized.");
//		}
//		return connection;
//	}
//
//	// Confirms whether a given user exists in the system.
//	static boolean findAccount(String email) {
//		String sql = "SELECT EXISTS(SELECT 1 FROM Accounts WHERE email = ?)";
//		try (var connection = connectToDatabase();
//			 var stmt = connection.prepareStatement(sql)) {
//			stmt.setString(1, email);
//			ResultSet res = stmt.executeQuery();
//			if (res.next()) {
//				return res.getBoolean(1);
//			}
//		} catch (SQLException e) {
//			System.out.println(e.getMessage());
//			e.printStackTrace();
//		}
//		return false;
//	}
//
//	// Handles account creation for guests alone.
//	static boolean createAccount(String email, String username, int passwordHash, String phoneNumber){
//		String sql = "INSERT INTO Accounts (email, name, password, phoneNumber, paymentId, priveleges)"
//				+" VALUES (?,?,?,?,?,0)"
//				+" RETURNING *";
//		try (var connection = connectToDatabase();
//			 var stmt = connection.prepareStatement(sql)) {
//			stmt.setString(1, email);
//			stmt.setString(2, username);
//			stmt.setInt(3, passwordHash);
//			stmt.setString(4, phoneNumber);
//			stmt.setString(5, UUID.randomUUID().toString());
//			ResultSet res = stmt.executeQuery();
//			return setSessionAccount(res);
//		} catch (SQLException e) {
//			System.out.println(e.getMessage());
//			e.printStackTrace();
//		}
//		return false;
//	}
//
//	// Validates a sign in, and sets SessionAccount if successful.
//	static boolean authenticate(String email, int passwordHash){
//		String sql = "SELECT * FROM Accounts WHERE email = ? and password = ?";
//		try (var connection = connectToDatabase();
//			 var stmt = connection.prepareStatement(sql)){
//			stmt.setString(1, email);
//			stmt.setInt(2, passwordHash);
//			ResultSet res = stmt.executeQuery();
//			return setSessionAccount(res);
//		} catch (SQLException e) {
//			System.out.println(e.getMessage());
//			e.printStackTrace();
//		}
//		return false;
//	}
//
//	// Sets SessionAccount. Change the switch cases if you want to change what type of account gets assigned.
//	static boolean setSessionAccount(ResultSet res) throws SQLException {
//		if (res == null){
//			UserRepository.SessionAccount = null;
//			return true;
//		}
//		if (res.next()) {
//			switch (res.getInt(5)){
//				case 1:
//					UserRepository.SessionAccount = new GuestClerk(
//							res.getString(1),
//							res.getString(2),
//							res.getInt(3),
//							res.getString(4),
//							res.getString(5)
//					);
//					break;
//				case 2:
//					UserRepository.SessionAccount = new GuestAdmin(
//							res.getString(1),
//							res.getString(2),
//							res.getInt(3),
//							res.getString(4),
//							res.getString(5)
//					);
//					break;
//				default:
//					UserRepository.SessionAccount = new Guest(
//							res.getString(1),
//							res.getString(2),
//							res.getInt(3),
//							res.getString(4),
//							res.getString(5)
//					);
//					break;
//			}
//			return true;
//		}
//		return false;
//	}
//
//
//
//
//
//
//
//	// Defunct
//	static boolean searchDatabase(String user, String pass) throws SQLException {
//		boolean found;
//
//		Connection conn = DatabaseConnection.connect(); // Connecting to Database
//		Statement stat = conn.createStatement(); // Creating SQL statement
//		// Format: String query = "SELECT (column) FROM (table) WHERE (condition)";
//		String query = "SELECT * FROM Users WHERE Username = \'" + user +
//				"\' AND Password = \'" + pass + "\'";
//		ResultSet rs = stat.executeQuery(query); // Making a Query using the string above.
//
//		found = rs.next(); // Going to the first row. Returns false if there is no data.
//
//		return found;
//	}
//}
