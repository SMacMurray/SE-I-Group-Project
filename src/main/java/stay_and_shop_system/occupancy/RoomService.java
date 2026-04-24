package stay_and_shop_system.occupancy;

import stay_and_shop_system.occupancy.database.RoomRepository;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class RoomService {
	public static Room[] rooms = new Room[300]; // Might need to fix the size

	// Dont use the RoomService to add rooms. Use RoomRepository now.

	public void createRoom(int number, int beds, int maxOccupancy, double baseDailyRate,
			boolean smokingStatus, List<Room.BedType> bedTypes, Room.QualityLevel qualityLevel,
			Room.RoomSize roomSize) {
		throw new RuntimeException("TODO: Replace the RoomService creation of rooms with the RoomRepository. You don't need RoomService to access the Repository");
//		bedTypes = bedTypes.stream().distinct().collect(Collectors.toList());
//		rooms[number - 100] = (new Room (number, beds, maxOccupancy, baseDailyRate, smokingStatus,
//							bedTypes, qualityLevel, roomSize));
	}

	// Make sure to get rid of this since we are using a database instead of a CSV.
	public void saveRoomToCSV(Room room) {
		throw new RuntimeException("TODO: Replace the CSV saving with the RoomRepository. You don't need RoomService to access the Repository");

//
//		try (FileWriter fw = new FileWriter("src/main/resources/updatedReserves.csv", true)) {
//			StringBuilder bedTypes = new StringBuilder();
//
//			if (room.bedTypes.size() > 1) {
//				bedTypes.append("\"");
//			}
//			for (int i = 0; i < room.bedTypes.size(); i++) {
//				bedTypes.append(room.bedTypes.get(i).name());
//				if (i < room.bedTypes.size() - 1) {
//					bedTypes.append(", ");
//				}
//			}
//			if (room.bedTypes.size() > 1) {
//				bedTypes.append("\"");
//			}
//
//			fw.write(
//					room.number + "," +
//							room.beds + "," +
//							room.maxOccupancy + "," +
//							room.baseDailyRate + "," +
//							(room.smokingStatus ? "Permitted" : "Prohibited") + "," +
//							bedTypes.toString() + "," +
//							room.qualityLevel.name() + "," +
//							room.roomSize.name() + "\n"
//			);
//
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
	}

	public List<Room> findCandidateRooms(RoomCriteria rc) {
		List<Room> candidateRooms = new ArrayList<>();

		for (Room r : RoomRepository.loadRooms()) {
			if (r != null) {
				System.out.println("Not null room : Room Service");
				boolean suitable = true;
				System.out.println("Room number: " + r.getNumber() + " : RoomService");
				// room number has to be within one of the ranges
				List<Boolean> floorBools = new ArrayList<>();
				for (int floor : rc.getRoomRange()) {
					floorBools.add(floor + 100 > r.getNumber() && floor <= r.getNumber());
				}
				if (!floorBools.isEmpty() && !floorBools.contains(true)) {
					suitable = false;
				}
				System.out.println(suitable + " : Room Service");

				suitable = (rc.getBedRange()[0] <= r.getBeds() && rc.getBedRange()[1] >= r.getBeds()) ? suitable : false;
				System.out.println(suitable + " : Room Service");

				suitable = (rc.getGuestRange()[0] <= r.getMaxOccupancy() && rc.getGuestRange()[1] >= r.getMaxOccupancy()) ? suitable : false;
				System.out.println(suitable + " : Room Service");

				suitable = (rc.getCostRange()[0] <= r.getDailyRate() && rc.getCostRange()[1] >= r.getDailyRate()) ? suitable : false;
				System.out.println(suitable + " : Room Service");

                List<Boolean> smokingBools = new ArrayList<>(rc.getSmokingStatuses());
				if (!smokingBools.isEmpty() && !smokingBools.contains(r.getSmokingStatus())) {
					suitable = false;
				}
				System.out.println(suitable + " : Room Service");

				// A room must have all the types in the rc.getBedTypes()
				List<Boolean> containsTypes = new ArrayList<>();
				for (int i = 0; i < rc.getBedTypes().size(); ++i) {
					containsTypes.add(false);
				}
				for (Room.BedType bT : r.getBedTypes()) {
					for (int i = 0; i < containsTypes.size(); ++i) {
						containsTypes.set(i, (bT == rc.getBedTypes().get(i)) ? true : containsTypes.get(i));
					}
				}
				for (int i = 0; i < containsTypes.size(); ++i) {
					suitable = (containsTypes.get(i) == true) ? suitable : false;
				}
				System.out.println(suitable + " : Room Service");

				if (!rc.getRoomSizes().contains(r.getRoomSize()) && !rc.getRoomSizes().isEmpty()) {
					suitable = false;
				}
				
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
		throw new RuntimeException("TODO: Replace the RoomService getting of Rooms with the RoomRepository. You don't need RoomService to access the Repository");
//
//		try {
//			return rooms[number - 100];
//		}
//		catch(ArrayIndexOutOfBoundsException e) {
//			System.out.println("Array out of bounds : RoomService getRoom()");
//			System.out.println("Returning null : RoomService getRoom()");
//
//			return null;
//		}
	}
	public boolean updateRoom(int originalRoomNumber,
							  int newRoomNumber,
							  int beds,
							  int maxOccupancy,
							  double baseDailyRate,
							  boolean smokingStatus,
							  List<Room.BedType> bedTypes,
							  Room.QualityLevel qualityLevel,
							  Room.RoomSize roomSize) {

		if (originalRoomNumber < 100 || originalRoomNumber > 399) {
			return false;
		}

		Room existingRoom = getRoom(originalRoomNumber);
		if (existingRoom == null) {
			return false;
		}

		if (newRoomNumber < 100 || newRoomNumber > 399) {
			return false;
		}

		if (beds < 1 || beds > 4) {
			return false;
		}

		if (maxOccupancy < 1 || baseDailyRate < 0) {
			return false;
		}

		// If changing the room number, make sure the target number is not already taken
		if (newRoomNumber != originalRoomNumber && getRoom(newRoomNumber) != null) {
			return false;
		}

		bedTypes = bedTypes.stream().distinct().toList();

		// If room number changed, clear old slot and create at new slot
		if (newRoomNumber != originalRoomNumber) {
			rooms[originalRoomNumber - 100] = null;
		}

		rooms[newRoomNumber - 100] = new Room(
				newRoomNumber,
				beds,
				maxOccupancy,
				baseDailyRate,
				smokingStatus,
				bedTypes,
				qualityLevel,
				roomSize
		);

		return true;
	}

	public void rewriteRoomsCSV() {
		try (FileWriter fw = new FileWriter("src/main/resources/updatedReserves.csv", false)) {
			fw.write("roomNumber,beds,maxOccupancy,baseDailyRate,smokingStatus,bedTypes,qualityLevel,roomSize\n");

			for (Room room : rooms) {
				if (room == null) continue;

				StringBuilder bedTypes = new StringBuilder();
				if (room.getBedTypes().size() > 1) {
					bedTypes.append("\"");
				}

				for (int i = 0; i < room.getBedTypes().size(); i++) {
					bedTypes.append(room.getBedTypes().get(i).name());
					if (i < room.getBedTypes().size() - 1) {
						bedTypes.append(", ");
					}
				}

				if (room.getBedTypes().size() > 1) {
					bedTypes.append("\"");
				}

				fw.write(
						room.getNumber() + "," +
								room.getBeds() + "," +
								room.getMaxOccupancy() + "," +
								room.getBaseDailyRate() + "," +
								(room.getSmokingStatus() ? "Permitted" : "Prohibited") + "," +
								bedTypes + "," +
								room.getQualityLevel().name() + "," +
								room.getRoomSize().name() + "\n"
				);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
