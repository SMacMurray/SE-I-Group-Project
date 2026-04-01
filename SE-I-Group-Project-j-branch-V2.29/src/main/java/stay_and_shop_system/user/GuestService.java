package stay_and_shop_system.user;

import java.util.*;

public class GuestService {
	private static List<Guest> guests = new ArrayList<>();
	
	public Guest addGuest(String name, String email) {
		Guest guest = new Guest(name, email);
		guests.add(guest);
		return guest;
	}
	public Guest findGuest(String name, String email) {
		Guest guest = new Guest(name, email);
		for (Guest g : guests) {
			if (g.equals(guest)) {
				guest = g;
			}
		}
		return guest;
	}
	
	public boolean containsGuest(String name, String email) {
		return guests.contains(new Guest(name, email));
	}
}
