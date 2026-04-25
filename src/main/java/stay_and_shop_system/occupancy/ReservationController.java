package stay_and_shop_system.occupancy;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import org.apache.commons.validator.routines.checkdigit.LuhnCheckDigit;
import stay_and_shop_system.ColorPalette;
import stay_and_shop_system.occupancy.database.ReservationRepository;
import stay_and_shop_system.occupancy.database.RoomRepository;
import stay_and_shop_system.user.*;

import javax.swing.*;
import java.text.SimpleDateFormat;
import java.util.*;


public class ReservationController {
	// Won't stop saying can't make a type of ArrayList or smth, had to cut the entire code than paste it
	private static List<Reservation> reservations  = new ArrayList<>();

	ReservationRepository rrp = new ReservationRepository();
	RoomRepository rp = new RoomRepository();
	private RoomService rs = new RoomService();
	private GuestService gs = new GuestService();
	private SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");

	public SimpleDateFormat getDateFormatter() {
		return formatter;
	}
	public Object[] reserveRoom(Room room, Calendar start, Calendar end, int guestNum,
								String guestName, String guestEmail, String phoneNumber,
								String creditCardNumber, String ccv, String billingAddr,
								Calendar expDate ) throws IllegalArgumentException {
		if (room == null || start == null || end == null || guestName == null
				|| guestName.isEmpty() || guestEmail == null || guestEmail.isEmpty() ||
				phoneNumber == null || phoneNumber.isEmpty() || creditCardNumber == null ||
				creditCardNumber.isEmpty() || ccv == null || ccv.isEmpty() ||
				billingAddr == null || billingAddr.isEmpty() || expDate == null) {
			throw new IllegalArgumentException("At least one input is empty.");
		}
		if (LuhnCheckDigit.LUHN_CHECK_DIGIT.isValid(creditCardNumber)) {
			throw new IllegalArgumentException("The credit card number is invalid.");
		}
		if (LuhnCheckDigit.LUHN_CHECK_DIGIT.isValid(creditCardNumber)) {
			throw new IllegalArgumentException("The credit card number is invalid.");
		}
		PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
		Phonenumber.PhoneNumber guestPhoneNumber = new Phonenumber.PhoneNumber();
		boolean validPhoneNumber = false;
		try {
			// Parsing international phone number
			guestPhoneNumber = phoneUtil.parse(phoneNumber, null);

			validPhoneNumber = phoneUtil.isValidNumber(guestPhoneNumber);
		} catch (NumberParseException e) {
			throw new IllegalArgumentException("The international phone number given is invalid");
		}
		if (!validPhoneNumber) {
			throw new IllegalArgumentException("The international phone number given is invalid");
		}
		if (!Reservation.validateEmail(guestEmail)) {
			throw new IllegalArgumentException("The email is invalid.");
		}
		if (ccv.length() > 4 || ccv.length() < 3 || !ccv.matches("[0-9]+")) {
			throw new IllegalArgumentException("The CCV is invalid.");
		}

		Reservation reservation = new Reservation(room, start, end, guestNum, guestName, guestEmail,  creditCardNumber);
		reservation.calculateTotal();

		User user = UserRepository.getSessionAccount();
		GuestInterface guest;
		PaymentMethod pm = new PaymentMethod(creditCardNumber, ccv, billingAddr, expDate);
		// Accounting for when Clerk is reserving a room for a guest and if the User is not signed in
		if (user == null || (user instanceof ClerkInterface && !user.getEmail().equals(guestEmail))) {
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
				rooms.remove(re.getRoom());
			}
		}

		System.out.println("Rooms to print on screen: " + rooms.size() + " : ReservationController");

		return rooms;
	}
	public Reservation modifyReservation(Reservation reservation, int roomNumber, int guestNumber, String guestName, String guestEmail, String creditCardNumber) throws IllegalArgumentException {
		Room room = RoomRepository.loadRoomOfRoomNumber(roomNumber);
		int previousReservationId = reservation.getReservationId();

		reservation.modifyReservation(room, guestNumber, guestName, guestEmail, creditCardNumber);
		ReservationRepository.modifyReservation(previousReservationId, reservation);

		return reservation;
	}
	public void checkIn(Reservation reservation, Calendar checkInDate, int guestId) {
		// Joel:
		// If guestId does not match reservation, exit / throw exception .(to make sure the person that owns reservation checks out/in)
		// Set the Check in Date of Reservation, Increment the cost by the rate by one time, and Update it in ReservationRepository
		// Set the Room of the reservation as occupied, and Update it in RoomRepository


	}
	public void checkOut(Reservation reservation, int guestId) {
		// Keep the Guest, since hotels usually keep the guest info.

		// Joel:
		// If guestId does not match reservation, exit / throw exception. (to make sure the person that owns reservation checks out/in)
		// Set the Room of the reservation as unoccupied, and Update it in RoomRepository
		// Keep the cost in a variable, Delete the reservation.
		// Return the cost
	}
}
