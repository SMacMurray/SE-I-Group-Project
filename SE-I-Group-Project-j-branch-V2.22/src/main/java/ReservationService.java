import java.util.*;

public class ReservationService {
	// Won't stop saying can't make a type of ArrayList or smth, had to cut it than paste it
	List<Reservation> reservations  = new ArrayList<>(); 
	
	public void reserveRoom(Room room, Calendar start, Calendar end, int guestNum) {
		reservations.add(new Reservation(room, start, end, guestNum));
	}
	public List<Room> deleteOverlapRooms(List<Room> rooms) {
		for (Reservation re : reservations) {
			rooms.remove(re.room);
		}
		
		return rooms;
	}
}
