package stay_and_shop_system.user;

import stay_and_shop_system.occupancy.Room;

public class Clerk implements ClerkInterface {
	public void setName(String x) {
		
	}
	public void setEmail(String x) {
		
	}
	public void setPhoneNumber(String x) { }
	public void setPassword(String x) { }
	public String getName() {
		return "";
	}
	public String getEmail() {
		return "";
	}
	public String getPhoneNumber() { return ""; }
    public int getPassword() { return 0; }

	public boolean addRoom(Room r) {
		return false;
	}

	public void modifyOwnInformation(Clerk clerk) {

	}
}
