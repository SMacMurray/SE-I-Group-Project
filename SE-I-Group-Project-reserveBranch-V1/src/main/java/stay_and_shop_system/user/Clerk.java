package stay_and_shop_system.user;

import stay_and_shop_system.occupancy.Room;

public class Clerk implements ClerkInterface {
	private String name = "John Doe";
	private String email = "dummyEmail@gmail.com";
	private String phoneNumber;
	private int hash; // Stores the hash of the password, not the password.
	private UserType id = UserType.ADMIN;

	public Clerk(String n, String e) {
		name = n;
		email = e;
	}
	public Clerk(String e, String n, int h, String p) {
		email = e;
		name = n;
		hash = h;
		phoneNumber = p;
	}

	public void setName(String x) {
		name = x;
	}
	public void setEmail(String x) {
		email = x;
	}
	public void setPhoneNumber(String x) { phoneNumber = x; }
	public void setPassword(String x) { hash = x.hashCode(); }
	public void setId(UserType id) { this.id = id; }

	public String getName() {
		return name;
	}
	public String getEmail() {
		return email;
	}
	public String getPhoneNumber() { return phoneNumber; }
	public int getPassword() { return hash; }
	public UserType getId() { return id; }

	public boolean addRoom(Room r) {
		return false;
	}

	public void modifyOwnInformation(Clerk clerk) {

	}
}
