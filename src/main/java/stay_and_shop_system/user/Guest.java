package stay_and_shop_system.user;

import stay_and_shop_system.occupancy.Reservation;
import java.util.*;

public class Guest implements GuestInterface {
	private String name = "John Doe";
    private String email = "dummyEmail@gmail.com";
    private String phoneNumber;
    private int hash; // Stores the hash of the password, not the password.
    // private List<Reservation> reservations = new ArrayList<>();
    
    
    public Guest(String n, String e) {
    	name = n;
    	email = e;
    }
    public Guest(String n, int h) {
    	name = n;
    	hash = h;
    }
	public void setName(String x) {
		name = x;
	}
    public void setEmail(String x) {
    	email = x;
    }
    public void setPhoneNumber(String x) { phoneNumber = x; }
    public void setPassword(String x) { hash = x.hashCode(); }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPhoneNumber() { return phoneNumber; }
    public int getPassword() { return hash; }

    // reservations is in the GuestInterface
    public void addReservation(Reservation r) {
    	reservations.add(r);
    }
    public void removeReservation(Reservation r) {
    	reservations.remove(r);
    }
    public List<Reservation> findReservations(){ return res.findReservationsOfName(name); }
    
    
    // Did not hash or use equals by 'name' because names can be the same,
    // but emails can't.
    @Override
    public boolean equals(Object o) {
    	if (o == this) return true;
    	if (!(o instanceof Guest)) return false;
    	Guest temp = (Guest)o;
    	return email.equals(temp.email);
    }
    @Override
    public int hashCode() {
    	return Objects.hash(email);
    }
}
