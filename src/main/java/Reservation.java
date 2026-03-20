import java.util.*;

public class Reservation {
	Room room;
	Calendar start;
	Calendar end;
	int guestNum;
	
	Reservation(Room r, Calendar s, Calendar e, int g) {
		room = r;
		start = s;
		end = e;
		guestNum = g;
	}
}
