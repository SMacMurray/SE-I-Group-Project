import java.util.*;

public class RoomService {
	public static Room[] rooms = new Room[300]; // Might need to fix the size
	
	public void createRoom(int number, int beds, int maxOccupancy, double baseDailyRate, 
			boolean smokingStatus, List<Room.BedType> bedTypes, Room.QualityLevel qualityLevel,
			Room.RoomSize roomSize) {
		rooms[number - 100] = (new Room (number, beds, maxOccupancy, baseDailyRate, smokingStatus,
							bedTypes, qualityLevel, roomSize));
	}
	public List<Room> findCandidateRooms(Room room) {
		
		List<Room> candidateRooms = new ArrayList<>();
		for (Room r : rooms) {
			if (r != null) {
				System.out.println("Not null room : Room Service");
				boolean suitable = true;
				suitable = (room.number + 100 > r.number && room.number <= r.number) ? suitable : false;
//				System.out.println(suitable + " : Room Service");
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
				suitable = (room.qualityLevel == r.qualityLevel) ? suitable : false;
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
		return rooms[number - 100];
	}
	
}
