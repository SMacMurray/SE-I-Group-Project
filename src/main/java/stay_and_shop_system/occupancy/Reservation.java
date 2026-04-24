package stay_and_shop_system.occupancy;

import org.apache.commons.validator.routines.checkdigit.LuhnCheckDigit;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class Reservation {
	private Room room;
	private int roomNumber = 0;
	private int guestNum = 0;
	private Calendar start;
	private Calendar end;
	private String guestName = "John Doe";
	private String guestEmail = "johnDoe@gmail.com";
	private String creditCardNumber = "9999-9999-9997";
	private double rate = 0;
	private double cost = 0;
	private int reservationId = Objects.hash(roomNumber + "John Doe");
	private int guestId = Objects.hash(roomNumber + "John Doe");

	private SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");

	// For testing purposes
	public Reservation() {
		roomNumber = 100;
		guestName = "Johnny Plow";
		reservationId = Objects.hash(roomNumber + guestName);
		start = Calendar.getInstance();
		end = Calendar.getInstance();
		room = new Room();
	}
	public void print() {
		System.out.println("Printing Reservation");
		System.out.println("Reservation Id: " + reservationId);
		System.out.println("Room Number: " + roomNumber);
		System.out.println("Guest Num: " + guestNum);
		System.out.println("Start Date: " + start.getTime());
		System.out.println("End Date: " + end.getTime());
		System.out.println("Guest Name: " + guestName);
		System.out.println("Credit Card Number: " + creditCardNumber);
		System.out.println("Rate: " + rate);
		System.out.println("Cost: " + cost);
	}

	public Reservation(Room r, Calendar s, Calendar e, int g, String n, String em, String cc) {
		room = r;
		roomNumber = r.getNumber();
		start = s;
		end = e;
		guestNum = g;
		guestName = n;
		guestEmail = em;
		creditCardNumber = cc;
		// Rounding it to 2 decimal places (you need to put 100.0 to make it work correctly instead of 100)
		rate = room.getDailyRate();
		cost = 0;
		reservationId = Objects.hash(r.getNumber() + n + getFormattedEndDate());
		guestId = Math.abs(Objects.hash(em));

		System.out.println(rate + " : Reservation");
		System.out.println(reservationId + " : Reservation");
	}

	public void modifyReservation(Room r, int gN, String n, String em, String cc) throws IllegalArgumentException {
		if (r == null) throw new IllegalArgumentException("Room is null");
		if (gN <= 0) throw new IllegalArgumentException("Guest Number is invalid");
		if (gN > r.getMaxOccupancy()) throw new IllegalArgumentException("Guest Number can't be higher than room guest number");
		if (n.isEmpty()) throw new IllegalArgumentException("Name can't be empty");
		if (em.isEmpty()) throw new IllegalArgumentException("Email can't be empty");
		if (!Reservation.validateEmail(em)) throw new IllegalArgumentException("Not a valid email");
		if (cc.isEmpty()) throw new IllegalArgumentException("Credit Card Number can't be empty");
		if (!LuhnCheckDigit.LUHN_CHECK_DIGIT.isValid(cc.replaceAll(" ", ""))) throw new IllegalArgumentException("Credit Card Number is not a valid one.");

		room = r;
		roomNumber = r.getNumber();
		guestNum = gN;
		guestName = n;
		guestEmail = em;
		creditCardNumber = cc;
		// Rounding it to 2 decimal places (you need to put 100.0 to make it work correctly instead of 100)
		rate = room.getDailyRate();
		// Cost stays the same
		reservationId = Objects.hash(r.getNumber() + n + getFormattedEndDate());
		guestId = Math.abs(Objects.hash(em));
	}
	public Room getRoom() { return room; }
	public int getRoomNumber() { return roomNumber; }
	public Calendar getStartDate() { return start; }
	public Calendar getEndDate() { return end; }
	public int getGuestNumber() { return guestNum; }
	public String getGuestName() { return guestName; }
	public String getGuestEmail() { return guestEmail; }
	public String getCreditCardNumber() { return creditCardNumber; }
	public Double getRate() { return rate; }
	public Double getCost() { return cost; }
	public int getReservationId() {return reservationId; }
	public int getGuestId() {return guestId; }
	public String getFormattedStartDate() { return formatter.format(start.getTime()); }
	public String getFormattedEndDate() { return formatter.format(end.getTime()); }

	public void setRoom(Room r) {
		r = room;
		updateRate();
		updateRoomNumber();
		updateReservationId();
	}
	public void setStartDate(Calendar s) { s = start; }
	public void updateRoomNumber() { roomNumber = room.getNumber(); }
	public void setEndDate(Calendar e) { e = end; }
	public void setGuestNumber(int g) { g  = guestNum; }
	public void setGuestName(String n) {
		n = guestName;
		updateReservationId();
	}
	public void setGuestEmail(String email) { guestEmail = email; }
	// TODO: MAKE INPUT VALIDATION FOR CREDIT CARD NUMBER
	public void setCreditCardNumber(String cc) { cc = creditCardNumber; }
	private void updateRate() {
		rate = room.getDailyRate();
	}
	public void setCost(double c) { cost = c; }
	public void updateReservationId() {
		reservationId = Objects.hash(room.getNumber() + guestName + getFormattedEndDate());
	}

	public void calculateTotal() {
		long diffInMillis = Math.abs(end.getTime().getTime() - start.getTime().getTime());
		long daysBetween = TimeUnit.DAYS.convert(diffInMillis, TimeUnit.MILLISECONDS);
		System.out.println("Days Betweeb: " + daysBetween);
		daysBetween += 1;

		cost = Math.round((rate * daysBetween) * 100.0) / 100.0;
	}
	public static boolean validateEmail(String email) throws IllegalArgumentException{
		// The regex means the delimiter will be kept within the start of the next segment. The delimiters are in the brackets '.' and '@'.
		String[] spl = email.split("(?=[.@])");
		Arrays.stream(spl).forEach(s -> System.out.println(s));
		// There can be multiple dots before the @
		boolean invalidEmail = false, atSignExists = false;
		for (int i = 0; i < spl.length && !invalidEmail; ++i) {
			if (spl[i].contains("@")) {
				atSignExists = true;
				if (spl.length - i != 2 || spl[i+1].contains("@") || spl[i].length() == 1 || i == 0) {
					invalidEmail = true;
				}
			}
			else {
				if (spl[i].length() == 1 && !Character.isLetter(spl[i].charAt(0))) { // when consecutive '..'
					invalidEmail = true;
				}
			}
		}

		if (!invalidEmail && atSignExists) {
			return true;
		}
		else return false;
	}
	@Override
	public String toString() {
		String str = "Reservation at room number " +  roomNumber + " w/ reservationId " + reservationId;
		str += "\n Guest Num: " + guestNum;
		str += "\n Start Date: " + getFormattedStartDate();
		str += "\n End Date: " + getFormattedEndDate();
		str += "\n Guest Name: " + guestName;
		str += "\n Credit Card Number: " + creditCardNumber;
		str += "\n Rate: " + rate;
		str += "\n Cost: " + cost;
		str += "\n GuestId: " + guestId;

		return str;
	}
}
