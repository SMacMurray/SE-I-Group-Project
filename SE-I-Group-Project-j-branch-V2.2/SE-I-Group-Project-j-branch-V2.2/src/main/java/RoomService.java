import java.util.*;

public class RoomService {
	private List<Room> rooms = new ArrayList<>();
	
	public void createRoom(int number, int beds, int maxOccupancy, double baseDailyRate, 
			boolean smokingStatus, Room.BedType bedType, Room.QualityLevel qualityLevel,
			Room.RoomSize roomSize) {
		rooms.add(new Room (number, beds, maxOccupancy, baseDailyRate, smokingStatus,
							bedType, qualityLevel, roomSize));
	}
}
