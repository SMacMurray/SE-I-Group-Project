import java.util.*;

public class Guest implements User {
	private String name = "John Doe";
    private String email = "dummyEmail@gmail.com";
    private List<Reservation> reservations = new ArrayList<>();
    
    
    Guest(String n, String e) {
    	name = n;
    	email = e;
    }
	public void setName(String x) {
		name = x;
	}
    public void setEmail(String x) {
    	email = x;
    }
    public String getName() { return name; }
    public String getEmail() { return email; }
    
    public void addReservation(Reservation r) {
    	reservations.add(r);
    }
    public void removeReservation(Reservation r) {
    	reservations.remove(r);
    }
    
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
