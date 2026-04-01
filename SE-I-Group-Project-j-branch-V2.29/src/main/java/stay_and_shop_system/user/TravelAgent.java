package stay_and_shop_system.user;

import stay_and_shop_system.occupancy.Reservation;

public class TravelAgent implements User {
    private String name = "John Doe";
    private String email = "dummyEmail@gmail.com";
    private String phoneNumber;
    private int hash; // Stores the hash of the password, not the password.
    // private List<Reservation> reservations = new ArrayList<>();

    // Fix when needed
    TravelAgent(String n, String e) {
        name = n;
        email = e;
    }
    TravelAgent(String n, int h) {
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


}
