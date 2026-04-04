package stay_and_shop_system.occupancy;

import java.util.*;

public class Reservation {
	public Room room;
	public int roomNumber = 0;
	public int guestNum = 0;
	public Calendar start;
	public Calendar end;
	public String guestName = "John Doe";
	public String creditCardNumber = "9999-9999-9997";
	public double rate = 0;
	public double cost = 0;
	public int reservationId = Objects.hash(roomNumber + "John Doe");

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

	public Reservation(Room r, Calendar s, Calendar e, int g, String n, String cc) {
		room = r;
		roomNumber = r.number;
		start = s;
		end = e;
		guestNum = g;
		guestName = n;
		creditCardNumber = cc;
		// Rounding it to 2 decimal places (you need to put 100.0 to make it work correctly instead of 100)
		rate = Math.round(100.0 * (r.baseDailyRate + r.qualityLevel.getPrice())) / 100.0;
		cost = 0;
		reservationId = Objects.hash(r.number + n);
		
		System.out.println(rate + " : Reservation");
		System.out.println(reservationId + " : Reservation");
	}
}
