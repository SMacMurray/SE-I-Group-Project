package stay_and_shop_system.occupancy.database;

import stay_and_shop_system.DatabaseConnection;
import stay_and_shop_system.occupancy.Reservation;
import stay_and_shop_system.occupancy.Room;

import javax.swing.*;
import java.sql.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RoomRepository {
    static Connection connection = DatabaseConnection.connect();

    public static void createTable() {
        String sql = """
                CREATE TABLE IF NOT EXISTS Rooms (
                    roomNumber INTEGER PRIMARY KEY,
                    bedNumber INTEGER NOT NULL,
                    maxOccupancy INTEGER NOT NULL,
                    baseDailyRate DOUBLE NOT NULL,
                    smokingStatus INTEGER NOT NULL,
                    bedTypes TEXT NOT NULL,
                    qualityLevel TEXT NOT NULL,
                    roomSize TEXT NOT NULL
                );
                """;
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to create table : RoomRepository", e);
        }
    }
    public static void dropTable() {
        String sql = "DROP TABLE IF EXISTS Rooms";
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to drop table : RoomRepository", e);
        }
    }

    public static List<Room> loadReservations() {
        String loadSQL = "SELECT * FROM Rooms";
        List<Room> rooms = new ArrayList<>();

        try (ResultSet rSet = connection.createStatement().executeQuery(loadSQL)) {
            while (rSet.next()) {
                rooms.add(mapResultSetToRoom(rSet));
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

        for (Room r : rooms) {
            System.out.println(r);
        }

        return rooms;
    }
    public static Room loadRoomOfRoomNumber(int roomNumber) {
        String loadSQL = " SELECT * FROM Rooms WHERE roomNumber = " + roomNumber;
        Room room = null;

        try (ResultSet rSet = connection.createStatement().executeQuery(loadSQL)) {

            if (rSet.next()) {
                room = mapResultSetToRoom(rSet);
            }
        }
        catch (SQLException | ParseException e) {
            e.printStackTrace();
            // JOptionPane.showMessageDialog(null, e + " : ReservationRepository");
        } catch (NullPointerException e) {
            System.out.println("You have not connected to the server.");
            e.printStackTrace();
            System.exit(1);
        }

        return room;
    }
    public static void addRoom(Room r) {
        // insert is SQL code.
        String insert = "INSERT INTO Rooms (roomNumber, bedNumber, maxOccupancy, baseDailyRate, smokingStatus, bedTypes, qualityLevel, roomSize) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(insert)) {
            //JOptionPane.showMessageDialog(null, "Successful Connection! : ReservationRepository");

            ps.setInt(1, r.getNumber());
            ps.setInt(2, r.getBeds());
            ps.setInt(3, r.getMaxOccupancy());
            ps.setDouble(4, r.getBaseDailyRate());
            ps.setInt(5, r.getSmokingStatus() ? 1 : 0);

            List<Room.BedType> bedTypesList = r.getBedTypes().stream()
                    .distinct()
                    .collect(Collectors.toList());
            String bedTypes = bedTypesList.getFirst().toString();
            for( int i = 1; i < bedTypesList.size(); ++i) {
                bedTypes += ", " + bedTypesList.get(i);
                System.out.println("rich");
            }

            ps.setString(6, bedTypes);
            ps.setString(7, r.getQualityLevel().toString());
            ps.setString(8, r.getRoomSize().toString());

            int rowAdded = ps.executeUpdate();
            if (rowAdded > 0) {
                System.out.println("A new row has been inserted successfully! : RoomRepository");
            } else {
                System.out.println("Insertion failed. : RoomRepository");
                // Notes:
                // In MySqL, a primary key (which i made the reservationId for the Reservations table) has to be unique.
            }


        }
        catch (SQLException e) {
            e.printStackTrace();
//			JOptionPane.showMessageDialog(null, e + " : ReservationRepository");
        }
    }
    // Assuming you can't change the roomNumber of a room
    public void modifyReservation(Room r) {
        String modifySQL = "UPDATE Rooms SET roomNumber = ?, bedNumber = ?, maxOccupancy = ?, baseDailyRate = ?, smokingStatus = ?, bedTypes = ?, qualityLevel = ?, roomSize = ? WHERE roomNumber = ?";
        try (PreparedStatement ps = connection.prepareStatement(modifySQL)) {

            ps.setInt(1, r.getNumber());
            ps.setInt(2, r.getBeds());
            ps.setInt(3, r.getMaxOccupancy());
            ps.setDouble(4, r.getBaseDailyRate());
            ps.setInt(5, r.getSmokingStatus() ? 1 : 0);
            String bedTypes = r.getBedTypes().getFirst().toString();
            for( int i = 1; i < r.getBedTypes().size(); ++i) {
                bedTypes += ", " + r.getBedTypes().get(i);
                System.out.println("rich");
            }
            ps.setString(6, bedTypes);
            ps.setString(7, r.getQualityLevel().toString());
            ps.setString(8, r.getRoomSize().toString());
            ps.setInt(9, r.getNumber());

            int rowAdded = ps.executeUpdate();
            if (rowAdded > 0) {
                System.out.println("A row has been modified successfully! : ReservationRepository");
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
    public static Room mapResultSetToRoom(ResultSet rSet) throws SQLException, ParseException{
        Reservation re = null;

        int roomNumber = rSet.getInt("roomNumber");
        int bedNumber = rSet.getInt("bedNumber");
        int maxOccupancy = rSet.getInt("maxOccupancy");
        double baseDailyRate = rSet.getInt("baseDailyRate");
        boolean smokingStatus = rSet.getBoolean("smokingStatus");

        List<Room.BedType> bedTypes = new ArrayList<>();
        String str = (rSet.getString("bedTypes")).replaceAll(" ", "");
        String[] spl = str.split(",");
        for (String s : spl) {
            bedTypes.add(Room.BedType.valueOf(s));
        }

        Room.QualityLevel qualityLevel = Room.QualityLevel.valueOf(rSet.getString("qualityLevel"));
        Room.RoomSize roomSize = Room.RoomSize.valueOf(rSet.getString("roomSize"));

        return new Room(roomNumber, bedNumber, maxOccupancy, baseDailyRate, smokingStatus, bedTypes, qualityLevel, roomSize);
    }

    public static void updateRoom(int originalRoomNumber, Room r) {
        String modifySQL = """
        UPDATE Rooms
        SET roomNumber = ?, bedNumber = ?, maxOccupancy = ?, baseDailyRate = ?,
            smokingStatus = ?, bedTypes = ?, qualityLevel = ?, roomSize = ?
        WHERE roomNumber = ?
        """;

        try (PreparedStatement ps = connection.prepareStatement(modifySQL)) {
            ps.setInt(1, r.getNumber());
            ps.setInt(2, r.getBeds());
            ps.setInt(3, r.getMaxOccupancy());
            ps.setDouble(4, r.getBaseDailyRate());
            ps.setInt(5, r.getSmokingStatus() ? 1 : 0);

            String bedTypes = String.join(", ",
                    r.getBedTypes().stream().map(Enum::name).toList());

            ps.setString(6, bedTypes);
            ps.setString(7, r.getQualityLevel().toString());
            ps.setString(8, r.getRoomSize().toString());
            ps.setInt(9, originalRoomNumber);

            int rowsUpdated = ps.executeUpdate();
            if (rowsUpdated <= 0) {
                System.out.println("Room update failed: RoomRepository");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e + " : RoomRepository");
        }
    }
}
