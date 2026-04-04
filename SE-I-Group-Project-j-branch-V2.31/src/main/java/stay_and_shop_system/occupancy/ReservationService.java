package stay_and_shop_system.occupancy;

import stay_and_shop_system.occupancy.database.ReservationDataTable;
import stay_and_shop_system.user.*;

import java.util.*;


public class ReservationService {
	// Won't stop saying can't make a type of ArrayList or smth, had to cut the entire code than paste it
	public static List<Reservation> reservations  = new ArrayList<>();

	ReservationDataTable rdt = new ReservationDataTable();
	private RoomService rs = new RoomService();
	GuestService gs = new GuestService();

	public void loadReservations() {
		reservations = rdt.loadReservations();
	}
	public void reserveRoom(Room room, Calendar start, Calendar end, int guestNum, String guestName, String email, String creditCardNumber ) {
		Reservation reservation = new Reservation(room, start, end, guestNum, guestName, creditCardNumber);
		reservations.add(reservation);

		ReservationDataTable.addReservation(reservation);

		Guest guest;
		if (!gs.containsGuest(guestName, email)) {
			guest = gs.addGuest(guestName, email);
		}
		else {
			guest = gs.findGuest(guestName, email);
		}
		
		guest.addReservation(reservation);
	}
	public List<Reservation> findReservationsOfName(String name) {
		return rdt.loadReservationsOfName(name);
	}
	public List<Room> deleteOverlapRooms(List<Room> rooms) {
		loadReservations();

		for (Reservation re : reservations) {
			rooms.remove(re.room);
		}
		
		return rooms;
	}
}
