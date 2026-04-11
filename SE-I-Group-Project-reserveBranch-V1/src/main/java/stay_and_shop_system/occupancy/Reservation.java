package stay_and_shop_system.occupancy;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class Reservation {
	public Room room;
	public int roomNumber = 0;
	public int guestNum = 0;
	public Calendar start;
	public Calendar end;
	public String guestName = "John Doe";
	public String guestEmail = "johnDoe@gmail.com";
	public String creditCardNumber = "9999-9999-9997";
	public double rate = 0;
	public double cost = 0;
	public int reservationId = Objects.hash(roomNumber + "John Doe");
	public int guestId = Objects.hash(roomNumber + "John Doe");

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
		roomNumber = r.number;
		start = s;
		end = e;
		guestNum = g;
		guestName = n;
		guestEmail = em;
		creditCardNumber = cc;
		// Rounding it to 2 decimal places (you need to put 100.0 to make it work correctly instead of 100)
		rate = Math.round(100.0 * (r.baseDailyRate + r.qualityLevel.getPrice())) / 100.0;
		cost = 0;
		reservationId = Objects.hash(r.number + n);
		guestId = Math.abs(Objects.hash(em));

		System.out.println(rate + " : Reservation");
		System.out.println(reservationId + " : Reservation");
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
		rate = Math.round(100.0 * (room.baseDailyRate + room.qualityLevel.getPrice())) / 100.0;
	}
	public void setCost(double c) { c = cost; }
	public void updateReservationId() {
		reservationId = Objects.hash(room.number + guestName);
	}

	public void calculateTotal() {
		long diffInMillis = Math.abs(end.getTime().getTime() - start.getTime().getTime());
		long daysBetween = TimeUnit.DAYS.convert(diffInMillis, TimeUnit.MILLISECONDS);
		System.out.println("Days Betweeb: " + daysBetween);
		daysBetween += 1;

		cost = Math.round((rate * daysBetween) * 100.0) / 100.0;
	}

	@Override
	public String toString() {
		String str = "Reservation at room number " +  roomNumber + " w/ reservationId " + reservationId;
		str += "\n Guest Num: " + guestNum;
		str += "\n Start Date: " + start.getTime();
		str += "\n End Date: " + end.getTime();
		str += "\n Guest Name: " + guestName;
		str += "\n Credit Card Number: " + creditCardNumber;
		str += "\n Rate: " + rate;
		str += "\n Cost: " + cost;
		str += "\n GuestId: " + guestId;

		return str;
	}
}
