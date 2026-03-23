import java.util.*;

public class Reservation {
	Room room;
	int roomNumber;
	int guestNum;
	Calendar start;
	Calendar end;
	String guestName; 
	String creditCardNumber;
	double rate;
	double cost;
	int reservationId;
	
	Reservation(Room r, Calendar s, Calendar e, int g, String n, String cc) {
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
		reservationId = Objects.hash(r.number + cc);
		
		System.out.println(rate + " : Reservation");
		System.out.println(reservationId + " : Reservation");
	}
}
