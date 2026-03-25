import java.util.*;
import java.io.FileWriter;
import java.io.IOException;

public class RoomService {


	Room[] rooms = new Room[300]; // Might need to fix the size
	
	public void createRoom(int number, int beds, int maxOccupancy, double baseDailyRate, 
			boolean smokingStatus, List<Room.BedType> bedTypes, Room.QualityLevel qualityLevel,
			Room.RoomSize roomSize) {
		rooms[number - 100] = (new Room (number, beds, maxOccupancy, baseDailyRate, smokingStatus,
							bedTypes, qualityLevel, roomSize));
	}
	public Room getRoom(int number) {
		return rooms[number - 100];
	}



	public void saveRoomToCSV(Room room) {
		try (FileWriter fw = new FileWriter("reserve.csv", true)) {
			StringBuilder bedTypes = new StringBuilder();

			for (int i = 0; i < room.bedType.size(); i++) {
				bedTypes.append(room.bedType.get(i).name());
				if (i < room.bedType.size() - 1) {
					bedTypes.append(",");
				}
			}

			fw.write(
					room.number + "\t" +
							room.beds + "\t" +
							room.maxOccupancy + "\t" +
							room.baseDailyRate + "\t" +
							(room.smokingStatus ? "Permitted" : "Not Permitted") + "\t" +
							bedTypes.toString() + "\t" +
							room.qualityLevel.name() + "\t" +
							room.roomSize.name() + "\n"
			);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
