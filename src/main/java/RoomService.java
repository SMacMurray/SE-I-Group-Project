import java.util.*;

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
	
}
