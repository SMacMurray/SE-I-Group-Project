package stay_and_shop_system.user;

import java.util.*;

public class GuestService {
	private static List<GuestInterface> guests = new ArrayList<>();
	
	public GuestInterface addGuest(String name, String email) {
		GuestInterface guest = new Guest(name, email);
		guests.add(guest);
		return guest;
	}
	public GuestInterface findGuest(String name, String email) {
		GuestInterface guest = new Guest(name, email);
		for (GuestInterface g : guests) {
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
