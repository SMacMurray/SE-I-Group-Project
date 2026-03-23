import java.util.*;

public class ReservationService {
	// Won't stop saying can't make a type of ArrayList or smth, had to cut the entire code than paste it
	static List<Reservation> reservations  = new ArrayList<>(); 
	GuestService gs = new GuestService();
	
	public void reserveRoom(Room room, Calendar start, Calendar end, int guestNum, String guestName, String email, String creditCardNumber ) {
		Reservation reservation = new Reservation(room, start, end, guestNum, guestName, creditCardNumber);
		reservations.add(reservation);
		
		Guest guest;
		if (!gs.containsGuest(guestName, email)) {
			guest = gs.addGuest(guestName, email);
		}
		else {
			guest = gs.findGuest(guestName, email);
		}
		
		guest.addReservation(reservation);
	}
	public List<Room> deleteOverlapRooms(List<Room> rooms) {
		for (Reservation re : reservations) {
			rooms.remove(re.room);
		}
		
		return rooms;
	}
}
