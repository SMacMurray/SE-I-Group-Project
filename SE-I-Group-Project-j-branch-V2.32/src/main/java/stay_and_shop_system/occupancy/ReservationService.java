package stay_and_shop_system.occupancy;

import stay_and_shop_system.occupancy.database.ReservationDataTable;
import stay_and_shop_system.user.*;

import java.text.SimpleDateFormat;
import java.util.*;


public class ReservationService {
	// Won't stop saying can't make a type of ArrayList or smth, had to cut the entire code than paste it
	private static List<Reservation> reservations  = new ArrayList<>();

	ReservationDataTable rdt = new ReservationDataTable();
	private RoomService rs = new RoomService();
	private GuestService gs = new GuestService();
	private SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");

	public void loadReservations() {
		reservations = rdt.loadReservations();
	}
	public List<Reservation> getReservations() {
		loadReservations();
		return reservations;
	}
	public SimpleDateFormat getDateFormatter() {
		return formatter;
	}
	public Reservation getReservationOfId(int id) {
		return rdt.loadReservationOfId(id);
	}
	public void deleteReservation(Reservation r) {
		reservations.remove(r);
		rdt.deleteReservation(r.getRoomNumber(), r.getGuestName());
	}
	public void reserveRoom(Room room, Calendar start, Calendar end, int guestNum, String guestName, String guestEmail, String creditCardNumber ) {
		Reservation reservation = new Reservation(room, start, end, guestNum, guestName, guestEmail,  creditCardNumber);
		reservations.add(reservation);

		ReservationDataTable.addReservation(reservation);

		// TODO: Handle GuestINterface
		if (!gs.containsGuest(guestName, guestEmail)) {
			gs.addGuest(guestName, guestEmail);
		}
		
	}
	public List<Reservation> findReservationsOfGuest(GuestInterface guest) {
		// TODO: Make guest have an ID attatched to their reservation unique to them because any guest can have the same name.
		return rdt.loadReservationsOfEmail(guest.getEmail());
//		throw new RuntimeException("TODO: Finish findReservationsOfGuest()");
	}
	public List<Room> deleteOverlapRooms(List<Room> rooms) {
		loadReservations();

		for (Reservation re : reservations) {
			rooms.remove(re.room);
		}
		
		return rooms;
	}
}
