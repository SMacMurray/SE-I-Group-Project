package stay_and_shop_system.occupancy;

import stay_and_shop_system.occupancy.database.ReservationRepository;
import stay_and_shop_system.user.*;

import java.text.SimpleDateFormat;
import java.util.*;


public class ReservationController {
	// Won't stop saying can't make a type of ArrayList or smth, had to cut the entire code than paste it
	private static List<Reservation> reservations  = new ArrayList<>();

	ReservationRepository rrp = new ReservationRepository();
	private RoomService rs = new RoomService();
	private GuestService gs = new GuestService();
	private SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");

	public SimpleDateFormat getDateFormatter() {
		return formatter;
	}
	public Object[] reserveRoom(Room room, Calendar start, Calendar end, int guestNum, String guestName, String guestEmail, String phoneNumber, String creditCardNumber, String ccv, String billingAddr, Calendar expDate ) {
		Reservation reservation = new Reservation(room, start, end, guestNum, guestName, guestEmail,  creditCardNumber);
		reservation.calculateTotal();

		User user = UserRepository.getSessionAccount();
		GuestInterface guest;
		PaymentMethod pm = new PaymentMethod(creditCardNumber, ccv, billingAddr, expDate);
		if (user == null) {
			if (!UserRepository.findUser(guestEmail)) {
				guest = new Guest(guestEmail, guestName,  phoneNumber, pm);
				UserRepository.addGuest(guest);
			}
		}
		else if (!(user instanceof GuestInterface)){
			user = GuestConversionService.toGuest(user, pm);
			UserRepository.changeTypeId((GuestInterface)user);
		}

		ReservationRepository.addReservation(reservation);

		return new Object[] {reservation.getGuestId(), reservation.getCost()};
	}

	public List<Room> deleteOverlapRooms(List<Room> rooms, Calendar[] dateRange) {
		reservations = ReservationRepository.loadReservations();

		for (Reservation re : reservations) {
			if (!dateRange[0].after(re.getEndDate()) && !re.getEndDate().after(dateRange[1])) {
				rooms.remove(re.room);
			}
		}

		System.out.println("Rooms to print on screen: " + rooms.size() + " : ReservationController");

		return rooms;
	}

}
