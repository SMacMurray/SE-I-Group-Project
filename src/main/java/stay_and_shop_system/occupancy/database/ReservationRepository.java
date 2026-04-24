package stay_and_shop_system.occupancy.database;

import stay_and_shop_system.occupancy.*;
import stay_and_shop_system.*;

import java.util.*;
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

public class ReservationRepository {
	static Connection connection = DatabaseConnection.connect();
	//ReservationController reS = new ReservationController();
	RoomService rs = new RoomService();
	static SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy/MM/dd");

	public static void createTable() {
		String sql = """
                CREATE TABLE IF NOT EXISTS Reservations (
                    reservationId INTEGER PRIMARY KEY,
                    roomNumber INTEGER NOT NULL,
                    guestNumber INTEGER NOT NULL,
                    startDate TEXT NOT NULL,
                    endDate TEXT NOT NULL,
                    checkInDate TEXT,
                    guestName TEXT NOT NULL,
                    guestEmail TEXT NOT NULL,
                    creditCardNumber TEXT NOT NULL,
                    rate DOUBLE NOT NULL,
                    cost DOUBLE NOT NULL,
                    guestId INTEGER NOT NULL
                );
                """;
		try (Statement stmt = connection.createStatement()) {
			stmt.execute(sql);
		} catch (SQLException e) {
			throw new RuntimeException("Failed to create table : ReservationRepository", e);
		}
	}
	public static void dropTable() {
		String sql = "DROP TABLE IF EXISTS Reservations";
		try (Statement stmt = connection.createStatement()) {
			stmt.execute(sql);
		} catch (SQLException e) {
			throw new RuntimeException("Failed to drop table : ReservationRepository", e);
		}
	}
	// Assuming rooms are already loaded
	// Joel: Im conflicted on if I want to combine loadRooms and loadReservationsOfName
	public static  List<Reservation> loadReservations() {
		String loadSQL = "SELECT * FROM Reservations";
		List<Reservation> reservations = new ArrayList<>();

		try (ResultSet rSet = connection.createStatement().executeQuery(loadSQL)) {
			while (rSet.next()) {
				reservations.add(mapResultSetToReservation(rSet));
			}
		}
		catch (SQLException | ParseException e) {
			e.printStackTrace();
			// JOptionPane.showMessageDialog(null, e + " : ReservationRepository");
		}
		catch (NullPointerException e) {
			System.out.println("You have not connected to the server.");
			e.printStackTrace();
			System.exit(1);
		}


        for (Reservation reserve : reservations) {
			reserve.print();
		}

		return reservations;
	}
	public static List<Reservation> loadReservationsOfGuestId(int guestId) {
		String loadSQL = " SELECT * FROM Reservations WHERE guestId = " + guestId + "";
		List<Reservation> reservations = new ArrayList<>();

		try (ResultSet rSet = connection.createStatement().executeQuery(loadSQL)) {
			reservations = new ArrayList<>();
			while (rSet.next()) {
				reservations.add(mapResultSetToReservation(rSet));
			}
		}
		catch (SQLException | ParseException e) {
			e.printStackTrace();
			// JOptionPane.showMessageDialog(null, e + " : ReservationRepository");
		}
		catch (NullPointerException e) {
			System.out.println("You have not connected to the server.");
			e.printStackTrace();
			System.exit(1);
		}

		for (Reservation reserve : reservations) {
			reserve.print();
		}

		return reservations;
	}
	public static Reservation loadReservationOfId(int id) {
		String loadSQL = " SELECT * FROM Reservations WHERE reservationId = " + id;
		Reservation reservation = null;

		try (ResultSet rSet = connection.createStatement().executeQuery(loadSQL)) {
			rSet.next();

			reservation = mapResultSetToReservation(rSet);
		}
		catch (SQLException | ParseException e) {
			e.printStackTrace();
			// JOptionPane.showMessageDialog(null, e + " : ReservationRepository");
		} catch (NullPointerException e) {
			System.out.println("You have not connected to the server.");
			e.printStackTrace();
			System.exit(1);
		}

		return reservation;
	}
	public static void addReservation(Reservation re) {
		// insert is SQL code.
		String insert = "INSERT INTO Reservations (reservationId, roomNumber, guestNumber, startDate, endDate, checkInDate, guestName, guestEmail, creditCardNumber, rate, cost, guestId) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		try (PreparedStatement ps = connection.prepareStatement(insert)) {
			//JOptionPane.showMessageDialog(null, "Successful Connection! : ReservationRepository");
			
			ps.setInt(1, re.getReservationId());
			ps.setInt(2, re.getRoomNumber());
			ps.setInt(3, re.getGuestNumber());
			ps.setString(4, dateFormatter.format(re.getStartDate().getTime()));
			ps.setString(5, dateFormatter.format(re.getEndDate().getTime()));
			if (re.getCheckInDate() != null) {
				ps.setString(6, dateFormatter.format(re.getCheckInDate().getTime()));
			}
			else {
				ps.setString(6, null);
			}
			ps.setString(7, re.getGuestName());
			ps.setString(8, re.getGuestEmail());
			ps.setString(9, re.getCreditCardNumber());
			ps.setDouble(10, re.getRate());
			ps.setDouble(11, re.getCost());
			ps.setInt(12, re.getGuestId());
			
			int rowAdded = ps.executeUpdate();
			if (rowAdded > 0) {
                System.out.println("A new row has been inserted successfully! : ReservationRepository");
            } else {
                System.out.println("Insertion failed. : ReservationRepository");
                // Notes:
                // In MySqL, a primary key (which i made the reservationId for the Reservations table) has to be unique.
            }
			
	
		}
		catch (SQLException e) {
			e.printStackTrace();
//			JOptionPane.showMessageDialog(null, e + " : ReservationRepository");
		}
	}
	public static void deleteReservation(Reservation reservation) {
		// delete is SQL code.
		String delete = "DELETE FROM Reservations WHERE reservationId = ?";
		try (PreparedStatement ps = connection.prepareStatement(delete)) {
			//JOptionPane.showMessageDialog(null, "Successful Connection! : ReservationRepository");

			System.out.println(Objects.hash(reservation.getReservationId()) + " : ReservationRepository");
			ps.setInt(1, reservation.getReservationId());
			
			int rowAdded = ps.executeUpdate();
			if (rowAdded > 0) {
                System.out.println("A row has been deleted successfully! : ReservationRepository");
            } else {
                System.out.println("Deletion failed. : ReservationRepository");
                // Notes:
                // In MySqL, a primary key (which i made the reservationId for the Reservations table) has to be unique.
            }
			
	
		}
		catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e + " : ReservationRepository");
		}
	}
	public static void modifyReservation(int prevReservationId, Reservation re) {
		String modifySQL = "UPDATE Reservations SET roomNumber = ?, guestNumber = ?, startDate = ?, endDate = ?, checkInDate = ?, guestName = ?, guestEmail = ?, creditCardNumber = ?, rate = ?, cost = ?, guestId = ?, reservationId = ? WHERE reservationId = ?";
		try (PreparedStatement ps = connection.prepareStatement(modifySQL)) {

			ps.setInt(1, re.getRoomNumber());
			ps.setInt(2, re.getGuestNumber());
			ps.setString(3, re.getFormattedStartDate());
			ps.setString(4, re.getFormattedEndDate());
			if (re.getCheckInDate() == null) {
				ps.setString(5, null);
			}
			else {
				ps.setString(5, re.getFormattedCheckInDate());
			}
			ps.setString(6, re.getGuestName() );
			ps.setString(7, re.getGuestEmail() );
			ps.setString(8, re.getCreditCardNumber() );
			ps.setDouble(9, re.getRate() );
			ps.setDouble(10, re.getCost() );
			ps.setInt(11, re.getGuestId());
			ps.setInt(12, re.getReservationId());
			ps.setInt(13, prevReservationId);

			int rowAdded = ps.executeUpdate();
			if (rowAdded > 0) {
				System.out.println("A row has been modified successfully! : ReservationRepository");
				System.out.println(re + " \n : ReservationRepository");
			} else {
				System.out.println("Modification failed. : ReservationRepository");
				// Notes:
				// In MySqL, a primary key (which i made the reservationId for the Reservations table) has to be unique.
			}
		}
		catch(SQLException e) {
			e.printStackTrace();

			JOptionPane.showMessageDialog(null, e + " : ReservationRepository");
		}
	}
	public static Reservation mapResultSetToReservation(ResultSet rSet) throws SQLException, ParseException{
		Reservation re = null;

		int reservationId = rSet.getInt("reservationId");
		int roomNumber = rSet.getInt("roomNumber");
		int guestNumber = rSet.getInt("guestNumber");

		Calendar start = Calendar.getInstance();
		start.setTime(dateFormatter.parse(rSet.getString("startDate")));
		Calendar end = Calendar.getInstance();
		end.setTime(dateFormatter.parse(rSet.getString("endDate")));
		Calendar checkIn = null;
		if (!(rSet.getString("checkInDate") == null)) {
			checkIn = Calendar.getInstance();
			checkIn.setTime(dateFormatter.parse(rSet.getString("endDate")));
		}

		String guestName = rSet.getString("guestName");
		String guestEmail = rSet.getString("guestEmail");
		String cc = rSet.getString("creditCardNumber");

		double rate = rSet.getDouble("rate");
		double cost = rSet.getDouble("cost");

		Room r = RoomRepository.loadRoomOfRoomNumber(roomNumber);
		if (r == null) {
			System.out.println("The reservation's room " + roomNumber + " does not exist. : ReservationRepository");
			System.out.println("Skipping reservation at room " + roomNumber + ". : ReservationRepository");
//				deleteReservation(roomNumber, guestName);
		} else {
			re = new Reservation(r, start, end, guestNumber, guestName, guestEmail, cc);
			re.setCheckInDate(checkIn);
		}

		return re;
	}

}
