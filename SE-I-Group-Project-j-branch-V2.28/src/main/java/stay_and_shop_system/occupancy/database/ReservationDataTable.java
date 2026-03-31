package stay_and_shop_system.occupancy.database;

import stay_and_shop_system.occupancy.*;
import stay_and_shop_system.*;

import java.sql.*;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Objects;
import java.text.SimpleDateFormat;
//import Reservation.*;
import javax.swing.JOptionPane;

// To delete all the elements in SQL table, use this SQL code:
// TRUNCATE TABLE Reservations;
// SELECT * FROM yummers.Reservations; -- Allows you to see the table in it's current state

// Cautionary Note: What happens when we delete a room from the database, then we leave a reservation of that type in the database

public class ReservationDataTable {
	static Connection connection = DatabaseConnection.connect();
	ReservationService reS = new ReservationService();
	RoomService rs = new RoomService();
	static SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy/MM/dd");

	public void loadReservations() {
		String loadSQL = "SELECT * FROM Reservations";

		// Assuming rooms are already loaded
		try (ResultSet rSet = connection.createStatement().executeQuery(loadSQL)) {
			while (rSet.next()) {
				int reservationId = rSet.getInt("reservationId");
				int roomNumber = rSet.getInt("roomNumber");
				int guestNumber = rSet.getInt("guestNumber");

				Calendar start = Calendar.getInstance();
				start.setTime(dateFormatter.parse(rSet.getString("startDate")) );
				Calendar end = Calendar.getInstance();
				end.setTime(dateFormatter.parse(rSet.getString("endDate")) );

				String guestName = rSet.getString("guestName");
				String cc = rSet.getString("creditCardNumber");

				double rate = rSet.getDouble("rate");
				double cost = rSet.getDouble("cost");

				if (rs.getRoom(roomNumber) == null) {
					JOptionPane.showMessageDialog(null, "The reservation's room " + roomNumber + " does not exist. : ReservationDataTable");
//					deleteReservation(roomNumber, guestName);
				}
				else {
					reS.loadReservation(new Reservation(rs.getRoom(roomNumber), start, end, guestNumber, guestName, cc));
				}
			}
		}
		catch (SQLException | ParseException e) {
			JOptionPane.showMessageDialog(null, e + " : ReservationDataTable");
		}
		catch (NullPointerException e) {
			System.out.println("You likely did not add the mysql-connecter. You have not connected to the server.");
			e.printStackTrace();
			System.exit(1);
		}

        for (Reservation reserve : ReservationService.reservations) {
			reserve.print();
		}
	}
	public static void addReservation(Reservation re) {
		// insert is SQL code.
		String insert = "INSERT INTO Reservations (reservationId, roomNumber, guestNumber, startDate, endDate, guestName, creditCardNumber, rate, cost) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
		try (PreparedStatement ps = connection.prepareStatement(insert)) {
			//JOptionPane.showMessageDialog(null, "Successful Connection! : ReservationDataTable");
			
			ps.setInt(1, re.reservationId);
			ps.setInt(2, re.roomNumber);
			ps.setInt(3, re.guestNum);
			ps.setString(4, dateFormatter.format(re.start.getTime()));
			ps.setString(5, dateFormatter.format(re.end.getTime()));
			ps.setString(6, re.guestName);
			ps.setString(7, re.creditCardNumber);
			ps.setDouble(8, re.rate);
			ps.setDouble(9, re.cost);
			
			int rowAdded = ps.executeUpdate();
			if (rowAdded > 0) {
                System.out.println("A new row has been inserted successfully! : ReservationDataTable");
            } else {
                System.out.println("Insertion failed. : ReservationDataTable");
                // Notes:
                // In MySqL, a primary key (which i made the reservationId for the Reservations table) has to be unique.
            }
			
	
		}
		catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e + " : ReservationDataTable");
		}
	}
	public void deleteReservation(int roomNumber, String name) {
		// delete is SQL code.
		String delete = "DELETE FROM Reservations WHERE reservationId = ?";
		try (PreparedStatement ps = connection.prepareStatement(delete)) {
			//JOptionPane.showMessageDialog(null, "Successful Connection! : ReservationDataTable");

			System.out.println(Objects.hash(roomNumber + name) + " : ReservationDataTable");
			ps.setInt(1, Objects.hash(roomNumber + name));						
			
			int rowAdded = ps.executeUpdate();
			if (rowAdded > 0) {
                System.out.println("A row has been deleted successfully! : ReservationDataTable");
            } else {
                System.out.println("Deletion failed. : ReservationDataTable");
                // Notes:
                // In MySqL, a primary key (which i made the reservationId for the Reservations table) has to be unique.
            }
			
	
		}
		catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e + " : ReservationDataTable");
		}
	}
	public void modifyReservation(int roomNumber, String name, Reservation re) {
		String modifySQL = "UPDATE Reservations SET roomNumber = ?, guestNumber = ?, startDate = ?, endDate = ?, guestName = ?, creditCardNumber = ?, rate = ?, cost = ? WHERE reservationId = ?";
		try (PreparedStatement ps = connection.prepareStatement(modifySQL)) {

			ps.setInt(1, re.roomNumber);
			ps.setInt(2, re.guestNum);
			ps.setString(3, dateFormatter.format(re.start.getTime()) );
			ps.setString(4, dateFormatter.format(re.end.getTime()) );
			ps.setString(5, re.guestName );
			ps.setString(6, re.creditCardNumber );
			ps.setDouble(7, re.rate );
			ps.setDouble(8, re.cost );
			ps.setInt(9, Objects.hash(roomNumber + name));

			int rowAdded = ps.executeUpdate();
			if (rowAdded > 0) {
				System.out.println("A row has been modified successfully! : ReservationDataTable");
			} else {
				System.out.println("Modification failed. : ReservationDataTable");
				// Notes:
				// In MySqL, a primary key (which i made the reservationId for the Reservations table) has to be unique.
			}
		}
		catch(SQLException e) {
			JOptionPane.showMessageDialog(null, e + " : ReservationDataTable");
		}
	}
	// getReservation
	// addReservation
	// deleteReservation - Reservation data should be linked to the Guest, but not the opposite for easy deletion
	// modifyReservation
	
	
//	import java.sql.Connection;
//	import java.sql.DriverManager;
//	import java.sql.PreparedStatement;
//	import java.sql.SQLException;
//
//	public class InsertRowExample {
//
//	    public static void main(String[] args) {
//	        // Database connection details
//	        String url = "jdbc:mysql://localhost:3306/yourDatabaseName";
//	        String user = "yourUsername";
//	        String password = "yourPassword";
//
//	        // SQL INSERT statement with placeholders (?)
//	        String insertSQL = "INSERT INTO EMPLOYEE (id, name, email, age, salary) VALUES (?, ?, ?, ?, ?)";
//
//	        try (
//	            // 1. Establish a connection to the database
//	            Connection conn = DriverManager.getConnection(url, user, password);
//	            // 2. Prepare the SQL statement
//	            PreparedStatement ps = conn.prepareStatement(insertSQL)
//	        ) {
//	            // 3. Set parameter values dynamically
//	            ps.setInt(1, 101);         // Set the first parameter (id)
//	            ps.setString(2, "John Doe"); // Set the second parameter (name)
//	            ps.setString(3, "john.doe@example.com"); // Set the third parameter (email)
//	            ps.setInt(4, 30);          // Set the fourth parameter (age)
//	            ps.setInt(5, 60000);       // Set the fifth parameter (salary)
//
//	            // 4. Execute the update (INSERT operation)
//	            int rowsAffected = ps.executeUpdate();
//
//	            // 5. Check if insertion was successful
//	            if (rowsAffected > 0) {
//	                System.out.println("A new row has been inserted successfully!");
//	            } else {
//	                System.out.println("Insertion failed.");
//	            }
//
//	        } catch (SQLException e) {
//	            // Handle any SQL exceptions
//	            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
//	            e.printStackTrace();
//	        }
//	        // 6. Resources are automatically closed by the try-with-resources statement
//	    }
//	}

//	
//	try (Connection connection = DatabaseConnection.connect()) {
//        Statement statement = connection.createStatement();
//
//        // String query = "SELECT column_name FROM table_name WHERE condition";
//        // String query = "SELECT Username FROM Users WHERE Username = 'Dad'";
//        String query = "SELECT * FROM Users WHERE Username = 'Dad'";
//        ResultSet resultSet = statement.executeQuery(query);
//
//        while (resultSet.next()) {
//            // Retrieve by column name or index (index is generally more efficient)
//            // String value = resultSet.getString("column_name");
//
//            // String value = resultSet.getString("Username");
//
//            // Or get by index (e.g., if it's the first column)
//            // String value = resultSet.getString(1);
//            System.out.println("RAAH");
//            System.out.println("Retrieved value: " + resultSet.getString("Username") + " " + resultSet.getString("Password"));
//        }
//    }
//    catch (SQLException e) {
//        e.printStackTrace();
//    }
}
