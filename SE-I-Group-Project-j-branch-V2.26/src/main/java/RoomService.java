import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class RoomService {
	public static Room[] rooms = new Room[300]; // Might need to fix the size
	
	public void createRoom(int number, int beds, int maxOccupancy, double baseDailyRate, 
			boolean smokingStatus, List<Room.BedType> bedTypes, Room.QualityLevel qualityLevel,
			Room.RoomSize roomSize) {
		rooms[number - 100] = (new Room (number, beds, maxOccupancy, baseDailyRate, smokingStatus,
							bedTypes, qualityLevel, roomSize));
	}

	public void saveRoomToCSV(Room room) {
		try (FileWriter fw = new FileWriter("updatedReserves.csv", true)) {
			StringBuilder bedTypes = new StringBuilder();

			if (room.bedTypes.size() > 1) {
				bedTypes.append("\"");
			}
			for (int i = 0; i < room.bedTypes.size(); i++) {
				bedTypes.append(room.bedTypes.get(i).name());
				if (i < room.bedTypes.size() - 1) {
					bedTypes.append(", ");
				}
			}
			if (room.bedTypes.size() > 1) {
				bedTypes.append("\"");
			}

			fw.write(
					room.number + "," +
							room.beds + "," +
							room.maxOccupancy + "," +
							room.baseDailyRate + "," +
							(room.smokingStatus ? "Permitted" : "Prohibited") + "," +
							bedTypes.toString() + "," +
							room.qualityLevel.name() + "," +
							room.roomSize.name() + "\n"
			);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public List<Room> findCandidateRooms(Room room) {
		List<Room> candidateRooms = new ArrayList<>();

		for (Room r : rooms) {
			if (r != null) {
				System.out.println("Not null room : Room Service");
				boolean suitable = true;
				System.out.println("Room number: " + r.number + " : RoomService");
				suitable = (room.number + 100 > r.number && room.number <= r.number) ? suitable : false;
				System.out.println(suitable + " : Room Service");
				suitable = (room.beds <= r.beds) ? suitable : false;
				System.out.println(suitable + " : Room Service");
				suitable = (room.maxOccupancy <= r.maxOccupancy) ? suitable : false;
				System.out.println(suitable + " : Room Service");
				suitable = (room.baseDailyRate <= r.baseDailyRate) ? suitable : false;
				System.out.println(suitable + " : Room Service");
				suitable = (room.smokingStatus == r.smokingStatus) ? suitable : false;
				List<Boolean> containsTypes = new ArrayList<>();
				for (int i = 0; i < room.bedTypes.size(); ++i) {
					containsTypes.add(false);
				}
				for (Room.BedType bT : r.bedTypes) {
					for (int i = 0; i < containsTypes.size(); ++i) {
						containsTypes.set(i, (bT == room.bedTypes.get(i)) ? true : containsTypes.get(i));
					}
				}
				for (int i = 0; i < containsTypes.size(); ++i) {
					suitable = (containsTypes.get(i) == true) ? suitable : false;
				}
				System.out.println(suitable + " : Room Service");
				suitable = (room.qualityLevel == r.qualityLevel) ? suitable : false;
				System.out.println(suitable + " : Room Service");
				suitable = (room.roomSize == r.roomSize) ? suitable : false;
				
				if (suitable) {
					System.out.println("Suitable Room : Room Service");
					candidateRooms.add(r);
				}
				System.out.println();
			}
		}
		
		
		return candidateRooms;
	}
	public Room getRoom(int number) {
		try {
			return rooms[number - 100];
		}
		catch(ArrayIndexOutOfBoundsException e) {
			System.out.println("Array out of bounds : RoomService getRoom()");
			System.out.println("Returning null : RoomService getRoom()");

			return null;
		}
	}
	
}
