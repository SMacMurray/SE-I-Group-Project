package stay_and_shop_system.occupancy;

import java.util.*;

public class SearchController {
	RoomService rs = new RoomService();
	ReservationService reS = new ReservationService();
	
	public List<Room> searchAvailableRooms(Room room) {
		// Have to convert the 'floor' String into a number so I can do findCandidateRooms
		return reS.deleteOverlapRooms(rs.findCandidateRooms(room));
	}
}
